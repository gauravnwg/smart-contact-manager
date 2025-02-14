package com.scm.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserForm {
	@NotBlank(message = "name must be required")
	private String name;
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$" , message = "email is required")
	private String email;
	@NotBlank(message = "password must be required")
	@Size(min = 8)
	private String password;
	@NotBlank(message = "mobile number must be required")
	@Size(min = 8, max = 12)
	private String phoneNumber;
	@NotBlank(message = "write about your self !")
	private String about;
}
