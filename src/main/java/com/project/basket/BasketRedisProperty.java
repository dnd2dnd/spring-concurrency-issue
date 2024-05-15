package com.project.basket;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "basket")
public class BasketRedisProperty {
	private final long timeout;
	private final String timeUnit;
}
