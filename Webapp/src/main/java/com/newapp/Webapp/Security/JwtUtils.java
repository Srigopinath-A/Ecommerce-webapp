package com.newapp.Webapp.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.newapp.Webapp.Entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j // it automatically generates a logger field in the class so more concise logging code without creating it manually .
public class JwtUtils {
	
	// first we have to create a jwt token expiring time
	// millisec * Minute * Hour * Day * Month .
	private static final long Expiring_Time_Milliec = 1000L * 60L * 60L * 24L* 30L * 6L;// token will expire in 6 months.
	// we going to create a secretKey key
	private SecretKey key ;
	
	
	// always make user that your value is coming from the spring.beans.factory.annotation.value.
	@Value("${secretejwtString}")
	private String secretejwtString;// it is in application properties 
	
	// when an bin of this jwtutil is created secrete key has to be initilaized so we are using the below
	@PostConstruct
	private void init() {
		// this constructor will assign a value to the key
		byte[] keybytes = secretejwtString.getBytes(StandardCharsets.UTF_8);// this line will convert String to byte
		this.key = new SecretKeySpec(keybytes,"HmacSHA256");// Hash-based Message Authentication code using sha256
	}
	
	public String generateToken(User user) {
		String username = user.getEmail();
		return generateToken(username);
	}
	
	//this is to generate the token and check it 
	public String generateToken (String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()* Expiring_Time_Milliec))
				.signWith(key)
				.compact();
	}
	// we going to validate the token .
	public String getUsernameFromToken(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	private <T> T extractClaims(String token,Function<Claims,T> claimsTFunction) {
		return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
	}
	
	public boolean isTokenValid(String token,UserDetails userdetails) {
		final String username = getUsernameFromToken(token);	
		return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
		}
	
	private boolean isTokenExpired(String token) {
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}
}
