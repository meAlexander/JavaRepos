package commands.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.action.getProducts.GetAllProductsCommand;
import exceptions.InputOptionException;

public class MainMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public MainMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Main menu: 1.Login 2.Registration 3.View products");
			printOut.println("Your input please: ");
			printOut.flush();
			String userMenuAnswer = buffReader.readLine();

			return getNextCommand(userMenuAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.flush();
			return new MainMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	private Command getNextCommand(String userMenuAnswer) throws InputOptionException {
		switch (userMenuAnswer) {
		case "Login":
		case "1":
			return new LoginMenuCommand(connection, printOut, buffReader);
		case "Registration":
		case "2":
			return new RegistrationMenuCommand(connection, printOut, buffReader);
		case "View products":
		case "3":
			return new GetAllProductsCommand(connection, printOut);
		default:
			throw new InputOptionException();
		}
	}
}