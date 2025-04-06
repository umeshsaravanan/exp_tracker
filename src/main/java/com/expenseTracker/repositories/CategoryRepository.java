package com.expenseTracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expenseTracker.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	@Query("SELECT c FROM Category c WHERE c.userId IS NULL OR c.userId = :userId")
	List<Category> findAllByUserIdOrGlobal(@Param("userId") Integer userId);
}
