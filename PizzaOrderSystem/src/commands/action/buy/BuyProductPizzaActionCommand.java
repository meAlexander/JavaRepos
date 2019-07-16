package commands.action.buy;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;
import exceptions.ProductException;
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
		this.pizza = pizza;
		this.user = user;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			checkPizzaInfo();
			printOut.println("Product added to the basket!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ProductException e) {
			printOut.println(e.getMessage());
		} catch (PurchaseException e) {
			printOut.println(e.getMessage());
		}
		return nextCommand;
	}

	public void addPizza(ResultSet resultSet) throws SQLException {
//		PreparedStatement ps = connection
//				.prepareStatement("INSERT INTO orders(itemName, count, username, dateOrder) VALUES(?, ?, ?, NOW())");
//		ps.setString(1, pizza.getName());
//		ps.setInt(2, pizza.getCount());
//		ps.setString(3, user.getUserName());
//
//		if (ps.execute()) {
//			throw new PurchaseException();
//		}
		
		PizzaItem newPizza = new PizzaItem(resultSet.getString("pizza_name"), resultSet.getString("size"),
				pizza.getCount(), resultSet.getDouble("price"));
		user.getBasket().add(newPizza);
	}

	public void checkPizzaInfo() throws SQLException, ProductException, PurchaseException {
		ResultSet resultSet = connection
				.prepareStatement(
						String.format("SELECT pizza_name, size, price FROM pizzas WHERE id = %d", pizza.getPizzaID()))
				.executeQuery();
		
		if (!resultSet.next()) {
			throw new ProductException();
		}
		addPizza(resultSet);
	}
}