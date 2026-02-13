package com.cryptodemoaccount.event;

import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.dto.UpdateDto;
import com.cryptodemoaccount.database.dto.UserDto;
import com.cryptodemoaccount.database.entity.BagEntity;
import com.cryptodemoaccount.database.entity.UserStatus;
import com.cryptodemoaccount.database.service.BagService;
import com.cryptodemoaccount.database.service.DataInitializerService;
import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.database.service.UserQuestionService;
import com.cryptodemoaccount.database.service.UserService;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.menu.bag_menu.BagMenu;
import com.cryptodemoaccount.menu.enter_asset_count_menu.EnterAssetCountMenu;
import com.cryptodemoaccount.menu.main_menu.MainMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.menu.my_profile_menu.MyProfileMenu;
import com.cryptodemoaccount.menu.support_menu.SupportMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class MessageEventHandlerTest {

    @Mock
    UserService userService;
    @Mock
    UserQuestionService userQuestionService;
    @Mock
    MyAssetsMenu myAssetsMenu;
    @Mock
    MainMenu mainMenu;
    @Mock
    BagMenu bagMenu;
    @Mock
    BagService bagService;
    @Mock
    DataInitializerService initializer;
    @Mock
    UserCoinService userCoinService;
    @Mock
    EnterAssetCountMenu enterAssetCountMenu;
    @Mock
    WaitingMenu waitingMenu;
    @Mock
    LastMessageService lastMessageService;
    @Mock
    MyProfileMenu myProfileMenu;
    @Mock
    SupportMenu supportMenu;

    @InjectMocks
    MessageEventHandler messageEventHandler;

    public UpdateDto getTooledUpdateDto() {
        return UpdateDto.builder()
                .chatId(ThreadLocalRandom.current().nextLong())
                .firstName(UUID.randomUUID().toString())
                .lastName(UUID.randomUUID().toString())
                .userName(UUID.randomUUID().toString())
                .userInput(Optional.empty())
                .build();
    }

    public BagDto getTooledBagDto() {
        return BagDto.builder()
                .assets(new HashMap<>())
                .id(UUID.randomUUID())
                .chatId(ThreadLocalRandom.current().nextLong())
                .createdAt(LocalDate.now())
                .totalCost(BigDecimal.ZERO)
                .build();
    }

    public UserDto getTooledUserDto() {
        return UserDto.builder()
                .userName("username")
                .firstName("firstname")
                .lastName("lastname")
                .bag(new BagEntity())
                .status(UserStatus.FREE)
                .chatId(ThreadLocalRandom.current().nextLong())
                .build();
    }

    public UserCoinDto getTooledUserCoinDto() {
        return UserCoinDto.builder()
                .coin(Coins.BITCOIN)
                .count(BigDecimal.ONE)
                .chatId(ThreadLocalRandom.current().nextLong())
                .assetDo(AssetDo.CREATE)
                .build();
    }

    @Test
    public void handleStartTest_newUser() {
        when(bagService.findByChatId(any())).thenReturn(Optional.empty());

        messageEventHandler.handleStart(new MessageEvent(this, Message.START, getTooledUpdateDto()));

        verify(mainMenu).sendMenu(any());
        verify(initializer).initializeUserAndBag(any());
    }

    @Test
    public void handleStartTest_oldUser() {
        when(bagService.findByChatId(any())).thenReturn(Optional.of(getTooledBagDto()));

        messageEventHandler.handleStart(new MessageEvent(this, Message.START, getTooledUpdateDto()));

        verify(mainMenu).sendMenu(any());
        verify(initializer, never()).initializeUserAndBag(any());
    }

    @Test
    public void handleBagTest() {
        messageEventHandler.handleBag(new MessageEvent(this, Message.BAG, getTooledUpdateDto()));

        verify(bagMenu).sendMenu(any());
    }

    @Test
    public void handleProfileTest() {
        messageEventHandler.handleProfile(new MessageEvent(this, Message.PROFILE, getTooledUpdateDto()));

        verify(myProfileMenu).editMsgAndSendMenu(any(), any());
    }

    @Test
    public void handleMenuTest() {
        messageEventHandler.handleMenu(new MessageEvent(this, Message.MENU, getTooledUpdateDto()));

        verify(mainMenu).sendMenu(any());
    }

    @Test
    public void handleUnknownTest_userWaitingNumberToCreateAsset() {
        UpdateDto update = getTooledUpdateDto();
        update.setUserInput(Optional.of("123.523"));
        when(userCoinService.isUserWaitingNumber(any())).thenReturn(true);
        when(userCoinService.findByChatId(any())).thenReturn(Optional.ofNullable(getTooledUserCoinDto()));

        messageEventHandler.handleUnknown(new MessageEvent(this, Message.UNKNOWN, update));

        verify(bagService).addAsset(any());
        verify(enterAssetCountMenu).sendSuccess(any());
    }

    @Test
    public void handleUnknownTest_userWaitingNumberToCreateAsset_incorrectInput() {
        UpdateDto update = getTooledUpdateDto();
        update.setUserInput(Optional.of("text"));
        when(userCoinService.isUserWaitingNumber(any())).thenReturn(true);


        assertThrows(NumberFormatException.class, () ->
                messageEventHandler.handleUnknown(new MessageEvent(this, Message.UNKNOWN, update)));
    }

    @Test
    public void handleUnknownTest_userWriteQuestion() {
        UpdateDto update = getTooledUpdateDto();
        update.setUserInput(Optional.of("question"));
        when(userCoinService.isUserWaitingNumber(any())).thenReturn(false);
        when(userService.isUserWriteQuestion(any())).thenReturn(true);
        when(userService.findByChatId(any())).thenReturn(Optional.ofNullable(getTooledUserDto()));

        messageEventHandler.handleUnknown(new MessageEvent(this, Message.UNKNOWN, update));

        verify(userQuestionService).create(any());
        verify(supportMenu).sendSuccess(any());
    }
}
