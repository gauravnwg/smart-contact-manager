package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ScmController {
	@Autowired
	private UserServices userServices;

	@GetMapping("/about")
	public String aboutPage() {
		return "about";
	}

	@GetMapping("/")
	public String index() {
		return "home";
	}

	// home
	@GetMapping("/home")
	public String homePage() {
		return "home";
	}

	// services
	@GetMapping("/services")
	public String servicesPage() {
		return "services";
	}

	// contact
	@GetMapping("/contact")
	public String contactPage() {
		return "contact";
	}

	// login
	@GetMapping("/logIn")
	public String loginPage() {
		return "login";
	}

//	@GetMapping("/logout")
//	public String logoutPage() {
//		return "redirect:/logIn";
//	}

	// register
	@GetMapping("/register")
	public String registerPage(Model m) {
		UserForm userform = new UserForm();
		m.addAttribute("userForm", userform);
		return "register";
	}

	// processing register
	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult result, HttpSession session) {
		System.out.println("processing Register");

		// validation
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			return "register";
		}

		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setAbout(userForm.getAbout());
		user.setProfilePic(
				"https://www.freepik.com/free-vector/user-circles-set_145856997.htm#fromView=keyword&page=1&position=12&uuid=e114f781-e8b4-4fbe-81dc-f5915363eade&new_detail=true");
		user.setEmailVerified(false);
		user.setPhoneVerified(true);
		user.setProviderUserId(null);
		user.setEnabled(true);
		User saveUser = this.userServices.saveUser(user);

		// add message
		System.out.println(user);
		session.setAttribute("message", "User Added Successfully");
		System.out.println("user successfully saved" + saveUser);

		return "redirect:/register";

	}
}
