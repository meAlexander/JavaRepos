package commands.actions.add;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commands.Command;
import exceptions.AddProductException;
import items.DrinkItem;

public class AddProductDrinkActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private DrinkItem drink;
	private Command nextCommand;

	public AddProductDrinkActionCommand(Connection connection, PrintStream printOut, DrinkItem drink, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.drink = drink;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			addDrink();
			printOut.println("Drink added!");
			printOut.flush();
			return nextCommand;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddProductException e) {
			printOut.println(e.getMessage());
		}
		return null;
	}

	public void addDrink()
			throws SQLException, IOException, AddProductException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO drinks(drink_type, brand, quantity, price) VALUES(?, ?, ?, ?)");
		ps.setString(1, drink.getName());
		ps.setString(2, drink.getBrand());
		ps.setInt(3, drink.getQuantity());
		ps.setDouble(4, drink.getPrice());

		if (ps.execute()) {
			throw new AddProductException();
		}
	}
}