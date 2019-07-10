package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.UpdateOrderStatusException;

public class UpdateOrderStatusCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public UpdateOrderStatusCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			getOrders();
			printOut.flush();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		printOut.println("Please enter the username of order");
		printOut.println("Your input please: ");
		printOut.flush();
		try {
			String user = buffReader.readLine();

			printOut.println("Please enter new order status");
			printOut.println("Your input please: ");
			printOut.flush();
			String status = buffReader.readLine();

			updateStatus(status, user);
			printOut.flush();
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UpdateOrderStatusException e) {
			System.out.println(e.getMessage());
		}
		printOut.println("Your input please: ");
		printOut.flush();
		return null;
	}

	public void updateStatus(String status, String username) throws SQLException, UpdateOrderStatusException {

		PreparedStatement ps = connection.prepareStatement("UPDATE orders SET orderStatus = ? WHERE username = ?");
		ps.setString(1, status);
		ps.setString(2, username);

		if (ps.execute()) {
			throw new UpdateOrderStatusException();
		}
	}

	public void getOrders() throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement("SELECT itemName, count, username, orderStatus, dateOrder FROM orders").executeQuery();

		printOut.println(String.format("Item name: %s, Count: %d, Username: %s, Order status: %s, Date order: %s",
				resultSet.getString("itemName"), resultSet.getInt("count"), resultSet.getString("username"),
				resultSet.getString("orderStatus"), resultSet.getDate("dateOrder")));
	}
}