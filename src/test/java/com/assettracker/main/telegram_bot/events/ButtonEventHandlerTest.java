package com.assettracker.main.telegram_bot.events;

import com.assettracker.main.telegram_bot.menu.asset_list_menu.Coins;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig
public class ButtonEventHandlerTest {

    @MockitoBean
    ButtonEventHandler buttonEventHandler;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private Long randomChatId() {
        return ThreadLocalRandom.current().nextLong();
    }

    @Test
    public void handleMyBagTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.MY_BAG, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleMyBag(any()));
    }

    @Test
    public void handleCreateAssetTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.CREATE_ASSET, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleCreateAsset(any()));
    }

    @Test
    public void handleUpdateAssetTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.UPDATE_ASSET, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleUpdateAsset(any()));
    }

    @Test
    public void handleForceUpdateAssetTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.FORCE_UPDATE_ASSET, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleForceUpdateAsset(any()));
    }

    @Test
    public void handleForceCreateAsset() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.FORCE_CREATE_ASSET, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleForceCreateAsset(any()));
    }

    @Test
    public void handleCreateAssetAfterTryDeleteTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.CREATE_ASSET_AFTER_TRY_DELETE, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1))
                        .handleCreateAssetAfterTryDelete(any()));
    }

    @Test
    public void handleCancelToMyAssetsTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.CANCEL_TO_MY_ASSETS, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleCancelToMyAssets(any()));
    }

    @Test
    public void handleDeleteAssetTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.DELETE_ASSET, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleDeleteAsset(any()));
    }

    @Test
    public void handleCancelToMenuTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.CANCEL_TO_MENU, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleCancelToMenu(any()));
    }

    @Test
    public void handleMyProfileTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.MY_PROFILE, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleProfile(any()));
    }

    @Test
    public void handleCancelToBagMenuTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.CANCEL_TO_BAG_MENU, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleCancelToBagMenu(any()));
    }

    @Test
    public void handleUpdateBagDataTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.UPDATE_BAG_DATA, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleUpdateBagData(any()));
    }

    @Test
    public void handleCancelToAssetsTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.CANCEL_TO_ASSETS, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleCancelToAssets(any()));
    }

    @Test
    public void handleAssetsTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.ASSETS, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleAssets(any()));
    }

    @Test
    public void handleMyAssetsTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.MY_ASSETS, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleMyAssets(any()));
    }

    @Test
    public void handleAssetStatisticsTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.ASSET_STATISTICS, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleAssetStatistics(any()));
    }

    @Test
    public void handleTradeWithAITest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.TRADE_WITH_AI, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleTradeWithAI(any()));
    }

    @Test
    public void handleAIAdviceTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.AI_ADVICE, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleAIAdvice(any()));
    }

    @Test
    public void handleSupportTest() {
        eventPublisher.publishEvent(new ButtonEvent(this, Button.SUPPORT, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleSupport(any()));
    }

    @Test
    public void handleAnyAssetButtonTest() {
        eventPublisher.publishEvent(new AssetButtonEvent(this, Coins.BITCOIN, randomChatId()));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(buttonEventHandler, times(1)).handleAnyAssetButton(any()));
    }
}
