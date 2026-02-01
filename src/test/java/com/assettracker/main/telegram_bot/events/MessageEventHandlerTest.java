package com.assettracker.main.telegram_bot.events;

import com.assettracker.main.telegram_bot.database.dto.UpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig
public class MessageEventHandlerTest {

    @MockitoBean
    MessageEventHandler messageEventHandler;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public UpdateDto getTooledUpdateDto() {
        return UpdateDto.builder()
                .chatId(ThreadLocalRandom.current().nextLong())
                .firstName(UUID.randomUUID().toString())
                .lastName(UUID.randomUUID().toString())
                .userName(UUID.randomUUID().toString())
                .userInput(Optional.empty())
                .build();
    }

    @Test
    public void handleStartTest() {
        var updateDto = getTooledUpdateDto();
        eventPublisher.publishEvent(new MessageEvent(this, Message.START, updateDto));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(messageEventHandler, times(1)).handleStart(any()));
    }

    @Test
    public void handleBagTest() {
        var updateDto = getTooledUpdateDto();
        eventPublisher.publishEvent(new MessageEvent(this, Message.BAG, updateDto));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(messageEventHandler, times(1)).handleBag(any()));
    }

    @Test
    public void handleProfileTest() {
        var updateDto = getTooledUpdateDto();
        eventPublisher.publishEvent(new MessageEvent(this, Message.PROFILE, updateDto));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(messageEventHandler, times(1)).handleProfile(any()));
    }

    @Test
    public void handleMenuTest() {
        var updateDto = getTooledUpdateDto();
        eventPublisher.publishEvent(new MessageEvent(this, Message.MENU, updateDto));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(messageEventHandler, times(1)).handleMenu(any()));
    }

    @Test
    public void handleUnknownTest() {
        var updateDto = getTooledUpdateDto();
        eventPublisher.publishEvent(new MessageEvent(this, Message.UNKNOWN, updateDto));

        await()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(messageEventHandler, times(1)).handleUnknown(any()));
    }
}
