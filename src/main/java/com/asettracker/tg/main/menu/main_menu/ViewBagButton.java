package com.asettracker.tg.main.menu.main_menu;

import com.asettracker.tg.main.dto.MyTelegramClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class ViewBagButton implements IMainMenuButton {

    private static final String VIEW_BAG_CALLBACK_DATA = "viewBag";
    private final TelegramClient telegramClient;
    private int menuOrder;

    public ViewBagButton(MyTelegramClient myTelegramClient) {
        this.telegramClient = myTelegramClient.getTelegramClient();
    }

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("my bag")
                .callbackData(VIEW_BAG_CALLBACK_DATA)
                .build();
    }

    @Override
    public boolean canHandleButton(CallbackQuery callbackQuery) {
        return callbackQuery.getData().equals(VIEW_BAG_CALLBACK_DATA);
    }

    @SneakyThrows
    @Override
    public void handleButton(CallbackQuery callbackQuery) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(callbackQuery.getFrom().getId())
                .text("вы нажали мой портфель")
                .build();
        telegramClient.execute(sendMessage);
    }
}
