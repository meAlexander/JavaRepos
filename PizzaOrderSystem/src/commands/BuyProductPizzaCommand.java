package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import exceptions.BuyProductException;

public class BuyProductPizzaCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private String user;
	private List<Item> basket;

	public BuyProductPizzaCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, String user, List<Item> basket) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
		this.basket = basket;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter pizza and count you want to buy");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String pizza = buffReader.readLine();
//			printOut.println("Your input please: ");
//			printOut.flush();
//			String size = buffReader.readLine();
			printOut.println("Your input please: ");
			printOut.flush();
			int count = Integer.parseInt(buffReader.readLine());
			Item pizzaItem = new Item(pizza, count);
			
			basket.add(pizzaItem);
			//buyPizza(pizza, size);	
			printOut.flush();
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		} 
//		catch (SQLException e) {
//			e.printStackTrace();
//		} catch (BuyProductException e) {
//			System.out.println(e.getMessage());
//		}
		return null;
	}

	public int getUserId() throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM users WHERE username = '%s'", user)).executeQuery();

		resultSet.next();
		return resultSet.getInt("id");
	}

	public void buyPizza(String pizza, String size)
			throws SQLException, IOException, BuyProductException {
		ResultSet resultSet = connection
				.prepareStatement(
						String.format("SELECT id FROM pizzas WHERE pizza_name = '%s' AND size = '%s'", pizza, size))
				.executeQuery();

		if (!resultSet.next()) {
			throw new BuyProductException();
		}
		acceptPizzaOrder(resultSet.getInt("id"), getUserId());
	}

	public void acceptPizzaOrder(int pizza_id, int userId) throws SQLException, BuyProductException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO orders(pizza_id, salad_id, drink_id, user_id, dateOrder) VALUES(?, NULL, NULL, ?, NOW())");
		ps.setInt(1, pizza_id);
		ps.setInt(2, userId);

		if (ps.execute()) {
			throw new BuyProductException();
		}
	}
}