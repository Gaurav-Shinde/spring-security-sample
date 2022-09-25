package com.gaurav.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.gaurav.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

	@Order(1)
	@Configuration
	public static class InMemSecurityConfig extends WebSecurityConfigurerAdapter{
		@Override
		public void configure(final AuthenticationManagerBuilder auth) throws Exception {
			 auth.inMemoryAuthentication().withUser("customer").password("password").roles("CUSTOMER")
	                         .and().withUser("admin").password("password").roles("ADMIN");
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/in-mem/course/**").hasRole("CUSTOMER")
			.antMatchers(HttpMethod.POST, "/in-mem/course/**").hasRole("ADMIN")
			.and().httpBasic();
		}
	}
	@Order(2)
	@Configuration
	public static class UserRepoSecurityConfig extends WebSecurityConfigurerAdapter{
		
		@Autowired
		private UserDetailsService userDetailsService;
		
		@Override
		public void configure(final AuthenticationManagerBuilder auth) throws Exception {
			 auth.userDetailsService(userDetailsService);
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/user-repo/course/**").hasRole("CUSTOMER")
			.antMatchers(HttpMethod.POST, "/user-repo/course/**").hasRole("ADMIN")
			.and().httpBasic();
		}
	}
	
	
}
