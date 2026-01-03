package com.asettracker.tg.main.service;

import com.asettracker.tg.main.menu.IButton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@AllArgsConstructor
public class GeneralButtonHandler {
    private List<IButton> allHandlers;

    public void handleAnyButton(Update update) {
        allHandlers.forEach(handler -> {
            if (handler.canHandleButton(update)) {
                handler.handleButton(update);
            }
        });
    }
}
