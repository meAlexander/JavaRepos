package petko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class MainMenuCommand implements Command {

	private PrintStream printOut;
	private BufferedReader buffReader;
	private Connection connection;

	public MainMenuCommand(PrintStream printOut, BufferedReader buffReader, Connection connection) {
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.connection = connection;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Choose option: 1.Log in 2.Registration 3.View products");
		printOut.println("Your input please: ");
		printOut.flush();
		try {
			String userMenuAnswer = buffReader.readLine();
			return getNextCommand(userMenuAnswer);
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new MainMenuCommand(printOut, buffReader, connection);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Command getNextCommand(String userMenuAnswer) {
		System.out.println("Returning: " + userMenuAnswer);
		switch (userMenuAnswer) {
		case "Log in":
		//	return new LoginCommand(printOut, buffReader);
			break;
		case "Registration":
		//	return new RegistrationCommand(printOut, buffReader);
			break;
		case "View products":
			return new ViewProductsCommand(printOut, connection);
		default:
			throw new UnsupportedOperationException("Wrong choice");
		}
		System.out.println("Returning null: " + userMenuAnswer);
		return null;
	}
}