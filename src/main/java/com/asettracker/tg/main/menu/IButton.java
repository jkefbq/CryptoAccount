package com.asettracker.tg.main.menu;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface IButton {
    InlineKeyboardButton getButton();
    boolean canHandleButton(Update update);
    void handleButton(Update update);
}
