package commands.inputs.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import commands.Command;
import commands.action.getOrders.GetAllOrdersActionCommand;
import commands.action.update.UpdateOrderStatusActionCommand;

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
			//getAllOrders();

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
		} catch (NumberFormatException e) {
			printOut.println("Expecting Integer!");
		} 
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
		return parent;
	}

	public Command getAllOrders() throws SQLException {
		
		return new GetAllOrdersActionCommand(connection, printOut);
	}
}