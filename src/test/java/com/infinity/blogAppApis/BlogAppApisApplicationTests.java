package com.infinity.blogAppApis;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Test
	void getEncodedPassword() {
		System.err.println(this.passwordEncoder.encode("sakshi123"));
	}

	@Test
	public void testMapper() {
		System.out.println(mapper);
	}
}
