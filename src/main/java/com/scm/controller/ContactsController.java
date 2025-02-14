package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.forms.ContactForm;
import com.scm.services.ContactServices;
import com.scm.services.ImageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contact") // Mapping the controller to the /user/contact
									// URL path
public class ContactsController {

	private Logger logger = LoggerFactory.getLogger(ContactsController.class); // Logger
																				// to
																				// log
																				// information

	@Autowired
	private ImageService imageService; // Injecting the ImageService to handle
										// image uploads

	private ContactServices contactServices; // Service to handle operations
												// related to contact entities

	// Constructor-based dependency injection to inject ContactServices
	public ContactsController(ContactServices contactServices) {
		super();
		this.contactServices = contactServices;
	}

	// GET request to show the contact form
	@GetMapping("/show")
	public String showContactForm(Model model) {
		ContactForm contactForm = new ContactForm(); // Initialize an empty form
														// object
		model.addAttribute("contactForm", contactForm); // Add the form to the
														// model for the view
		System.out.println(contactForm); // Debugging the contact form object
		return "user/add_contact"; // Return the view to render the contact form
	}

	// POST request to process the contact form submission
	@PostMapping("/process-register")
	public String processContactRegister(
			@Valid @ModelAttribute ContactForm contactForm, // Validates the
															// contact form
															// input
			BindingResult result, // Holds validation errors, if any
			HttpSession session) { // Session to store temporary data like
									// messages

		// Check if there were validation errors
		if (result.hasErrors()) {
			session.setAttribute("message", "please correct errors"); // Store
																		// error
																		// message
																		// in
																		// session
			System.out.println(result.getAllErrors()); // Log all errors for
														// debugging
			return "user/add_contact"; // Return the form view if there are
										// errors
		}

		// If no errors, proceed with the image processing
		logger.info("image details : {}",
				contactForm.getPicture().getOriginalFilename()); // Log image
																	// details

		// Call imageService to upload the image and get the file URL
		String fileURL = imageService.uploadImage(contactForm.getPicture());

		// Create a new Contact entity and populate it with data from the form
		Contact contact = new Contact();
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getContactAddress());
		contact.setDescription(contactForm.getContactDescription());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setFavorite(contactForm.isFavorite());
		contact.setPicture(fileURL); // Set the uploaded image URL

		// Add the new contact to the contact service, which will handle
		// persistence
		Contact contact2 = this.contactServices.addContact(contact);
		System.out.println("Contact Saved Successfully" + contact2); // Log
																		// successful
																		// contact
																		// save

		// Store a success message in the session
		session.setAttribute("message",
				"You Have Added a New Contact Successfully");

		// Redirect the user back to the contact form after successful
		// submission
		return "redirect:/user/contact/show"; // Redirect to show contact form
												// again
	}

	// view contacts
	
	@GetMapping("/view")
	public String viewContact() {
		
		return "user/view_contact";
	}
	
	
	
	
	
	
	
	
	
	
	

}
