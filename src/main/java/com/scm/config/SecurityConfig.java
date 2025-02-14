package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import com.scm.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
	@Autowired
	private OAuthenticationSuccessHandler handler ;

	@Autowired
	private SecurityCustomUserDetailService detailService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		// user details service object
		daoAuthenticationProvider.setUserDetailsService(detailService);
		// password encoder object
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// Authorization configuration
		httpSecurity.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/user/**").authenticated(); // "/user/**" requires authentication
			authorize.anyRequest().permitAll(); // All other requests are permitted
		});

		// Form login configuration

		httpSecurity.formLogin(formLogin -> {
			formLogin.loginPage("/logIn") // Custom login page
					.loginProcessingUrl("/authenticate") // Custom URL for login submission
					.successForwardUrl("/user/profile") // Redirect on successful login
					.usernameParameter("email") // Custom username parameter (email instead of 'username')
					.passwordParameter("password"); // Custom password parameter
		});
		
	    // Disable CSRF if necessary (be cautious with this)
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		
		 // Logout configuration
		httpSecurity.logout(logoutForm -> {
			logoutForm.logoutUrl("/logout");
			logoutForm.logoutSuccessUrl("/logIn?logout=true");
		});
		
		 // OAuth2 Login configuration
//		httpSecurity.oauth2Login(Customizer.withDefaults());
		httpSecurity.oauth2Login(oauth ->{ 
			  oauth.loginPage("/logIn") 
			  .successHandler(handler);
		});
		

		return httpSecurity.build();
	}
}
