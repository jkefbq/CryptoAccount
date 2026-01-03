package com.asettracker.tg.main.database.service;

import com.asettracker.tg.main.database.entity.TelegramIdEntity;
import com.asettracker.tg.main.database.repository.TelegramIdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class TelegramIdDbService {

    private final TelegramIdRepository tgRepo;

    public TelegramIdEntity saveTelegramIdEntity(TelegramIdEntity telegramIdEntity) {
        return tgRepo.save(telegramIdEntity);
    }
}
