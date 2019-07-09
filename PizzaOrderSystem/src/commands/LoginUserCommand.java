package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.LoginException;

public class LoginUserCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	
	public LoginUserCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter your username, password");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String user = buffReader.readLine();
			printOut.println("Your input please: ");
			String pass = buffReader.readLine();
			
			if (checkUserInfo(user, pass)) {
				return getNextCommand("True", user);
			} else {
				throw new LoginException();
			}
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new MainMenu(connection, printOut, buffReader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private Command getNextCommand(String userOptionAnswer, String user) {
		System.out.println("Returning: " + userOptionAnswer);
		switch (userOptionAnswer) {
		case "True":
			return new LoginUserMenuCommand(connection, printOut, buffReader, user);
		default:
			throw new UnsupportedOperationException();
		}
	}

	public boolean checkUserInfo(String user, String pass) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				String.format("SELECT username FROM users WHERE username = '%s' AND password = '%s'", user, pass))
				.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}
}