package com.infinity.blogAppApis.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infinity.blogAppApis.entites.Role;
import com.infinity.blogAppApis.entites.User;
import com.infinity.blogAppApis.exceptions.ResourceNotFoundException;
import com.infinity.blogAppApis.payloads.UserDto;
import com.infinity.blogAppApis.repo.RoleRepo;
import com.infinity.blogAppApis.repo.UserRepo;
import com.infinity.blogAppApis.services.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Value("${ADMIN_USER}")
	Long v1;
	
	@Value("${NORMAL_USER}")
	Long v2;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
		
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserRepo userRepo; 

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToEntity(userDto);
		User savedUser = this.repo.save(user);

		return this.entityToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long id) {

		User user = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "d", id));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User savedUser = this.repo.save(user);
		return this.entityToDto(savedUser);

	}

	@Override
	public UserDto getUserById(Long id) {

		User user = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

		return this.entityToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.repo.findAll();

		return users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Long id) {

		User user = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

		this.repo.delete(user);
	}

	public User dtoToEntity(UserDto dto) {
		User user =this.mapper.map(dto, User.class);
//		user.setId(dto.getId());
//		user.setName(dto.getName());
//		user.setEmail(dto.getEmail());
//		user.setPassword(dto.getPassword());
//		user.setAbout(dto.getAbout());

		return user;

	}

	public UserDto entityToDto(User user) {
		UserDto dto = this.mapper.map(user, UserDto.class);
//		dto.setId(user.getId());
//		dto.setName(user.getName());
//		dto.setEmail(user.getEmail());
//		dto.setPassword(user.getPassword());
//		dto.setAbout(user.getAbout());

		return dto;
	}

	@Override
	public UserDto registerUser(UserDto dto) {

		User user = this.mapper.map(dto, User.class);
		
		user.setPassword(this.encoder.encode(user.getPassword()));
		
		Role role = this.roleRepo.findById(v2).get();
		
		user.getRoles().add(role);
		
		this.userRepo.save(user);
		return this.mapper.map(user, UserDto.class);
	}

}
