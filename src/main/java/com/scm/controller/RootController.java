package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserServices;

@ControllerAdvice
public class RootController {
	@Autowired
	private UserServices userServices;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ModelAttribute
	public void addLoggedIUserInformation(Model model, Authentication authentication) {
		if (authentication == null) {
			return;
		}

		String userName = Helper.getEmailLoggedInUser(authentication);
		User user = userServices.getUserByEmail(userName);
		logger.info("logged In User Name : {}", user.getName());
		logger.info("logged In User Email : {}", user.getEmail());
		model.addAttribute("loggedInUser", user);

	}
}
