package commands.actions.buy;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import exceptions.BuyProductException;
import items.DrinkItem;
import items.User;

public class BuyProductDrinkActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private DrinkItem drink;
	private Command nextCommand;

	public BuyProductDrinkActionCommand(Connection connection, PrintStream printOut, DrinkItem drink, User user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.drink = drink;
		this.user = user;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			buyDrink();
			printOut.flush();
			return nextCommand;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BuyProductException e) {
			printOut.println(e.getMessage());
		}
		return null;
	}

	public void buyDrink()
			throws SQLException, IOException, BuyProductException {
		ResultSet resultSet = connection.prepareStatement(
				String.format("SELECT id FROM drinks WHERE drink_type = '%s' AND brand = '%s'", drink.getName(), drink.getBrand()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new BuyProductException();
		}
		acceptDrinkOrder();
	}

	public void acceptDrinkOrder() throws SQLException, BuyProductException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO orders(itemName, count, username) VALUES(?, ?, ?)");
		ps.setString(1, drink.getName());
		ps.setInt(2, drink.getCount());
		ps.setString(3, user.getUserName());

		if (ps.execute()) {
			throw new BuyProductException();
		}
	}
}