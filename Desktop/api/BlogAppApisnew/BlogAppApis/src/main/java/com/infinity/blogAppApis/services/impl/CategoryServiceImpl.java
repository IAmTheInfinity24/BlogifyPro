package com.infinity.blogAppApis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.blogAppApis.entites.Category;
import com.infinity.blogAppApis.exceptions.ResourceNotFoundException;
import com.infinity.blogAppApis.payloads.CategoryDto;
import com.infinity.blogAppApis.repo.CategoryRepo;
import com.infinity.blogAppApis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = this.mapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.mapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		this.categoryRepo.save(category);
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Long categoryId) {

		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {

		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> list = categories.stream().map(category->mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		return list;
	}

}
