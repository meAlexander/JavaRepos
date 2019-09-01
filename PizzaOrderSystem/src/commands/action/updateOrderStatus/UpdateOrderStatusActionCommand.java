package commands.action.updateOrderStatus;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import exceptions.OrderException;
import exceptions.OrderStatusException;

public class UpdateOrderStatusActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private String status;
	private int id;
	private Command nextCommand;

	public UpdateOrderStatusActionCommand(Connection connection, PrintStream printOut, int id, String status,
			Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.status = status;
		this.id = id;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			checkOrder();
			printOut.println("Order status updated!");
			printOut.flush();
		} catch (SQLException e) {
			if (!(status.equals("Waiting")) || !(status.equals("Accepted")) || !(status.equals("Delivery"))) {
				printOut.println("Order status must be 'Waiting', 'Accepted' or 'Delivery'");
				printOut.flush();
			}
		} catch (OrderException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (OrderStatusException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	public void updateStatus() throws SQLException, OrderStatusException {
		PreparedStatement ps = connection.prepareStatement("UPDATE orders SET orderStatus = ? WHERE id = ?");
		ps.setString(1, status);
		ps.setInt(2, id);

		if (ps.execute()) {
			throw new OrderStatusException();
		}
	}

	public void checkOrder() throws SQLException, OrderException, OrderStatusException {
		ResultSet resultSet = connection.prepareStatement(String.format("SELECT id FROM orders WHERE id = %d", id))
				.executeQuery();

		if (!resultSet.next()) {
			throw new OrderException();
		}
		updateStatus();
	}
}