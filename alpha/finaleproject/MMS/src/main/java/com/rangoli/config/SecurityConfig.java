package com.rangoli.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;

@Configuration

@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    http.csrf().disable()
	        .authorizeRequests()
	        .antMatchers(
	        		
	                "/api/users/register",
	                "/api/users/login",
	                "/api/admin/login",
	                "/api/users/forgot-password",
	                "/api/users/reset-password"
	        ).permitAll()
	        .antMatchers("/api/users/unlock").hasRole("ADMIN")
	        .antMatchers("/api/admin/**").hasRole("ADMIN")
	        .antMatchers("/api/auth/**").permitAll()
	        .antMatchers(HttpMethod.DELETE,"/api/admin/users/**").hasRole("ADMIN")
	        .anyRequest().authenticated();

	    http.addFilterBefore(jwtAuthenticationFilter,
	            UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
}



