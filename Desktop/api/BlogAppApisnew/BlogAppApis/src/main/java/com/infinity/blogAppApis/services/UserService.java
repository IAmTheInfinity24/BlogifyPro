package com.infinity.blogAppApis.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infinity.blogAppApis.payloads.UserDto;

public interface UserService {

	UserDto registerUser(UserDto dto);
	public UserDto createUser(UserDto user);
	public UserDto updateUser(UserDto user,Long id);
	public UserDto getUserById(Long id);
	List<UserDto> getAllUsers();
	void deleteUser(Long id);
}
