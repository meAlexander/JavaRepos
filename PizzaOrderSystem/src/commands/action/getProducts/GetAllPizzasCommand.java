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
import commands.inputs.buy.GetPizzaInputBuyCommand;

public class GetAllPizzasCommand implements Command {
	private static Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public GetAllPizzasCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		GetAllPizzasCommand.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("That`s all available pizzas");
		List<String> allPizzas = new ArrayList<>();
		try {
			allPizzas.addAll(getPizzas());
			for (String product : allPizzas) {
				printOut.println(product);
			}
			printOut.flush();
			return new GetPizzaInputBuyCommand(connection, printOut, buffReader, user, parent);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getPizzas() throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM pizzas").executeQuery();
		List<String> pizzasList = new ArrayList<>();
		
		while (resultSet.next()) {
			String pizza = String.format("Pizza id: %d, Pizza name: %s, Size: %s, Ingredients: %s, Price: %.2f",
					resultSet.getInt("id"),
					resultSet.getString("pizza_name"),
					resultSet.getString("size"),
					resultSet.getString("ingredients"),
					resultSet.getDouble("price"));
			pizzasList.add(pizza);
		}
		return pizzasList;
	}
}