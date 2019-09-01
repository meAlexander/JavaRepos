package commands.menus.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.inputs.updateOrderStatus.GetOrderInputUpdateCommand;
import commands.menus.MainMenuCommand;
import exceptions.InputOptionException;

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
		try {
			printOut.println("Login admin menu: 1.Add product 2.Delete product 3.Update order status 4.Main menu");
			printOut.println("Your input please: ");
			printOut.flush();

			String adminMenuAnswer = buffReader.readLine();
			return getNextCommand(adminMenuAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.flush();
			return new LoggedInAdminMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	private Command getNextCommand(String adminMenuAnswer) throws InputOptionException {
		switch (adminMenuAnswer) {
		case "Add product":
		case "1":
			return new AddProductMenuCommand(connection, printOut, buffReader);
		case "Delete product":
		case "2":
			return new DeleteProductMenuCommand(connection, printOut, buffReader);
		case "Update order status":
		case "3":
			return new GetOrderInputUpdateCommand(connection, printOut, buffReader);
		case "Main menu":
		case "4":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}