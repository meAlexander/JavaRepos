package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.menus.LoggedInUserMenuCommand;
import commands.menus.MainMenuCommand;
import exceptions.LoginException;
import items.User;

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
		printOut.println("Please enter your username");

		try {
			printOut.println("Your input please: ");
			printOut.flush();
			String userName = buffReader.readLine();
			
			printOut.println("Please enter your password");
			printOut.println("Your input please: ");
			printOut.flush();
			String password = buffReader.readLine();

			if (checkUserInfo(userName, password)) {
				User user = new User(userName);
				return getNextCommand(user);
			} else {
				throw new LoginException();
			}
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new MainMenuCommand(connection, printOut, buffReader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private Command getNextCommand(User user) {
		return new LoggedInUserMenuCommand(connection, printOut, buffReader, user);
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