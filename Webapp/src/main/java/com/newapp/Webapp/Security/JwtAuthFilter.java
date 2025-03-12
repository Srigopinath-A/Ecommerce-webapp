package com.newapp.Webapp.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtils jwtutils;
	private final CustomUserDetailService customuserdetailservice;

	// this will be doing internal filter
	// this will execute only once per request with a single request processing
	// cycle
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// this is for extracting token from our request
		// when ever user reaching us with the end point we have get the token and
		// validate it
		String token = getTokenFromRequest(request);
		if (token != null) {
			String username = jwtutils.getUsernameFromToken(token);
			UserDetails userdetails = customuserdetailservice.loadUserByUsername(username);
			if (StringUtils.hasText(username) && jwtutils.isTokenValid(token, userdetails)) {
				log.info("valid jwt for {}", username);

				UsernamePasswordAuthenticationToken authenticationtoken = new UsernamePasswordAuthenticationToken(
						userdetails, null, userdetails.getAuthorities());
				authenticationtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationtoken);
			}
		}
		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		// first getting token from the header
		String token = request.getHeader("Authorization");
		// beare token is used for validation and along we can check it on the post man
		if (StringUtils.hasText(token) && StringUtils.startsWithIgnoreCase(token, "Beares")) {
			return token.substring(7);
		}
		return null;
	}

}
