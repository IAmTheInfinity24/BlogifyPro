package com.infinity.blogAppApis.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.infinity.blogAppApis.entites.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDto {

	private long id;

	private String name;

	private String email;

	private String password;

	private String about;
	
	Set<RoleDto> roles = new HashSet<>();

}
