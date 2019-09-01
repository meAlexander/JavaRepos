package commands.action.delete;

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
			checkPizzaInfo();
			printOut.println("Pizza deleted!");
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

	public void deletePizza() throws SQLException, DeleteProductException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM pizzas WHERE id = ?");
		ps.setInt(1, pizza.getPizzaID());

		if (ps.execute()) {
			throw new DeleteProductException();
		}
	}

	public void checkPizzaInfo() throws SQLException, ProductException, DeleteProductException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM pizzas WHERE id = %d", pizza.getPizzaID()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductException();
		}
		deletePizza();
	}
}