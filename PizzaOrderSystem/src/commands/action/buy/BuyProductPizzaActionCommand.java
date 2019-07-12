package commands.action.buy;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;
import exceptions.ProductInfoException;
import exceptions.PurchaseException;
import items.PizzaItem;

public class BuyProductPizzaActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private PizzaItem pizza;
	private Command nextCommand;

	public BuyProductPizzaActionCommand(Connection connection, PrintStream printOut, PizzaItem pizza, User user,
			Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.pizza = pizza;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			acceptPizzaOrder();
			printOut.println("Accepted order!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ProductInfoException e) {
			printOut.println(e.getMessage());
		} catch (PurchaseException e) {
			printOut.println(e.getMessage());
		}
		return nextCommand;
	}

	public void acceptPizzaOrder() throws SQLException, PurchaseException, ProductInfoException {
		checkPizzaInfo();

		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO orders(itemName, count, username, dateOrder) VALUES(?, ?, ?, NOW())");
		ps.setString(1, pizza.getName());
		ps.setInt(2, pizza.getCount());
		ps.setString(3, user.getUserName());

		if (ps.execute()) {
			throw new PurchaseException();
		}
	}

	public void checkPizzaInfo() throws SQLException, ProductInfoException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM pizzas WHERE pizza_name = '%s' AND size = '%s'",
						pizza.getName(), pizza.getSize()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductInfoException();
		}
	}
}