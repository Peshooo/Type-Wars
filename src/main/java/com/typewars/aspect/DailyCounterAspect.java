package com.typewars.aspect;

import com.typewars.service.stats.DailyCountersService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class DailyCounterAspect {
    private final DailyCountersService dailyCountersService;

    public DailyCounterAspect(DailyCountersService dailyCountersService) {
        this.dailyCountersService = dailyCountersService;
    }

    @Around("execution(* *(..)) && @annotation(dailyCounter)")
    public Object invoke(ProceedingJoinPoint proceedingJoinPoint, DailyCounter dailyCounter) {
        dailyCountersService.count(dailyCounter.key());

        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
