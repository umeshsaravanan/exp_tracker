package com.expenseTracker.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "category_id")
	private int id;
	
	@Column(name="category_name")
	private String name;
	
	@Column(name= "userId")
	private Integer userId;
	
	public Category() {
		
	}
	
	public Category(int id, String name, Integer userId) {
		this.id = id;
		this.name = name;
		this.userId = userId;
	}

	public int getid() {
		return id;
	}
	
	public void setid(int id) {
		this.id = id;
	}
	
	public String getname() {
		return name;
	}
	
	public void setname(String name) {
		this.name = name;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", userId=" + userId + "]";
	}
}
