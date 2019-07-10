package commands.actions.add;

import java.io.IOException;
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

	public AddProductPizzaActionCommand(Connection connection, PrintStream printOut, PizzaItem pizza, Command nextCommand) {
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

	public void addPizza() throws SQLException, IOException, AddProductException {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO pizzas(pizza_name, size, ingredients, price) VALUES(?, ?, ?, ?)");
		ps.setString(1, pizza.getName());
		ps.setString(2, pizza.getSize());
		ps.setString(3, pizza.getIngredients());
		ps.setDouble(4, pizza.getPrice());
		ps.execute();
//		if(ps.execute()) {
//			throw new AddProductException();
//		}
	}
}