package com.asettracker.tg.main.menu.main_menu;

import com.asettracker.tg.main.dto.MyTelegramClient;
import com.asettracker.tg.main.menu.bag_menu.BagMenu;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class ViewBagButton implements IMainMenuButton {

    private static final String VIEW_BAG_CALLBACK_DATA = "viewBag";
    private final TelegramClient telegramClient;
    private final BagMenu bagMenu;

    public ViewBagButton(MyTelegramClient myTelegramClient, BagMenu bagMenu) {
        this.telegramClient = myTelegramClient.getTelegramClient();
        this.bagMenu = bagMenu;
    }

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("my bag")
                .callbackData(VIEW_BAG_CALLBACK_DATA)
                .build();
    }

    @Override
    public boolean canHandleButton(Update update) {
        return update.getCallbackQuery().getData().equals(VIEW_BAG_CALLBACK_DATA);
    }

    @SneakyThrows
    @Override
    public void handleButton(Update update) {
        bagMenu.sendMenu(update);
    }
}
