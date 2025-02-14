/*
 * package com.scm.config;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.core.userdetails.User; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.security.provisioning.InMemoryUserDetailsManager;
 * 
 * @Configuration public class SecurityConfrigation {
 * 
 * // Bean to provide password encoder
 * 
 * @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();
 * // Stronger password encoding }
 * 
 * // Define UserDetailsService with in-memory authentication
 * 
 * @Bean UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
 * { // Create users with encoded passwords UserDetails user1 =
 * User.builder().username("admin").password(passwordEncoder.encode("admin123"))
 * .roles("ADMIN", "USER").build();
 * 
 * UserDetails user2 =
 * User.builder().username("user").password(passwordEncoder.encode("user123")).
 * roles("USER") .build();
 * 
 * // InMemoryUserDetailsManager manages the users return new
 * InMemoryUserDetailsManager(user1, user2); } }
 */