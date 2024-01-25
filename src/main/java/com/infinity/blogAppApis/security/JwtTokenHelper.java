package com.infinity.blogAppApis.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtTokenHelper{

	public static final long JWT_TOKEN_VALIDITY = 604800000;

	private String secreatKey = "jwtTokenKey";

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimFromToken(token);

		return claimResolver.apply(claims);
	}

	public Claims getAllClaimFromToken(String token) {
		return Jwts.parser().setSigningKey(secreatKey).parseClaimsJws(token).getBody();
	}

	public boolean isTokenexpired(String Token) {
		final Date expiration = getExpirationDateFromToken(Token);
		return expiration.before(new Date());
	}

	public String genrateToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secreatKey).compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenexpired(token));
	}
}
