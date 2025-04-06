package com.expenseTracker.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expenses {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="exp_id")
	private int expenseId;

	@Column(name="exp_name")
	private String expenseName;

	@Column(name="category_id")
	private int categoryId;

	@Column(name="amount")
	private float amount;

	@Column(name="added_at")
	private Timestamp addedAt;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="type")
	private String type;
	
	public Expenses() {
		
	}
	
	public Expenses(int expenseId, String expenseName, int categoryId, float amount, Timestamp addedAt, int userId, String type) {
		this.expenseId = expenseId;
		this.expenseName = expenseName;
		this.categoryId = categoryId;
		this.amount = amount;
		this.addedAt = addedAt;
		this.userId = userId;
		this.type = type;
	}
	
	public int getExpenseId() {
		return expenseId;
	}
	
	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}
	
	public String getExpenseName() {
		return expenseName;
	}
	
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public Timestamp getAddedAt() {
		return addedAt;
	}
	
	public void setAddedAt(Timestamp addedAt) {
		this.addedAt = addedAt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Expenses [expenseId=" + expenseId + ", expenseName=" + expenseName + ", categoryId=" + categoryId
				+ ", amount=" + amount + ", addedAt=" + addedAt + ", userId=" + userId + ", type=" + type + "]";
	}
}
