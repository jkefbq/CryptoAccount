package com.cryptodemoaccount.aop;

import com.cryptodemoaccount.event.MessageEvent;
import com.cryptodemoaccount.database.service.UserCoinService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class MessageEventHandlerInterceptor {

    private final UserCoinService userCoinService;

    @Before("!execution(* com.cryptodemoaccount.events.MessageEventHandler.handleUnknown(..)) &&" +
            "execution(* com.cryptodemoaccount.events.MessageEventHandler.*(..))")
    public void checkIsWaitingNumber(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long chatId = null;
        for (Object arg : args) {
            if (arg instanceof MessageEvent) {
                chatId = ((MessageEvent) arg).getUpdateDto().getChatId();
            }
        }
        if (chatId == null) {
            throw new IllegalStateException("cannot recognize " + joinPoint.getSignature().getName() +
                    " method args=" + args + " [lost ButtonEvent or AssetButtonEvent type]");
        }
        userCoinService.deleteByChatId(chatId);
    }
}
