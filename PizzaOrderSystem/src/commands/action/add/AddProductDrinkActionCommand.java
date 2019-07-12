package commands.action.add;

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

	public AddProductDrinkActionCommand(Connection connection, PrintStream printOut, DrinkItem drink,
			Command nextCommand) {
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
		} catch (SQLException e) {
			if(!(drink.getQuantity() == 500) || !(drink.getQuantity() == 1000) || !(drink.getQuantity() == 1500)) {
				printOut.println("Drink quantity must be '500', '1000', '1500'");
			}else {
				e.printStackTrace();
			}
		} catch (AddProductException e) {
			printOut.println(e.getMessage());
		}
		return nextCommand;
	}

	public void addDrink() throws SQLException, AddProductException {
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