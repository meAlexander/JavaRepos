package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class RegistrationMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public RegistrationMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Registration menu: 1.User 2.Admin 3.Main menu");
		printOut.println("Your input please: ");
		printOut.flush();
		
		try {
			String userRegistrationAnswer = buffReader.readLine();
			return getNextCommand(userRegistrationAnswer);
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new RegistrationMenuCommand(connection, printOut, buffReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Command getNextCommand(String userRegistrationAnswer) {
		System.out.println("Returning: " + userRegistrationAnswer);
		switch (userRegistrationAnswer) {
		case "User":
			return new RegistrationUserCommand(connection, printOut, buffReader);
		case "Admin":
			return new RegistrationAdminCommand(connection, printOut, buffReader);
		case "Main menu":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}