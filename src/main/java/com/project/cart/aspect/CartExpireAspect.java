package com.project.cart.aspect;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.project.cart.CartRedisProperty;
import com.project.cart.domain.CartRedis;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class CartExpireAspect {
	private final CartRedis cartRedis;
	private final long timeout;
	private final TimeUnit timeUnit;

	public CartExpireAspect(CartRedis cartRedis, CartRedisProperty cartRedisProperty) {
		this.cartRedis = cartRedis;
		this.timeout = cartRedisProperty.getTimeout();
		this.timeUnit = TimeUnit.valueOf(cartRedisProperty.getTimeUnit());
	}

	@Around("execution(* com.project.cart.service.CartService..*(..)) && args(key, ..)")
	public Object executeExpireByBasket(ProceedingJoinPoint joinPoint, Long key) throws Throwable {
		cartRedis.expireValues(key, timeout, timeUnit);
		Object result = joinPoint.proceed();
		cartRedis.expireValues(key, timeout, timeUnit);
		return result;
	}
}