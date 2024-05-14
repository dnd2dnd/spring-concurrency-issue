package com.project.basket.domain;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BasketRedis {
	private final RedisTemplate<Long, Long> redisTemplate;
	@Getter
	private final HashOperations<Long, Long, BasketProduct> hashOperations;

	public BasketRedis(RedisTemplate<Long, Long> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOperations = redisTemplate.opsForHash();
	}

	public void expireValues(Long key, long timeout, TimeUnit timeUnit) {
		log.info("Redis 기존 시간, Key : {}, ExpiredTime(ms) : {}", key, redisTemplate.getExpire(key));
		redisTemplate.expire(key, timeout, timeUnit);
		log.info("Redis 만료시간 갱신, Key : {} , Type : {}, ExpiredTime : {}", key, timeUnit, timeout);
	}
}
