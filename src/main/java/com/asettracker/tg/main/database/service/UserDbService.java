package com.asettracker.tg.main.database.service;

import com.asettracker.tg.main.database.entity.BagEntity;
import com.asettracker.tg.main.database.entity.TelegramIdEntity;
import com.asettracker.tg.main.database.entity.UserEntity;
import com.asettracker.tg.main.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserDbService {

    private final UserRepository userRepo;

    public UserEntity saveUser(UserEntity user, BagEntity bag) {
        user.setBag(bag);
        return userRepo.save(user);
    }

    public boolean hasUserByTelegramId(TelegramIdEntity telegramId) {
        return userRepo.findByTelegramId(telegramId).isPresent();
    }

    public Optional<UserEntity> findByTelegramId(TelegramIdEntity telegramId) {
        return userRepo.findByTelegramId(telegramId);
    }
}
