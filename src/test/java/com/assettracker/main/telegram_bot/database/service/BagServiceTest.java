package com.assettracker.main.telegram_bot.database.service;

import com.assettracker.main.telegram_bot.database.dto.BagDto;
import com.assettracker.main.telegram_bot.database.entity.BagEntity;
import com.assettracker.main.telegram_bot.database.mapper.BagMapperImpl;
import com.assettracker.main.telegram_bot.database.repository.BagRepository;
import com.assettracker.main.telegram_bot.service.MarketInfoKeeper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BagServiceTest {

    @Mock
    BagRepository bagRepository;
    @Mock
    BagMapperImpl bagMapper;
    @Mock
    MarketInfoKeeper marketInfoKeeper;
    @InjectMocks
    BagService bagService;

    public BagEntity getBagEntity() {
        return new BagEntity(ThreadLocalRandom.current().nextLong());
    }

    @Test
    public void createBagTest() {
        var bag = getBagEntity();

        bagService.createBag(bag);

        verify(bagRepository, times(1)).save(bag);
    }

    @Test
    public void updateBagTest() {
        var bag = getBagEntity();

        bagService.updateBag(bag);

        verify(bagRepository, times(1)).save(bag);
    }

    @Test
    public void findByChatIdTest() {
        bagService.findByChatId(1L);
        verify(bagRepository, times(1)).findByChatId(1L);
    }

    @Test
    public void toDtoTest() {
        bagService.toDto(getBagEntity());
        verify(bagMapper).toDto(any());
    }

    @Test
    public void toEntityTest() {
        bagService.toEntity(new BagDto());
        verify(bagMapper).toEntity(any());
    }
}
