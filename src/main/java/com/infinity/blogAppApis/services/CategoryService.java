package com.infinity.blogAppApis.services;

import java.util.List;

import com.infinity.blogAppApis.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto,Long categoryid );
	
	void deleteCategory(Long categoryId);
	
	CategoryDto getCategory(Long categoryId);
	
	List<CategoryDto> getAllCategories();
}
