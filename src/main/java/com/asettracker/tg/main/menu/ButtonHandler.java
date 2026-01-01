package com.asettracker.tg.main.menu;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ButtonHandler {
    boolean canHandleButton(Update update);
    void handleButton(Update update);
}
