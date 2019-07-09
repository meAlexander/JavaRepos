package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.AddProductException;
import exceptions.BuyProductException;

public class BuyProductPizzaCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private String user;

	public BuyProductPizzaCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, String user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter pizza and size");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String pizza = buffReader.readLine();
			printOut.println("Your input please: ");
			String size = buffReader.readLine();

			buyPizza(pizza, size);
			printOut.flush();
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BuyProductException e) {
			System.out.println(e.getMessage());
		} catch (AddProductException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public int getUserId() throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM users WHERE username = '%s'", user)).executeQuery();

		resultSet.next();
		return resultSet.getInt("id");
	}

	public void buyPizza(String pizza, String size)
			throws SQLException, IOException, BuyProductException, AddProductException {
		ResultSet resultSet = connection
				.prepareStatement(
						String.format("SELECT id FROM pizzas WHERE pizza_name = '%s' AND size = '%s'", pizza, size))
				.executeQuery();

		if (!resultSet.next()) {
			throw new BuyProductException();
		}
		acceptPizzaOrder(resultSet.getInt("id"), getUserId());
	}

	public void acceptPizzaOrder(int pizza_id, int userId) throws SQLException, AddProductException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO orders(pizza_id, salad_id, drink_id, user_id, dateOrder) VALUES(?, NULL, NULL, ?, NOW())");
		ps.setInt(1, pizza_id);
		ps.setInt(2, userId);

		if (ps.execute()) {
			throw new AddProductException();
		}
	}
}