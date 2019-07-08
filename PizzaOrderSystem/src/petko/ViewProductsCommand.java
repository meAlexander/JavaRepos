package petko;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewProductsCommand implements Command {

	private PrintStream printOut;
	private Connection connection;

	public ViewProductsCommand(PrintStream printOut, Connection connection) {
		this.printOut = printOut;
		this.connection = connection;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("That`s all available products");
		List<String> allProducts = new ArrayList<String>();
		try {
			allProducts.addAll(getPizzas(connection, printOut));
			allProducts.addAll(getSalads(connection, printOut));
			allProducts.addAll(getDrinks(connection, printOut));

			for (String product : allProducts) {
				printOut.println(product);
			}
			printOut.flush();
			return parent;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getPizzas(Connection connection, PrintStream printout) throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM pizzas").executeQuery();
		List<String> pizzasList = new ArrayList<>();
		while (resultSet.next()) {
			String pizza = String.format("Pizza name: %s, Size: %s, Ingredients: %s, Price: %.2f",
					resultSet.getString("pizza_name"), resultSet.getString("size"), resultSet.getString("ingredients"),
					resultSet.getDouble("price"));
			pizzasList.add(pizza);
		}

		return pizzasList;
	}

	public List<String> getSalads(Connection connection, PrintStream printout) throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM salads").executeQuery();
		List<String> saladsList = new ArrayList<>();
		while (resultSet.next()) {
			String salad = String.format("Salad name: %s, Ingredients: %s, Price: %.2f",
					resultSet.getString("salad_name"), resultSet.getString("ingredients"),
					resultSet.getDouble("price"));
			saladsList.add(salad);
		}
		return saladsList;
	}

	public List<String> getDrinks(Connection connection, PrintStream printout) throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM drinks").executeQuery();
		List<String> drinksList = new ArrayList<String>();
		while (resultSet.next()) {
			String drinks = String.format("Drink type: %s, Brand: %s, Quantity: %d, Price: %.2f",
					resultSet.getString("drink_type"), resultSet.getString("brand"), resultSet.getInt("quantity"),
					resultSet.getDouble("price"));
			drinksList.add(drinks);
		}
		return drinksList;
	}
}