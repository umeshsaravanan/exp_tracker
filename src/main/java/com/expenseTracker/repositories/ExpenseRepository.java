package com.expenseTracker.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expenseTracker.models.Expenses;

public interface ExpenseRepository extends JpaRepository<Expenses, Integer> {

	@Query("SELECT e FROM Expenses e WHERE DATE(e.addedAt) = :date AND e.userId = :userId")
	List<Expenses> findByUserIdAndDate(@Param("userId") int userId, 
	                                   @Param("date") Date date);	
}
