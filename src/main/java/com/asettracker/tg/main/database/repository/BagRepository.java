package com.asettracker.tg.main.database.repository;

import com.asettracker.tg.main.database.entity.BagEntity;
import com.asettracker.tg.main.database.entity.TelegramIdEntity;
import com.asettracker.tg.main.database.service.BagDbService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BagRepository extends JpaRepository<BagEntity, UUID> {

    Optional<BagEntity> findByTelegramId(TelegramIdEntity telegramId);
}
