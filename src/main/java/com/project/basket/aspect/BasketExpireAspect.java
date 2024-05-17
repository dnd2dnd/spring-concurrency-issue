package com.project.basket.aspect;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.basket.domain.BasketRedis;
import com.project.basket.BasketRedisProperty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class BasketExpireAspect {
	private final BasketRedis basketRedis;
	private final long timeout;
	private final TimeUnit timeUnit;

	public BasketExpireAspect(BasketRedis basketRedis, BasketRedisProperty basketRedisProperty) {
		this.basketRedis = basketRedis;
		this.timeout = basketRedisProperty.getTimeout();
		this.timeUnit = TimeUnit.valueOf(basketRedisProperty.getTimeUnit());
	}

	@Around("execution(* com.project.basket.service.BasketRedisUtil..*(..)) && args(key, ..)")
	public Object executeExpireByBasket(ProceedingJoinPoint joinPoint, Long key) throws Throwable {
		basketRedis.expireValues(key, timeout, timeUnit);
		Object result = joinPoint.proceed();
		basketRedis.expireValues(key, timeout, timeUnit);
		return result;
	}
}
