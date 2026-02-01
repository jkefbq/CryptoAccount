package com.assettracker.main.telegram_bot.database.service;

import com.assettracker.main.telegram_bot.database.dto.BagDto;
import com.assettracker.main.telegram_bot.database.dto.UpdateDto;
import com.assettracker.main.telegram_bot.database.entity.BagEntity;
import com.assettracker.main.telegram_bot.database.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataInitializerServiceTest {

    @Captor
    ArgumentCaptor<UserEntity> captor;

    @Mock
    UserService userService;
    @Mock
    BagService bagService;

    @InjectMocks
    DataInitializerService initializer;

    public UpdateDto getUpdateDto() {
        return UpdateDto.builder()
                .chatId(ThreadLocalRandom.current().nextLong())
                .firstName("firstname")
                .lastName("lastname")
                .userName("username")
                .build();
    }

    public BagDto getBagDto() {
        return BagDto.builder()
                .id(UUID.randomUUID())
                .chatId(ThreadLocalRandom.current().nextLong())
                .assets(new HashMap<>())
                .totalCost(BigDecimal.TEN)
                .createdAt(LocalDate.now())
                .build();
    }

    public BagEntity getBagEntity() {
        return new BagEntity(ThreadLocalRandom.current().nextLong());
    }

    @Test
    public void initializeUserAndBagTest() {
        when(bagService.createBag(any())).thenReturn(getBagDto());
        when(bagService.toEntity(any())).thenReturn(getBagEntity());
        initializer.initializeUserAndBag(getUpdateDto());

        verify(userService, times(1)).createUser(captor.capture());
        verify(bagService, times(1)).createBag(any());
        assertNotNull(captor.getValue().getBag());
    }
}
