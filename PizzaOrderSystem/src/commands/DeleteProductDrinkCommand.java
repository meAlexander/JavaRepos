package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.DeleteProductException;

public class DeleteProductDrinkCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public DeleteProductDrinkCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Enter drink type and brand you want to delete: ");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String drinkType = buffReader.readLine();
			printOut.println("Your input please: ");
			String brand = buffReader.readLine();

			deleteDrink(drinkType, brand);
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

	public void deleteDrink(String drinkType, String brand) throws SQLException, IOException, DeleteProductException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM drinks WHERE drink_type = ? AND brand = ?");
		ps.setString(1, drinkType);
		ps.setString(2, brand);

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}
}