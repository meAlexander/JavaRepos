package commands.action.delete;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commands.Command;
import exceptions.DeleteProductException;
import items.DrinkItem;

public class DeleteProductDrinkActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private DrinkItem drink;
	private Command nextCommand;

	public DeleteProductDrinkActionCommand(Connection connection, PrintStream printOut, DrinkItem drink, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.drink = drink;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			deleteDrink();
			printOut.println("Drink deleted!");
			printOut.flush();
			return nextCommand;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DeleteProductException e) {
			printOut.println(e.getMessage());
		}
		return null;
	}

	public void deleteDrink() throws SQLException, IOException, DeleteProductException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM drinks WHERE drink_type = ? AND brand = ?");
		ps.setString(1, drink.getName());
		ps.setString(2, drink.getBrand());

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}
}