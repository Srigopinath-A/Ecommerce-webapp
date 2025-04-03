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

	    String token = getTokenFromRequest(request);
	    if (token != null) {
	    	String username = jwtutils.getUsernameFromToken(token);

            UserDetails userDetails = customuserdetailservice.loadUserByUsername(username);

            if (StringUtils.hasText(username) && jwtutils.isTokenValid(token, userDetails)){
                log.info("VALID JWT FOR {}", username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		// first getting token from the header
		String token = request.getHeader("Authorization");
		// beare token is used for validation and along we can check it on the post man
		// StringUtils.startsWithIgnoreCase(token, "Bearer ") this is case sensitive 
		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7);
		}
		return null;
	}
	  

}
