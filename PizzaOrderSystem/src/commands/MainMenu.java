package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class MainMenu implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public MainMenu(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Main menu: 1.Login 2.Registration 3.View products");
		printOut.println("Your input please: ");
		printOut.flush();
		
		try {
			String userMenuAnswer = buffReader.readLine();
			return getNextCommand(userMenuAnswer);
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new MainMenu(connection, printOut, buffReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Command getNextCommand(String userMenuAnswer) {
		System.out.println("Returning: " + userMenuAnswer);
		switch (userMenuAnswer) {
		case "Login":
			return new LoginMenu(connection, printOut, buffReader);
		case "Registration":
			return new RegistrationMenu(connection, printOut, buffReader);
		case "View products":
			return new ViewProductsCommand(connection, printOut);
		default:
			throw new UnsupportedOperationException();
		}
	}
}