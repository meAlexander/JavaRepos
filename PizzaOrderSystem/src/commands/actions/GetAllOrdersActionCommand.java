package commands.actions;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;

public class GetAllOrdersActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;

	public GetAllOrdersActionCommand(Connection connection, PrintStream printOut) {
		this.connection = connection;
		this.printOut = printOut;
	}

	@Override
	public Command execute(Command parent) {
		try {
			getAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void getAllOrders() throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement("SELECT id, itemName, count, username, orderStatus, dateOrder FROM orders")
				.executeQuery();

		while (resultSet.next()) {
			printOut.println(String.format(
					"Order id: %d, Item name: %s, Count: %d, Username: %s, Order status: %s, Date order: %s",
					resultSet.getInt("id"), resultSet.getString("itemName"), resultSet.getInt("count"),
					resultSet.getString("username"), resultSet.getString("orderStatus"),
					resultSet.getTimestamp("dateOrder")));
		}
	}
}