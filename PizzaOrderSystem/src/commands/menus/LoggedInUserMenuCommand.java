package commands.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.ViewProductsCommand;
import items.User;

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
		printOut.println("Login user menu: 1.Buy products 2.View products 3.Main menu");
		printOut.println("Your input please: ");
		printOut.flush();
		
		try {
			String userMenuAnswer = buffReader.readLine();
			return getNextCommand(userMenuAnswer);
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new LoggedInUserMenuCommand(connection, printOut, buffReader, user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Command getNextCommand(String userMenuAnswer) {
		switch (userMenuAnswer) {
		case "Buy products":
			return new BuyProductMenuCommand(connection, printOut, buffReader, user);
		case "View products":
			return new ViewProductsCommand(connection, printOut);
		case "Main menu":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}