package com.assettracker.main.telegram_bot.database.service;

import com.assettracker.main.telegram_bot.config.security.AdminUserService;
import com.assettracker.main.telegram_bot.config.security.SecurityConfig;
import com.assettracker.main.telegram_bot.database.dto.UserQuestionDto;
import com.assettracker.main.telegram_bot.database.mapper.UserQuestionMapperImpl;
import com.assettracker.main.telegram_bot.database.repository.UserQuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({SecurityConfig.class, AdminUserService.class, BCryptPasswordEncoder.class,
        UserQuestionMapperImpl.class, UserQuestionService.class})
public class UserQuestionServiceTest {

    @Autowired
    UserQuestionService questionService;

    @MockitoBean
    UserQuestionRepository questionRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void saveTest() {
        questionService.save(new UserQuestionDto());

        verify(questionRepository, times(1)).save(any());
    }

    @Test
    public void findByUserIdTest_unauthorize() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> questionService.findByUserId(UUID.randomUUID()));
        verify(questionRepository, never()).findByUserId(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByUserIdTest_admin() {
        questionService.findByUserId(UUID.randomUUID());

        verify(questionRepository, times(1)).findByUserId(any());
    }

    @Test
    public void getAllUserQuestionsTest_unauthorize() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> questionService.getAllUserQuestions());
        verify(questionRepository, never()).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllUserQuestionsTest_admin() {
        questionService.getAllUserQuestions();

        verify(questionRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTestTest_unauthorize() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> questionService.findById(UUID.randomUUID()));
        verify(questionRepository, never()).findById(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByIdTestTest_admin() {
        questionService.findById(UUID.randomUUID());

        verify(questionRepository, times(1)).findById(any());
    }

    @Test
    public void deleteByIdTest_unauthorize() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> questionService.deleteById(UUID.randomUUID()));
        verify(questionRepository, never()).deleteById(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteByIdTest_admin() {
        questionService.deleteById(UUID.randomUUID());
        verify(questionRepository, times(1)).deleteById(any());
    }
}
