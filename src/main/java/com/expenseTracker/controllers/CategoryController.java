package com.expenseTracker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.models.Category;
import com.expenseTracker.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/categories")
	public List<Category> getCategories(HttpServletRequest request) {
		int userId = categoryService.getUserIdFromCookies(request);
		return categoryService.getCategory(userId);
	}
	
	@PostMapping("/add")
	public Category addCategory(@RequestBody Category category, HttpServletRequest request) {
		int userId = categoryService.getUserIdFromCookies(request);
		category.setUserId(userId);
		return categoryService.saveCategory(category);
	}
	
}
