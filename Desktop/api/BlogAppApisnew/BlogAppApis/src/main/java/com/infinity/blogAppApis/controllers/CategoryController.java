package com.infinity.blogAppApis.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.blogAppApis.payloads.ApiResponse;
import com.infinity.blogAppApis.payloads.CategoryDto;
import com.infinity.blogAppApis.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId) {

		CategoryDto category = this.service.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> categories = this.service.getAllCategories();

		return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);

	}

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto dto=this.service.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(dto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Long categoryId){
	CategoryDto updatedCategory = this.service.updateCategory(categoryDto, categoryId);
	return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
		this.service.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted",true,new Date()),HttpStatus.OK);
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
