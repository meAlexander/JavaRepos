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
import items.SaladItem;

public class BuyProductSaladActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private SaladItem salad;
	private Command nextCommand;

	public BuyProductSaladActionCommand(Connection connection, PrintStream printOut, SaladItem salad, User user,
			Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.salad = salad;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			acceptSaladOrder();
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

	public void acceptSaladOrder() throws SQLException, PurchaseException, ProductInfoException {
		checkSaladInfo();

		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO orders(itemName, count, username) VALUES(?, ?, ?)");
		ps.setString(1, salad.getName());
		ps.setInt(2, salad.getCount());
		ps.setString(3, user.getUserName());

		if (ps.execute()) {
			throw new PurchaseException();
		}
	}

	public void checkSaladInfo() throws SQLException, ProductInfoException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM salads WHERE salad_name = '%s'", salad.getName()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductInfoException();
		}
	}
}