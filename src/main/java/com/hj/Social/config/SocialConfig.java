package com.hj.Social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SocialConfig {
	// extends WebSecurityConfigurerAdapter
	
	
	@Bean
	public PasswordEncoder getPasswordEncord() {
		return new BCryptPasswordEncoder();
	}
	
//	@Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests()
//            .antMatchers("/admin").hasRole("admin")
//            .antMatchers("/board").hasRole("user")
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .defaultSuccessUrl("/")
//            .permitAll()
//            .and()
//            .logout()
//            .logoutRequestMatcher(new AntPathRequestMatcher("/social/logout"))
//            .logoutSuccessUrl("/")
//            .invalidateHttpSession(true)
//            .and()
//            .exceptionHandling()
//            .accessDeniedPage("/social/loginPage");
//        httpSecurity.csrf().disable();
//    }

} //class
