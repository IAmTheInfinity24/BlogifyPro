package com.infinity.blogAppApis.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity.blogAppApis.entites.Role;

public interface RoleRepo extends JpaRepository<Role,Long> {

}
