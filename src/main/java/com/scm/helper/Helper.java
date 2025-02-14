package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public class Helper {

	public static String getEmailLoggedInUser(Authentication authentication) {

		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
			String clientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
			OAuth2AuthenticatedPrincipal principal = oauth2AuthenticationToken.getPrincipal();

			String username = "";

			if (clientId.equalsIgnoreCase("google")) {
				System.out.println("Getting Email from Google");
				// Assuming email is in the "email" attribute from Google OAuth2 response
				username = principal.getAttribute("email").toString();
			} 
			else if (clientId.equalsIgnoreCase("github")) {
				System.out.println("Getting Email from GitHub");
				// Assuming email is in the "email" attribute from GitHub OAuth2 response
				username = principal.getAttribute("email") != null ? principal.getAttribute("email").toString()
						: principal.getAttribute("login") != null
								? principal.getAttribute("login").toString() + "@gmail.com"
								: "";
			}
			return username;

		}

		// Return an empty string or handle the case where the authentication is not
		// OAuth2

		else {
			System.out.println("Getting data from local database");
			// If not Google or GitHub, returning the name of the authenticated user.
			return authentication.getName();
		}
	}
}
