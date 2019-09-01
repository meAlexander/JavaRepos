package commands.inputs.updateOrderStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import commands.action.updateOrderStatus.UpdateOrderStatusActionCommand;

public class GetOrderInputUpdateCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetOrderInputUpdateCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			getAllOrders();

			printOut.println("Please enter the id order");
			printOut.println("Your input please: ");
			printOut.flush();
			int id = Integer.parseInt(buffReader.readLine());

			printOut.println("Please enter new order status");
			printOut.println("Your input please: ");
			printOut.flush();
			String status = buffReader.readLine();

			return new UpdateOrderStatusActionCommand(connection, printOut, id, status, parent);
		} catch (IOException e) {
			printOut.println("Error input data!");
			printOut.flush();
		} catch (NumberFormatException e) {
			printOut.println("Expecting Integer!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void getAllOrders() throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement("SELECT id, products, totalPrice, username, orderStatus, dateOrder FROM orders")
				.executeQuery();

		while (resultSet.next()) {
			printOut.println(String.format(
					"Order id: %d, Products: %s, Price: %.2f, Username: %s, Order status: %s, Date order: %s",
					resultSet.getInt("id"), resultSet.getString("products"), resultSet.getDouble("totalPrice"),
					resultSet.getString("username"), resultSet.getString("orderStatus"),
					resultSet.getTimestamp("dateOrder")));
		}
	}
}