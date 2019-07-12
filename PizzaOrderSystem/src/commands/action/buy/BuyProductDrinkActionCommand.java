package commands.action.buy;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;
import exceptions.ProductInfoException;
import exceptions.PurchaseException;
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
			acceptDrinkOrder();
			printOut.println("Accepted order!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ProductInfoException e) {
			printOut.println(e.getMessage());
		} catch (PurchaseException e) {
			printOut.println(e.getMessage());
		}
		return nextCommand;
	}

	public void acceptDrinkOrder() throws SQLException, PurchaseException, ProductInfoException {
		checkDrinkInfo();

		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO orders(itemName, count, username) VALUES(?, ?, ?)");
		ps.setString(1, drink.getName());
		ps.setInt(2, drink.getCount());
		ps.setString(3, user.getUserName());

		if (ps.execute()) {
			throw new PurchaseException();
		}
	}

	public void checkDrinkInfo() throws SQLException, ProductInfoException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM drinks WHERE drink_type = '%s' AND brand = '%s'",
						drink.getName(), drink.getBrand()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductInfoException();
		}
	}
}