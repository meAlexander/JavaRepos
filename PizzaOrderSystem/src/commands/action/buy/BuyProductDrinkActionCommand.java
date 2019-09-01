package commands.action.buy;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;
import exceptions.ProductException;
import items.DrinkItem;

public class BuyProductDrinkActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private DrinkItem drink;
	private Command nextCommand;

	public BuyProductDrinkActionCommand(Connection connection, PrintStream printOut, DrinkItem drink, User user,
			Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.drink = drink;
		this.user = user;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			checkDrinkInfo();
			printOut.println("Product added to the basket!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ProductException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	public void addDrink(ResultSet resultSet) throws SQLException, ProductException {
		DrinkItem newDrink = new DrinkItem(resultSet.getString("drink_type"), resultSet.getString("brand"),
				resultSet.getString("quantity"), drink.getAmount(), resultSet.getDouble("price"));
		user.getBasket().add(newDrink);
	}

	public void checkDrinkInfo() throws SQLException, ProductException {
		ResultSet resultSet = connection
				.prepareStatement(
						String.format("SELECT drink_type, brand, quantity, price FROM drinks WHERE id = %d", drink.getDrinkID()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductException();
		}
		addDrink(resultSet);
	}
}