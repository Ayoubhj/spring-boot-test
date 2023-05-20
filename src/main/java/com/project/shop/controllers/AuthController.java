package com.project.shop.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shop.entity.RefreshToken;
import com.project.shop.entity.User;
import com.project.shop.entity.UserDetailsImpl;
import com.project.shop.enuM.Role;
import com.project.shop.exception.TokenRefreshException;
import com.project.shop.repo.RoleRepository;
import com.project.shop.repo.UserRepository;
import com.project.shop.request.LoginRequest;
import com.project.shop.request.SignupRequest;
import com.project.shop.request.TokenRefreshRequest;
import com.project.shop.response.JwtResponse;
import com.project.shop.response.MessageResponse;
import com.project.shop.response.TokenRefreshResponse;
import com.project.shop.security.jwt.JwtUtils;
import com.project.shop.services.RefreshTokenService;

import lombok.extern.slf4j.Slf4j;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
  
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		
		return ResponseEntity.ok(new JwtResponse(token, 
				                                 refreshToken.getToken(),
				 								 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}
	
	@PostMapping("/refreshtoken")
	  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
	    String requestRefreshToken = request.getRefreshToken();

	    return refreshTokenService.findByToken(requestRefreshToken)
	        .map(refreshTokenService::verifyExpiration)
	        .map(RefreshToken::getUser)
	        .map(user -> {
	        
	          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
	          
	          log.info("{}",token);
	          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
	        })
	        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
	            "Refresh token is not in database!"));
	  }
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));


		Set<com.project.shop.entity.Role> roles = new HashSet<>();


			com.project.shop.entity.Role userRole = roleRepository.findByName(Role.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
	

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
