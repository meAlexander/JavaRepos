package client;

import java.util.ArrayList;
import java.util.List;

import items.Item;

public class User {
	private String userName;
	private String password;
	private String phone;
	private String email;
	private List<Item> basket = new ArrayList<>();
	
	public User(String user, String password, String phone, String email) {
		this.userName = user;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}
	
	public User(String user, String password) {
		this(user, password, "NULL", "NULL");
	}

	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getEmail() {
		return email;
	}

	public List<Item> getBasket() {
		return basket;
	}
}