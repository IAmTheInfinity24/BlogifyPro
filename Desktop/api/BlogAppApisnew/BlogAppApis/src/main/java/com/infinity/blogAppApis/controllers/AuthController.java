package com.infinity.blogAppApis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.blogAppApis.exceptions.InvalidUserException;
import com.infinity.blogAppApis.payloads.JwtAuthRequest;
import com.infinity.blogAppApis.payloads.JwtAuthResponse;
import com.infinity.blogAppApis.payloads.UserDto;
import com.infinity.blogAppApis.security.JwtTokenHelper;
import com.infinity.blogAppApis.services.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		
		this.Authenticate(request.getUsername(),request.getPassword());
		
		UserDetails username = userDetailsService.loadUserByUsername(request.getUsername());
	
		String token = this.jwtTokenHelper.genrateToken(username);
		
		JwtAuthResponse jwtAuthResponse =new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
	
	}
	
	private void Authenticate (String username,String password)throws Exception {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		} catch (BadCredentialsException e) {
			throw new InvalidUserException("User","username",username);
		}

	}
	
	@PostMapping("/register")
	ResponseEntity<UserDto> resgisterUser(@RequestBody UserDto userDto){
		
		UserDto registerUser = this.userService.registerUser(userDto);
		
		return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
	}
	
}
