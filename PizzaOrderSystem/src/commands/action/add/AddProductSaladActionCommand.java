package commands.action.add;

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

	public AddProductSaladActionCommand(Connection connection, PrintStream printOut, SaladItem salad,
			Command nextCommand) {
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
		} catch (SQLException e) {
			if (e.getMessage().contains("Duplicate key")) {
				printOut.println("Salad exists with this name!");
				printOut.flush();
			} else {
				e.printStackTrace();
			}
		} catch (AddProductException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	public void addSalad() throws AddProductException, SQLException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO salads(salad_name, ingredients, price) VALUES(?, ?, ?)");
		ps.setString(1, salad.getName());
		ps.setString(2, salad.getIngrediens());
		ps.setDouble(3, salad.getPrice());

		if (ps.execute()) {
			throw new AddProductException();
		}
	}
}