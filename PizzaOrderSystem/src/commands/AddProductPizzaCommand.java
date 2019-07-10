package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.AddProductException;

public class AddProductPizzaCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public AddProductPizzaCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter pizza name, size, ingredients and price you want to add");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String pizza = buffReader.readLine();
			printOut.println("Your input please: ");
			printOut.flush();
			String size = buffReader.readLine();
			printOut.println("Your input please: ");
			printOut.flush();
			String ingredients = buffReader.readLine();
			printOut.println("Your input please: ");
			printOut.flush();
			double price = Double.parseDouble(buffReader.readLine());
			
			addPizza(pizza, size, ingredients, price);
			printOut.flush();
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddProductException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void addPizza(String pizza, String size, String ingredients, double price) throws SQLException, IOException, AddProductException {

		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO pizzas(pizza_name, size, ingredients, price) VALUES(?, ?, ?, ?)");
		ps.setString(1, pizza);
		ps.setString(2, size);
		ps.setString(3, ingredients);
		ps.setDouble(4, price);
		
		if(ps.execute()) {
			throw new AddProductException();
		}
	}
}