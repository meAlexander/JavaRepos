package commands;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import client.User;
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (OrderException e) {
			printOut.println(e.getMessage());
		}
		printOut.flush();
		return nextCommand;
	}

	public void purchaseProducts() throws SQLException, OrderException {
		double totalPrice = 0;
		for (Item product : user.getBasket()) {
			String itemName = product.getName();
			int count = product.getCount();
			double price = product.getPrice();
			builder.append(String.format("%s - %d, price: %.2f;", itemName, count, price * count));
			totalPrice += price * count;
		}
		acceptOrder(totalPrice);
	}

	public void acceptOrder(double totalPrice) throws SQLException, OrderException {
		String products = "Products: " + builder.deleteCharAt(builder.length() - 1) + ".";
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