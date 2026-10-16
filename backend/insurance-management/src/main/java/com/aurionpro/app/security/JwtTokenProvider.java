package com.aurionpro.app.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

		return Jwts.builder()
				.subject(username)
				.issuedAt(currentDate)
				.expiration(expireDate)
				.claim("role", authentication.getAuthorities())
				.signWith(key())
				.compact();
	}

	private SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
			return true;
		} catch (MalformedJwtException e) {
			throw new IllegalArgumentException("Invalid Jwt Token", e);
		} catch (ExpiredJwtException e) {
			throw new IllegalArgumentException("Expired Jwt Token", e);
		} catch (UnsupportedJwtException e) {
			throw new IllegalArgumentException("Unsupported Jwt Token", e);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
		return claims.getSubject();
	}
}