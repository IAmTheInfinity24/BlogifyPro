 package com.infinity.blogAppApis;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.infinity.blogAppApis.entites.Role;
import com.infinity.blogAppApis.repo.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Value("${ADMIN_USER}")
	Long v1;
	
	@Value("${NORMAL_USER}")
	Long v2;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	 PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		
		try {
			Role role = new Role();
			role.setId(v1);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(v2);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = Arrays.asList(role,role1);
			List<Role> savedRoles = this.roleRepo.saveAll(roles);
			
			savedRoles.forEach(r->System.out.println(r.getName()));
		} catch (Exception e) {

		e.printStackTrace();
		}
	}
}
