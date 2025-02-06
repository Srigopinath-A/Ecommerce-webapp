package com.newapp.Webapp.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig { // this acts as a final security check

	private final JwtAuthFilter jwtauthfilter;
	
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity httpsecurity) throws Exception{
		httpsecurity.csrf(AbstractHttpConfigurer::disable)// this is default configuration
		.cors(Customizer.withDefaults())
		.authorizeHttpRequests( request -> request // matching of the request has been checked 
		.requestMatchers("/auth/","/category/**","/product/**","/order/**").permitAll() // this does not authenticate user accessing this it just permitall
		.anyRequest().authenticated())
		.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtauthfilter, UsernamePasswordAuthenticationFilter.class);
	return httpsecurity.build();
		}
	
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationmanager(AuthenticationConfiguration authenticationconfiguration) throws Exception {
		return authenticationconfiguration.getAuthenticationManager();
	}
}
