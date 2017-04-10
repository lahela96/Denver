package com.colorado.denver.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.colorado.denver.services.SecurityServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityServiceImpl customAuthenticationProvider;

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.authenticationProvider(customAuthenticationProvider)
				.userDetailsService(auth.getDefaultUserDetailsService())
				.passwordEncoder(new BCryptPasswordEncoder());// This will probaply not work
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		/*
		 * http.authorizeRequests().antMatchers("/assests/**").permitAll()
		 * .antMatchers("/admin").access("hasRole('ADMIN')")
		 * .antMatchers("/member").access("hasRole('ADMIN') and hasRole('MEMBER')")
		 * .and()
		 * .formLogin().loginPage("/loginPage")
		 * .defaultSuccessUrl("/homePage")
		 * .failureUrl("/loginPage?error")
		 * .usernameParameter("userName").passwordParameter("password")
		 * .and().csrf()
		 * .and()
		 * .logout().logoutSuccessUrl("/loginPage?logout");
		 */

	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}