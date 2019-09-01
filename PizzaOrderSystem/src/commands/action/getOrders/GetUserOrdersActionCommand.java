package commands.action.getOrders;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;

public class GetUserOrdersActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;

	public GetUserOrdersActionCommand(Connection connection, PrintStream printOut, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		try {
			getUserOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parent;
	}

	public void getUserOrders() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(String.format(
				"SELECT id, products, totalPrice, username, orderStatus, dateOrder FROM orders WHERE username = '%s'",
				user.getUserName())).executeQuery();

		while (resultSet.next()) {
			printOut.println(String.format(
					"Order id: %d, Products: %s, Total Price: %.2f, Username: %s, Order status: %s, Date order: %s",
					resultSet.getInt("id"),
					resultSet.getString("products"),
					resultSet.getDouble("totalPrice"),
					resultSet.getString("username"),
					resultSet.getString("orderStatus"),
					resultSet.getTimestamp("dateOrder")));
		}
	}
}