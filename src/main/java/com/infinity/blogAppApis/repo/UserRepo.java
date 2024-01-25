package com.infinity.blogAppApis.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity.blogAppApis.entites.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String username);


}
