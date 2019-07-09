package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.AddProductException;

public class AddProductSaladCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	
	public AddProductSaladCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter pizza and size");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String salad = buffReader.readLine();
			printOut.println("Your input please: ");
			String ingredients = buffReader.readLine();
			printOut.println("Your input please: ");
			double price = Double.parseDouble(buffReader.readLine());
			
			addSalad(salad, ingredients, price);
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
	
	public void addSalad(String salad, String ingredients, double price)
			throws SQLException, IOException, AddProductException {

		PreparedStatement ps = connection.prepareStatement("INSERT INTO salads(salad_name, ingredients, price)" + "VALUES(?, ?, ?)");
		ps.setString(1, salad);
		ps.setString(2, ingredients);
		ps.setDouble(3, price);
		
		if(ps.execute()) {
			throw new AddProductException();
		}
	}
}