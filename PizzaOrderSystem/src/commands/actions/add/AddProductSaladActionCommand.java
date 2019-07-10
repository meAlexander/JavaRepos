package commands.actions.add;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commands.Command;
import exceptions.AddProductException;
import items.SaladItem;

public class AddProductSaladActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private SaladItem salad;
	private Command nextCommand;
	
	public AddProductSaladActionCommand(Connection connection, PrintStream printOut, SaladItem salad, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.salad = salad;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			addSalad();
			printOut.println("Salad added!");
			printOut.flush();
			return nextCommand;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddProductException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void addSalad()
			throws SQLException, IOException, AddProductException {

		PreparedStatement ps = connection.prepareStatement("INSERT INTO salads(salad_name, ingredients, price) VALUES(?, ?, ?)");
		ps.setString(1, salad.getName());
		ps.setString(2, salad.getIngrediens());
		ps.setDouble(3, salad.getPrice());
		
		if(ps.execute()) {
			throw new AddProductException();
		}
	}
}