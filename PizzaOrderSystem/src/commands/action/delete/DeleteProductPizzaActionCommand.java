package commands.action.delete;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import exceptions.DeleteProductException;
import exceptions.ProductException;
import items.PizzaItem;

public class DeleteProductPizzaActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private PizzaItem pizza;
	private Command nextCommand;

	public DeleteProductPizzaActionCommand(Connection connection, PrintStream printOut, PizzaItem pizza,
			Command nextCommand) {
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DeleteProductException e) {
			printOut.println(e.getMessage());
		} catch (ProductException e) {
			printOut.println(e.getMessage());
		}
		return nextCommand;
	}

	public void deletePizza() throws SQLException, IOException, DeleteProductException, ProductException {
		checkPizzaInfo();

		PreparedStatement ps = connection.prepareStatement("DELETE FROM pizzas WHERE pizza_name = ? AND size = ?");
		ps.setString(1, pizza.getName());
		ps.setString(2, pizza.getSize());

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}

	public void checkPizzaInfo() throws SQLException, ProductException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM pizzas WHERE pizza_name = '%s' AND size = '%s'",
						pizza.getName(), pizza.getSize()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductException();
		}
	}
}