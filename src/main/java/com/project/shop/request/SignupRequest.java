package com.project.shop.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {

	private String username;
	private String email;
	private String password;
	
	
}
