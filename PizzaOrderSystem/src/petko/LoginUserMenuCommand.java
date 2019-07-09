package petko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class LoginUserMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private String user;
	
	public LoginUserMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, String user) {
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
			return new LoginUserMenuCommand(connection, printOut, buffReader, user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Command getNextCommand(String userMenuAnswer) {
		System.out.println("Returning: " + userMenuAnswer);
		switch (userMenuAnswer) {
		case "Buy products":
			return new BuyProductMenuCommand(connection, printOut, buffReader, user);
		case "View products":
			return new ViewProductsCommand(connection, printOut);
		case "Main menu":
			return new MainMenu(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}