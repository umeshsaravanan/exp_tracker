package com.expenseTracker.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.models.Expenses;
import com.expenseTracker.services.ExpenseService;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
	
	private final ExpenseService expenseService;
	
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@GetMapping("/expenses/{date}")
    public List<Expenses> getExpenses(@PathVariable String date, HttpServletRequest request) {
        int userId = expenseService.getUserIdFromCookies(request);
        LocalDate localDate = LocalDate.parse(date);
        System.out.println(localDate);
        return expenseService.getExpensesForUserOnDate(localDate, userId);
    }

    @PostMapping("/addExpense")
    public Expenses addExpense(@RequestBody Expenses expense, HttpServletRequest request) {
    	int userId = expenseService.getUserIdFromCookies(request);
    	expense.setUserId(userId);
    	return expenseService.saveUser(expense);
    }
}
