package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class LoginMenu implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	
	public LoginMenu(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Login menu: 1.User 2.Admin 3.Main menu");
		printOut.println("Your input please: ");
		printOut.flush();
		
		try {
			String userLoginAnswer = buffReader.readLine();
			return getNextCommand(userLoginAnswer);
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new LoginMenu(connection, printOut, buffReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Command getNextCommand(String userLoginAnswer) {
		System.out.println("Returning: " + userLoginAnswer);
		switch (userLoginAnswer) {
		case "User":
			return new LoginUserCommand(connection, printOut, buffReader);
		case "Admin":
			return new LoginAdminCommand(connection, printOut, buffReader);
		case "Main menu":
			return new MainMenu(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}