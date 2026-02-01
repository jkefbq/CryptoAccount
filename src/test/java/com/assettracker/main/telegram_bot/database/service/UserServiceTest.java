package com.assettracker.main.telegram_bot.database.service;

import com.assettracker.main.telegram_bot.database.dto.UserDto;
import com.assettracker.main.telegram_bot.database.entity.UserEntity;
import com.assettracker.main.telegram_bot.database.entity.UserStatus;
import com.assettracker.main.telegram_bot.database.mapper.UserMapper;
import com.assettracker.main.telegram_bot.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    public UserDto getUserDto() {
        return UserDto.builder()
                .userName("username")
                .firstName("firstname")
                .lastName("lastname")
                .id(UUID.randomUUID())
                .chatId(ThreadLocalRandom.current().nextLong())
                .status(UserStatus.FREE)
                .build();
    }

    @Test
    public void createUserTest() {
        userService.saveUser(getUserDto());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void findByIdTest() {
        userService.findById(UUID.randomUUID());
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void findByChatIdTest() {
        userService.findByChatId(ThreadLocalRandom.current().nextLong());
        verify(userRepository, times(1)).findByChatId(any());
    }

    @Test
    public void isUserWriteQuestionTest() {
        var user = new UserEntity();
        user.setStatus(UserStatus.WRITING_QUESTION);
        when(userRepository.findByChatId(1L)).thenReturn(Optional.of(user));

        boolean isUserWriteQuestion = userService.isUserWriteQuestion(1L);

        verify(userRepository, times(1)).findByChatId(any());
        assertTrue(isUserWriteQuestion);
    }

}
