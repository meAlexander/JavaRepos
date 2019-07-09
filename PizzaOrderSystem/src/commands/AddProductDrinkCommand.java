package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.AddProductException;

public class AddProductDrinkCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public AddProductDrinkCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter drink type, brand, quantity and price you want to add");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String drinkType = buffReader.readLine();
			printOut.println("Your input please: ");
			String brand = buffReader.readLine();
			printOut.println("Your input please: ");
			int quantity = Integer.parseInt(buffReader.readLine());
			printOut.println("Your input please: ");
			double price = Double.parseDouble(buffReader.readLine());

			addDrink(drinkType, brand, quantity, price);
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

	public void addDrink(String drinkType, String brand, int quantity, double price)
			throws SQLException, IOException, AddProductException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO drinks(drink_type, brand, quantity, price) VALUES(?, ?, ?, ?)");
		ps.setString(1, drinkType);
		ps.setString(2, brand);
		ps.setInt(3, quantity);
		ps.setDouble(4, price);

		if (ps.execute()) {
			throw new AddProductException();
		}
	}
}