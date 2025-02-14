package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		logger.info("OAuthenticationSuccessHandler");

		// Identify the provider
		var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
		String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
		logger.info(authorizedClientRegistrationId);

		var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
		oauthUser.getAttributes().forEach((key, value) -> {
			logger.info(key + " ->" + value);
		});

		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setRoleList(List.of(AppConstants.Roll_USER));
		user.setEmailVerified(true);
		user.setEnabled(true);

		// Mobile number generation
		long randomPhoneNumber = (long) (Math.random() * 9000000000L) + 1000000000L;
		String phoneNumber = String.format("+91 %d", randomPhoneNumber); // Example phone number format
		user.setPhoneNumber(phoneNumber);

		// OAuth provider logic
		if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
			user.setEmail(oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : "");
			user.setName(oauthUser.getAttribute("name") != null ? oauthUser.getAttribute("name").toString() : "");
			user.setProfilePic(oauthUser.getAttribute("picture") != null ? oauthUser.getAttribute("picture").toString()
					: "/default-pic.jpg");
			user.setProviderUserId(oauthUser.getName());
			user.setPassword("Radhe Radhe");
			user.setAbout("This account was created by Google");
			user.setProvider(Providers.GOOGLE);

		} else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
			String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
					: oauthUser.getAttribute("login") != null
							? oauthUser.getAttribute("login").toString() + "@gmail.com"
							: "";
			String picture = oauthUser.getAttribute("avatar_url");
			String name = oauthUser.getAttribute("login");
			String providerUserId = oauthUser.getName();

			user.setEmail(email);
			user.setName(name);
			user.setProfilePic(picture != null ? picture : "/default-pic.jpg");
			user.setProviderUserId(providerUserId);
			user.setPassword("Radhe Krishna");
			user.setAbout("This account was created by GitHub");
			user.setProvider(Providers.GitHub);

		} else {
			logger.info("OAuthenticationSuccessHandler: Unknown provider");
		}

		try {
			User existingUser = this.userRepository.findByEmail(user.getEmail()).orElse(null);
			if (existingUser == null) {
				this.userRepository.save(user);
				logger.info("User saved successfully: " + user.getEmail());
			}
		} catch (Exception e) {
			logger.error("Error saving user: " + user.getEmail(), e);
		}

		// Redirect to the profile page
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}
}
