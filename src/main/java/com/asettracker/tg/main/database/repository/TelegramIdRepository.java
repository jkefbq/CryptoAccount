package com.asettracker.tg.main.database.repository;

import com.asettracker.tg.main.database.entity.TelegramIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramIdRepository extends JpaRepository<TelegramIdEntity, Long> {

}
