package com.infinity.blogAppApis.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity.blogAppApis.entites.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	
}
