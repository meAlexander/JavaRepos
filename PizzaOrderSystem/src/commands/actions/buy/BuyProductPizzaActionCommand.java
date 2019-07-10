package commands.actions.buy;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import exceptions.BuyProductException;
import items.PizzaItem;
import items.User;

public class BuyProductPizzaActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private PizzaItem pizza;
	private Command nextCommand;

	public BuyProductPizzaActionCommand(Connection connection, PrintStream printOut, PizzaItem pizza, User user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.pizza = pizza;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			buyPizza();
			printOut.flush();
			return nextCommand;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BuyProductException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void buyPizza() throws SQLException, IOException, BuyProductException {
		ResultSet resultSet = connection
				.prepareStatement(
						String.format("SELECT id FROM pizzas WHERE pizza_name = '%s' AND size = '%s'", pizza.getName(), pizza.getSize()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new BuyProductException();
		}
		acceptPizzaOrder();
	}

	public void acceptPizzaOrder() throws SQLException, BuyProductException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO orders(itemName, count, username) VALUES(?, ?, ?)");
		ps.setString(1, pizza.getName());
		ps.setInt(2, pizza.getCount());
		ps.setString(3, user.getUserName());

		if (ps.execute()) {
			throw new BuyProductException();
		}
	}
}