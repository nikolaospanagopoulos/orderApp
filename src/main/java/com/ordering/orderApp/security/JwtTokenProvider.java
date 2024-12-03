package com.ordering.orderApp.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import com.ordering.orderApp.exceptions.InvalidJwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtTokenProvider {
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		// System.out.println(authentication.getCredentials().toString() + "!!!!!!");
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);
		return Jwts.builder().subject(username).issuedAt(new Date()).expiration(expirationDate).signWith(key())
				.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
			return true;
		} catch (MalformedJwtException ex) {
			throw new InvalidJwtException("Jwt Malformed");
		} catch (ExpiredJwtException e) {
			throw new InvalidJwtException("Jwt Expired");
		} catch (UnsupportedJwtException ex) {
			throw new InvalidJwtException("Jwt Unsupported");
		} catch (IllegalArgumentException ex) {
			throw new InvalidJwtException("Jwt null or empty");
		}
	}

	public String getUsername(String token) {
		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
}
