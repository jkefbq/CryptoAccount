package com.asettracker.tg.main.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "telegram_id")
    private TelegramIdEntity telegramId;

    @Column(name = "first_name", length = 35)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "username", length = 45)
    private String userName;

    @Setter
    @OneToOne
    @JoinColumn(name = "bag_id")
    private BagEntity bag;

    public UserEntity(Update update, TelegramIdEntity telegramId) {
        this.firstName = update.getMessage().getFrom().getFirstName();
        this.lastName = update.getMessage().getFrom().getLastName();
        this.userName = update.getMessage().getFrom().getUserName();
        this.telegramId = telegramId;
    }
}
