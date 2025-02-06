package com.newapp.Webapp.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Loginrequest {
	
	
	@NotBlank(message = "Email should not be Empty")
	private String email;
	@NotBlank(message = "Password should not be Empty")
	private String password;
}