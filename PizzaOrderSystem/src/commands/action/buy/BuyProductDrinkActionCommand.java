package commands.action.buy;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;
import exceptions.ProductException;
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
			checkDrinkInfo();
			printOut.println("Product added to the basket!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ProductException e) {
			printOut.println(e.getMessage());
		} catch (PurchaseException e) {
			printOut.println(e.getMessage());
		}
		return nextCommand;
	}

	public void addDrink(ResultSet resultSet) throws SQLException, PurchaseException, ProductException {
//		PreparedStatement ps = connection
//				.prepareStatement("INSERT INTO orders(itemName, count, username, dateOrder) VALUES(?, ?, ?, NOW())");
//		ps.setString(1, drink.getName());
//		ps.setInt(2, drink.getCount());
//		ps.setString(3, user.getUserName());
//
//		if (ps.execute()) {
//			throw new PurchaseException();
//		}

		DrinkItem newDrink = new DrinkItem(resultSet.getString("drink_type"), resultSet.getString("brand"),
				resultSet.getInt("quantity"), drink.getCount(), resultSet.getDouble("price"));
		user.getBasket().add(newDrink);
	}

	public void checkDrinkInfo() throws SQLException, ProductException, PurchaseException {
		ResultSet resultSet = connection.prepareStatement(String
				.format("SELECT drink_type, brand, quantity, price FROM drinks WHERE id = %d", drink.getDrinkID()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductException();
		}
		addDrink(resultSet);
	}
}