package com.asettracker.tg.main.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users_telegram_id")
@AllArgsConstructor
@NoArgsConstructor
public class TelegramIdEntity {
    @Id
    private Long TelegramId;
}
