package com.cryptodemoaccount.event;

import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.dto.UserQuestionDto;
import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.database.service.BagService;
import com.cryptodemoaccount.database.service.DataInitializerService;
import com.cryptodemoaccount.database.service.UserQuestionService;
import com.cryptodemoaccount.database.service.UserService;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.menu.bag_menu.BagMenu;
import com.cryptodemoaccount.menu.enter_asset_count_menu.EnterAssetCountMenu;
import com.cryptodemoaccount.menu.main_menu.MainMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.menu.my_profile_menu.MyProfileMenu;
import com.cryptodemoaccount.menu.support_menu.SupportMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MessageEventHandler {

    private final UserService userService;
    private final UserQuestionService userQuestionService;
    private final MyAssetsMenu myAssetsMenu;
    private final MainMenu mainMenu;
    private final BagMenu bagMenu;
    private final BagService bagService;
    private final DataInitializerService initializer;
    private final UserCoinService userCoinService;
    private final EnterAssetCountMenu enterAssetCountMenu;
    private final WaitingMenu waitingMenu;
    private final LastMessageService lastMessageService;
    private final MyProfileMenu myProfileMenu;
    private final SupportMenu supportMenu;

    @EventListener(condition = "event.getMessage().name() == 'START'")
    public void handleStart(MessageEvent event) {
        var chatId = event.getUpdateDto().getChatId();
        mainMenu.sendMenu(chatId);
        if (bagService.findByChatId(chatId).isEmpty()) {
            initializer.initializeUserAndBag(event.getUpdateDto());
        }
    }

    @EventListener(condition = "event.getMessage().name() == 'MENU'")
    public void handleMenu(MessageEvent event) {
        var chatId = event.getUpdateDto().getChatId();
        mainMenu.sendMenu(chatId);
    }

    @EventListener(condition = "event.getMessage().name() == 'BAG'")
    public void handleBag(MessageEvent event) {
        Long chatId = event.getUpdateDto().getChatId();
        bagMenu.sendMenu(chatId);
    }

    @EventListener(condition = "event.getMessage().name() == 'PROFILE'")
    public void handleProfile(MessageEvent event) {
        waitingMenu.sendMenu(event.getUpdateDto().getChatId());
        Integer lastMessageId = lastMessageService.getLastMessage(event.getUpdateDto().getChatId());
        myProfileMenu.editMsgAndSendMenu(event.getUpdateDto().getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getMessage().name() == 'UNKNOWN'")
    public void handleUnknown(MessageEvent event) {
        var chatId = event.getUpdateDto().getChatId();
        if (userCoinService.isUserWaitingNumber(chatId)) {
            addUserAssetAndSendSuccess(event, chatId);
        } else if (userService.isUserWriteQuestion(chatId)) {
            saveQuestionAndSendSuccess(event, chatId);
        }
    }

    public void saveQuestionAndSendSuccess(MessageEvent event, Long chatId) {
        UUID userId = userService.findByChatId(chatId).orElseThrow().getId();
        userQuestionService.create(
                new UserQuestionDto(event.getUpdateDto().getUserInput().orElseThrow(), userId)
        );
        supportMenu.sendSuccess(chatId);
    }

    public void addUserAssetAndSendSuccess(MessageEvent event, Long chatId) {
        addAssetAndDeleteTmpUserCoin(event, chatId);
        enterAssetCountMenu.sendSuccess(chatId);
        myAssetsMenu.sendMenu(chatId);
    }

    public void addAssetAndDeleteTmpUserCoin(MessageEvent event, Long chatId) {
        var coinCount = BigDecimal.valueOf(
                Double.parseDouble(event.getUpdateDto().getUserInput().orElseThrow().trim())
        );
        UserCoinDto tmpCoin = userCoinService.findByChatId(chatId).orElseThrow();
        tmpCoin.setCount(coinCount);
        BagDto updateBag = bagService.addAsset(tmpCoin);
        bagService.update(updateBag);
        userCoinService.deleteByChatId(chatId);
    }
}
