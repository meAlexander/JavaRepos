package commands;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import exceptions.BuyProductException;
import items.Item;

public class PurchaseCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private String user;
	private List<Item> basket;

	public PurchaseCommand(Connection connection, PrintStream printOut, String user, List<Item> basket) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.basket = basket;
	}

	@Override
	public Command execute(Command parent) {

		try {
			purchaseProducts();
			return parent;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BuyProductException e) {
			System.out.println(e.getMessage());
		}

		printOut.flush();
		return parent;
	}

	public void purchaseProducts() throws SQLException, BuyProductException {

		for (Item product : basket) {
			printOut.println(product.toString());
			printOut.flush();
			String itemName = product.getName();
			int count = product.getCount();
			acceptOrder(itemName, count);
		}
	}

	public void acceptOrder(String itemName, int count) throws SQLException, BuyProductException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO orders(itemName, count, username, dateOrder) VALUES(?, ?, ?, NOW())");
		ps.setString(1, itemName);
		ps.setInt(2, count);
		ps.setString(3, user);

		if (ps.execute()) {
			throw new BuyProductException();
		}
	}
}