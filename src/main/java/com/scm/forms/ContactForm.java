
package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@ToString
@Builder
public class ContactForm {
	@NotBlank(message = "not blank")
	private String name;
	@Email(message = "Please provide a valid email address")
	private String email;
	@Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "enter valid phone number")
	private String phoneNumber;

	private String contactAddress;

	private String contactDescription;

	private String websiteLink;

	private String linkedInLink;
	
	private MultipartFile picture;
	
	private boolean favorite = true;
}
