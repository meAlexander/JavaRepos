package commands;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import client.User;
import exceptions.BasketException;
import exceptions.OrderException;
import items.Item;

public class PurchaseActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private Command nextCommand;
	private StringBuilder builder = new StringBuilder();

	public PurchaseActionCommand(Connection connection, PrintStream printOut, User user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			purchaseProducts();
			printOut.println("Order is added!");
			printOut.flush();
			clearBasket();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OrderException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (BasketException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	private void clearBasket() {
		user.getBasket().clear();
	}

	public void purchaseProducts() throws SQLException, OrderException, BasketException {
		double totalPrice = 0;
		for (Item product : user.getBasket()) {
			String itemName = product.getName();
			int count = product.getAmount();
			double price = product.getPrice();
			builder.append(String.format("%s, amount - %d;", itemName, count));
			totalPrice += price * count;
		}
		
		if(builder.length() == 0 || totalPrice == 0) {
			throw new BasketException();
		}
		acceptOrder(totalPrice);
	}

	public void acceptOrder(double totalPrice) throws SQLException, OrderException {
		String products = builder.deleteCharAt(builder.length() - 1) + ".";
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO orders(products, totalPrice, username) VALUES(?, ?, ?)");
		ps.setString(1, products);
		ps.setDouble(2, totalPrice);
		ps.setString(3, user.getUserName());

		if (ps.execute()) {
			throw new OrderException();
		}
	}
}