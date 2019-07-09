package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.DeleteProductException;

public class DeleteProductPizzaCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public DeleteProductPizzaCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter pizza and size you want to delete");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String pizza = buffReader.readLine();
			printOut.println("Your input please: ");
			String size = buffReader.readLine();

			deletePizza(pizza, size);
			printOut.flush();
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DeleteProductException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void deletePizza(String pizza, String size) throws SQLException, IOException, DeleteProductException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM pizzas WHERE pizza_name = ? AND size = ?");
		ps.setString(1, pizza);
		ps.setString(2, size);

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}
}