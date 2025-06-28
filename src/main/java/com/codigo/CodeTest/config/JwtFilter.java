package com.codigo.CodeTest.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codigo.CodeTest.dto.ErrorResponse;
import com.codigo.CodeTest.serviceimpl.UserDetailsServiceImpl;
import com.codigo.CodeTest.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getRequestURI();
		if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
		    filterChain.doFilter(request, response);
		    return;
		}
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String email = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				email = jwtUtil.extractEmail(token);
			} catch (ExpiredJwtException ex) {
				// Handle expired JWT
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				ErrorResponse error = new ErrorResponse();
		        error.setStatus(401);
		        error.setMessageList(List.of("JWT Token is expired!"));
				response.getWriter().write(new ObjectMapper()
						.writeValueAsString(error));
				return;
			} catch (JwtException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				ErrorResponse error = new ErrorResponse();
		        error.setStatus(401);
		        error.setMessageList(List.of("Invalid JWT Token"));
				response.getWriter().write(new ObjectMapper()
						.writeValueAsString(error));
			}
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
			if (jwtUtil.validateToken(token)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
