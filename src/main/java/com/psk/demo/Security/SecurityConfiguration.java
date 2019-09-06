package com.psk.demo.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity
//			.authorizeRequests()
//				.antMatchers("/**").permitAll()
//				.anyRequest().authenticated().and()
//			.cors().and()
//			.csrf().disable()
//			.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//			.headers()
//				.frameOptions()
//				.sameOrigin()
//				.cacheControl();
	}
}
