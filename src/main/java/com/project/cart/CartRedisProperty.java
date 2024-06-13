package com.project.cart;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "cart")
public class CartRedisProperty {
	private final long timeout;
	private final String timeUnit;
}
