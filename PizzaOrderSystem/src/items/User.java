package items;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String userName;
	private List<Item> basket = new ArrayList<>();
	
	public User(String user) {
		this.userName = user;
	}

	public String getUserName() {
		return userName;
	}

	public List<Item> getBasket() {
		return basket;
	}
}