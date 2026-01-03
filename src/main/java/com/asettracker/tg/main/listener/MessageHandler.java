package com.asettracker.tg.main.listener;

import com.asettracker.tg.main.database.entity.BagEntity;
import com.asettracker.tg.main.database.entity.TelegramIdEntity;
import com.asettracker.tg.main.database.entity.UserEntity;
import com.asettracker.tg.main.database.service.BagDbService;
import com.asettracker.tg.main.database.service.TelegramIdDbService;
import com.asettracker.tg.main.database.service.UserDbService;
import com.asettracker.tg.main.dto.MyTelegramClient;
import com.asettracker.tg.main.menu.main_menu.MainMenu;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class MessageHandler {

    private final TelegramClient telegramClient;
    private final BagDbService bagDbService;
    private final UserDbService userDbService;
    private final TelegramIdDbService tgIdDbService;
    private final MainMenu mainMenu;


    public MessageHandler(BagDbService bagDbService, MyTelegramClient myTelegramClient, UserDbService userDbService, TelegramIdDbService tgIdDbService, MainMenu mainMenu) {
        this.bagDbService = bagDbService;
        this.telegramClient = myTelegramClient.getTelegramClient();
        this.userDbService = userDbService;
        this.tgIdDbService = tgIdDbService;
        this.mainMenu = mainMenu;
    }

    public void handleAnyMessage(Update update) {
        switch (update.getMessage().getText()) {
            case "/start" -> handleStartMsg(update);
            case "/menu" -> handleMenu(update);
            default -> handleUnknown(update);
        }
    }

    private void handleMenu(Update update) {
        mainMenu.sendMenu(update);
    }

    @SneakyThrows
    private void handleUnknown(Update update) {
        SendMessage sendMessage = SendMessage.builder()
                .text("нераспознанная команда, хотите вернуться в меню? /menu")
                .chatId(update.getMessage().getChatId())
                .build();
        telegramClient.execute(sendMessage);
    }

    @Transactional
    private void handleStartMsg(Update update) {
        mainMenu.sendMenu(update);
        new Thread(() -> createUserAndBagAndTgId(update)).start();
    }

    @Transactional
    private void createUserAndBagAndTgId(Update update) {
        if (!userDbService.hasUserByTelegramId(
                new TelegramIdEntity(update.getMessage().getFrom().getId()))
        ) {
            TelegramIdEntity telegramIdEntity = tgIdDbService
                    .saveTelegramIdEntity(new TelegramIdEntity(update.getMessage().getFrom().getId()));
            userDbService.saveUser(
                    new UserEntity(update, telegramIdEntity),
                    bagDbService.saveBag(new BagEntity(telegramIdEntity))
            );
        }
    }
}
