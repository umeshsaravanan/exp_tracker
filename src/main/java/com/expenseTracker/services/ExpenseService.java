package com.expenseTracker.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenseTracker.models.Expenses;
import com.expenseTracker.repositories.ExpenseRepository;

@Service
public class ExpenseService {
	private final ExpenseRepository expenseRepository;
	
	@Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
	
	public Expenses saveUser(Expenses expense) {
        return expenseRepository.save(expense);
    }
	
	public List<Expenses> getExpensesForUserOnDate(LocalDate localDate, int userId) {
	    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    return expenseRepository.findByUserIdAndDate(userId, date);
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
