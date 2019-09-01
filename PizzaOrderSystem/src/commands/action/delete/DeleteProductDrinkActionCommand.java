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
			checkDrinkInfo();
			printOut.println("Drink deleted!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DeleteProductException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (ProductException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	public void deleteDrink() throws SQLException, DeleteProductException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM drinks WHERE id = ?");
		ps.setInt(1, drink.getDrinkID());

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}

	public void checkDrinkInfo() throws SQLException, ProductException, DeleteProductException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM drinks WHERE id = %d", drink.getDrinkID()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductException();
		}
		deleteDrink();
	}
}