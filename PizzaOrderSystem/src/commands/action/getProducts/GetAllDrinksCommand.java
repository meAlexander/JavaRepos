package commands.action.getProducts;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.User;
import commands.Command;
import commands.inputs.buy.GetDrinkInputBuyCommand;

public class GetAllDrinksCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public GetAllDrinksCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("That`s all available drinks");
		List<String> allDrinks = new ArrayList<>();
		try {
			allDrinks.addAll(getDrinks());
			for (String product : allDrinks) {
				printOut.println(product);
			}
			printOut.flush();
			return new GetDrinkInputBuyCommand(connection, printOut, buffReader, user, parent);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getDrinks() throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM drinks").executeQuery();
		List<String> drinksList = new ArrayList<String>();
		while (resultSet.next()) {
			String drinks = String.format("Drink id: %d, Drink type: %s, Brand: %s, Quantity: %d, Price: %.2f",
					resultSet.getInt("id"), resultSet.getString("drink_type"), resultSet.getString("brand"),
					resultSet.getInt("quantity"), resultSet.getDouble("price"));
			drinksList.add(drinks);
		}
		return drinksList;
	}
}