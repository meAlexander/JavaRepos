package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.DeleteProductException;

public class DeleteProductSaladCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	
	public DeleteProductSaladCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Enter salad name you want to delete: ");
		printOut.println("Your input please: ");
		printOut.flush();
		try {
			String salad = buffReader.readLine();
			
			deleteSalad(salad);
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
	
	public void deleteSalad(String salad)
			throws SQLException, IOException, DeleteProductException {

		PreparedStatement ps = connection.prepareStatement("DELETE FROM salads WHERE salad_name = ?");
		ps.setString(1, salad);
		
		if(ps.execute()) {
			throw new DeleteProductException();
		}
	}
}