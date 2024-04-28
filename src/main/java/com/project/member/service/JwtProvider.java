package com.project.member.service;

import static io.jsonwebtoken.security.Keys.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.project.member.JwtCredentials;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	private final SecretKey key;
	private final long accessTokenExpirationTime;

	public JwtProvider(JwtCredentials jwtCredentials) {
		this.key = hmacShaKeyFor(jwtCredentials.getSecretKey().getBytes(UTF_8));
		this.accessTokenExpirationTime = jwtCredentials.getAccessTokenExpirationTime();
	}

	public String createAccessToken(Long memberId) {
		return createToken(memberId.toString(), accessTokenExpirationTime, key);
	}

	private String createToken(String payload, long expireLength, SecretKey key) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expireLength);
		return Jwts.builder()
			.setSubject(payload)
			.setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}
}
