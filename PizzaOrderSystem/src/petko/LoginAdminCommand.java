package petko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.LoginException;

public class LoginAdminCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	
	public LoginAdminCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter your adminName, password");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String admin = buffReader.readLine();
			printOut.println("Your input please: ");
			String pass = buffReader.readLine();
			
			if (checkAdminInfo(admin, pass)) {
				return getNextCommand("True", admin);
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
			return new LoginAdminMenuCommand(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
	
	public boolean checkAdminInfo(String admin, String pass) throws SQLException {

		ResultSet resultSet = connection.prepareStatement(String.format(
				"SELECT adminName, password" + " FROM admins WHERE adminName = '%s' AND password = '%s'", admin, pass))
				.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}
}