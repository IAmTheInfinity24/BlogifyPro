package com.infinity.blogAppApis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.infinity.blogAppApis.entites.User;
import com.infinity.blogAppApis.exceptions.ResourceNotFoundException;
import com.infinity.blogAppApis.repo.UserRepo;
@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "UserName", username));
		
		return user;
	}

}
