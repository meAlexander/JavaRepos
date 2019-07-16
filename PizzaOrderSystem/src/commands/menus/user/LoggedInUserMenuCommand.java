package commands.menus.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.action.getProducts.GetAllProductsCommand;
import commands.menus.MainMenuCommand;
import exceptions.InputOptionException;

public class LoggedInUserMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public LoggedInUserMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Login user menu: 1.Buy products 2.View products 3.Main menu");
			printOut.println("Your input please: ");
			printOut.flush();
			String userMenuAnswer = buffReader.readLine();

			return getNextCommand(userMenuAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.flush();
			return new LoggedInUserMenuCommand(connection, printOut, buffReader, user);
		}
		return null;
	}

	private Command getNextCommand(String userMenuAnswer) throws InputOptionException {
		switch (userMenuAnswer) {
		case "Buy products":
		case "1":
			return new BuyProductMenuCommand(connection, printOut, buffReader, user);
		case "View products":
		case "2":
			return new GetAllProductsCommand(connection, printOut);
		case "Main menu":
		case "3":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}