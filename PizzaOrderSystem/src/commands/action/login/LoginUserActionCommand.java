package commands.action.login;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;
import commands.menus.LoginMenuCommand;
import commands.menus.user.LoggedInUserMenuCommand;
import exceptions.LoginException;

public class LoginUserActionCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;
	
	public LoginUserActionCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		try {
			if (checkUserInfo()) {
				return new LoggedInUserMenuCommand(connection, printOut, buffReader, user);
			}else {
				throw new LoginException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginException e) {
			printOut.println(e.getMessage());
			return new LoginMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	public boolean checkUserInfo() throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				String.format("SELECT username FROM users WHERE username COLLATE utf8mb4_0900_as_cs LIKE '%s' AND password LIKE '%s'", user.getUserName(), user.getPassword()))
				.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}
}