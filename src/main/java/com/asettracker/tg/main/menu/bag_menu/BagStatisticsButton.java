//package com.asettracker.tg.main.menu.bag_menu;
//
//import com.asettracker.tg.main.dto.MyTelegramClient;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.generics.TelegramClient;
//
//@Component
//public class BagStatisticsButton implements IBagMenuButton {
//
//    private static final String BAG_STATISTICS_CALLBACK_DATA = "bagStatistics";
//    private final TelegramClient telegramClient;
//
//    public BagStatisticsButton(MyTelegramClient myTelegramClient) {
//        this.telegramClient = myTelegramClient.getTelegramClient();
//    }
//
//    @Override
//    public InlineKeyboardButton getButton() {
//        return InlineKeyboardButton.builder()
//                .text("Статистика")
//                .callbackData(BAG_STATISTICS_CALLBACK_DATA)
//                .build();
//    }
//
//    @Override
//    public boolean canHandleButton(Update update) {
//        return update.getCallbackQuery().getData().equals(BAG_STATISTICS_CALLBACK_DATA);
//    }
//
//    @Override
//    public void handleButton(Update update) {
//
//    }
//}
