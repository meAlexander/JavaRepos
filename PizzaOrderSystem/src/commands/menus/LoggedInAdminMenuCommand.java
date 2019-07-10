package commands.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.UpdateOrderStatusCommand;

public class LoggedInAdminMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public LoggedInAdminMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Login admin menu: 1.Add product 2.Delete product 3.Update order status 4.Main menu");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String adminMenuAnswer = buffReader.readLine();
			return getNextCommand(adminMenuAnswer);
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new LoggedInAdminMenuCommand(connection, printOut, buffReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Command getNextCommand(String adminMenuAnswer) {
		switch (adminMenuAnswer) {
		case "Add product":
			return new AddProductMenuCommand(connection, printOut, buffReader);
		case "Delete product":
			return new DeleteProductMenuCommand(connection, printOut, buffReader);
		case "Update order status":
			return new UpdateOrderStatusCommand(connection, printOut, buffReader);
		case "Main menu":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}