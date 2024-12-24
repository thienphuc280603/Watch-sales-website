package com.poly.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
	    .authorizeRequests()
	        .antMatchers("/**", "/oauth2/**", "/login-google","/api/putProduct","/api/orders").permitAll()
	        .anyRequest().authenticated()
	        .and()
	    .formLogin()
	        .loginPage("/login")
	        .defaultSuccessUrl("/")
	        .failureUrl("/login?error")
	        .permitAll()
	        .and()
	        .oauth2Login()
	        .loginPage("/login/oauth2/code/google")
	        .defaultSuccessUrl("/login-google");
	}

}