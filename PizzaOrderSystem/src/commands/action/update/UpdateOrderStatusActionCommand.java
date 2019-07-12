package commands.action.update;

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
			updateStatus();
			printOut.println("Updated order status!");
			printOut.flush();
		} catch (SQLException e) {
			if (!status.equals("Waiting") || !status.equals("Cooking") || !status.equals("Delivery")) {
				printOut.println("Status must be 'Waiting', 'Cooking' or 'Delivery'");
			}
		} catch (OrderException e) {
			printOut.println(e.getMessage());
		} catch (OrderStatusException e) {
			printOut.println(e.getMessage());
		}
		return nextCommand;
	}

	public void updateStatus() throws SQLException, OrderException, OrderStatusException {
		checkOrder();
		
		PreparedStatement ps = connection.prepareStatement("UPDATE orders SET orderStatus = ? WHERE id = ?");
		ps.setString(1, status);
		ps.setInt(2, id);

		if (ps.execute()) {
			throw new OrderStatusException();
		}
	}
	
	public void checkOrder() throws SQLException, OrderException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM orders WHERE id = %d", id))
				.executeQuery();
		
		if(!resultSet.next()) {
			throw new OrderException();
		}
	}
}