package commands.action.add;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commands.Command;
import exceptions.AddProductException;
import items.PizzaItem;

public class AddProductPizzaActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private PizzaItem pizza;
	private Command nextCommand;

	public AddProductPizzaActionCommand(Connection connection, PrintStream printOut, PizzaItem pizza,
			Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.pizza = pizza;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			addPizza();
			printOut.println("Pizza added!");
			printOut.flush();
		} catch (SQLException e) {
			if (!pizza.getSize().equals("S") || !pizza.getSize().equals("M") || !pizza.getSize().equals("L")) {
				printOut.println("Size must be 'S', 'M' or 'L'");
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

	public void addPizza() throws SQLException, AddProductException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO pizzas(pizza_name, size, ingredients, price) VALUES(?, ?, ?, ?)");
		ps.setString(1, pizza.getName());
		ps.setString(2, pizza.getSize());
		ps.setString(3, pizza.getIngredients());
		ps.setDouble(4, pizza.getPrice());

		if (ps.execute()) {
			throw new AddProductException();
		}
	}
}