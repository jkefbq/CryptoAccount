package com.asettracker.tg.main.database.service;

import com.asettracker.tg.main.database.entity.BagEntity;
import com.asettracker.tg.main.database.entity.TelegramIdEntity;
import com.asettracker.tg.main.database.repository.BagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BagDbService {

    private final BagRepository bagRepo;

    public BagEntity saveBag(BagEntity bag) {
        return bagRepo.save(bag);
    }

    public Optional<BagEntity> findBagByTelegramId(TelegramIdEntity telegramId) {
        return bagRepo.findByTelegramId(telegramId);
    }
}
