package com.asettracker.tg.main.menu.main_menu;

import com.asettracker.tg.main.dto.MyTelegramClient;
import com.asettracker.tg.main.menu.IMenu;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Component
public class MainMenu implements IMenu {

    private final TelegramClient telegramClient;
    private final List<IMainMenuButton> buttons;

    public MainMenu(MyTelegramClient myTelegramClient, List<IMainMenuButton> buttons) {
        this.telegramClient = myTelegramClient.getTelegramClient();
        this.buttons = buttons;
    }

    @SneakyThrows
    @Override
    public void sendMenu(Update update) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("С чего начнём?🧐")
                .replyMarkup(combineButtons(buttons))
                .build();
        telegramClient.execute(sendMessage);
    }
}