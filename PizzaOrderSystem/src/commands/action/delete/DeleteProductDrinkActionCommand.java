package commands.action.delete;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import exceptions.DeleteProductException;
import exceptions.ProductException;
import items.DrinkItem;

public class DeleteProductDrinkActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private DrinkItem drink;
	private Command nextCommand;

	public DeleteProductDrinkActionCommand(Connection connection, PrintStream printOut, DrinkItem drink,
			Command nextCommand) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DeleteProductException e) {
			printOut.println(e.getMessage());
		} catch (ProductException e) {
			printOut.println(e.getMessage());
		}
		return nextCommand;
	}

	public void deleteDrink() throws SQLException, DeleteProductException, ProductException {
		checkDrinkInfo();

		PreparedStatement ps = connection
				.prepareStatement("DELETE FROM drinks WHERE drink_type = ? AND brand = ? AND quantity = %d");
		ps.setString(1, drink.getName());
		ps.setString(2, drink.getBrand());
		ps.setInt(3, drink.getQuantity());

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}

	public void checkDrinkInfo() throws SQLException, ProductException {
		ResultSet resultSet = connection.prepareStatement(
				String.format("SELECT id FROM drinks WHERE drink_type = '%s' AND brand = '%s' AND quantity = %d",
						drink.getName(), drink.getBrand(), drink.getQuantity()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductException();
		}
	}
}