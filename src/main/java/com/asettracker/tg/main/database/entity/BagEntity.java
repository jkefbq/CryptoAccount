package com.asettracker.tg.main.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "bags")
@Entity
@Getter
@NoArgsConstructor
public class BagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "telegram_id")
    private TelegramIdEntity telegramId;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "asset_count")
    private int assetCount;

    public BagEntity(TelegramIdEntity telegramId) {
        this.createdAt = LocalDate.now();
        this.totalCost = BigDecimal.ZERO;
        this.assetCount = 0;
        this.telegramId = telegramId;
    }
}
