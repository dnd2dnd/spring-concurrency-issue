package com.project.auth;

import static io.jsonwebtoken.security.Keys.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.project.auth.config.JWTCredentials;
import com.project.auth.exception.TokenExpiredException;
import com.project.auth.exception.TokenInvalidException;
import com.project.member.dto.response.TokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTProvider {

	private final SecretKey key;
	private final long accessTokenExpirationTime;

	public JWTProvider(JWTCredentials jwtCredentials) {
		this.key = hmacShaKeyFor(jwtCredentials.getSecretKey().getBytes(UTF_8));
		this.accessTokenExpirationTime = jwtCredentials.getAccessTokenExpirationTime();
	}

	public TokenResponse createAccessToken(Long memberId) {
		String accessToken = createToken(memberId.toString(), accessTokenExpirationTime, key);
		return new TokenResponse(accessToken);
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


	public Long getPayload(String token) {
		return Long.parseLong(validateParseJws(token).getBody().getSubject());
	}

	public Jws<Claims> validateParseJws(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		} catch (JwtException e) {
			throw new TokenInvalidException();
		}
	}

}
