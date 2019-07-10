package commands.action.delete;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commands.Command;
import exceptions.DeleteProductException;
import items.PizzaItem;

public class DeleteProductPizzaActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private PizzaItem pizza;
	private Command nextCommand;

	public DeleteProductPizzaActionCommand(Connection connection, PrintStream printOut, PizzaItem pizza, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.pizza = pizza;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			deletePizza();
			printOut.println("Pizza deleted!");
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

	public void deletePizza() throws SQLException, IOException, DeleteProductException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM pizzas WHERE pizza_name = ? AND size = ?");
		ps.setString(1, pizza.getName());
		ps.setString(2, pizza.getSize());

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}
}