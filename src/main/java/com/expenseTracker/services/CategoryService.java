package com.expenseTracker.services;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenseTracker.models.Category;
import com.expenseTracker.repositories.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}
	
	public List<Category> getCategory(int userId){
		return categoryRepository.findAllByUserIdOrGlobal(userId);
	}
	
	public int getUserIdFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("userId".equals(cookie.getName())) {
	                try {
	                    return Integer.parseInt(cookie.getValue());
	                } catch (NumberFormatException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    return -1;
	}
}
