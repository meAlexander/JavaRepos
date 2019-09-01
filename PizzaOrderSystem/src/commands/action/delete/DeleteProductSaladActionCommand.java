package commands.action.delete;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import exceptions.DeleteProductException;
import exceptions.ProductException;
import items.SaladItem;

public class DeleteProductSaladActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private SaladItem salad;
	private Command nextCommand;

	public DeleteProductSaladActionCommand(Connection connection, PrintStream printOut, SaladItem salad,
			Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.salad = salad;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			checkSaladInfo();
			printOut.println("Salad deleted!");
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

	public void deleteSalad() throws SQLException, DeleteProductException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM salads WHERE id = ?");
		ps.setInt(1, salad.getSaladID());

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}

	public void checkSaladInfo() throws SQLException, ProductException, DeleteProductException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM salads WHERE id = %d", salad.getSaladID()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductException();
		}
		deleteSalad();
	}
}