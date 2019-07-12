package commands.action.registration;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.User;
import commands.Command;
import commands.menus.RegistrationMenuCommand;
import exceptions.AddUserExceptions;
import exceptions.RegistrationException;

public class RegistrationAdminActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;
	private Command nextCommand;

	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^*&+=]).{8,}";
	private static final String EMAIL_PATTERN = "^[a-z]+[A-Za-z0-9_.-]{4,}+@[a-z]{2,6}+\\.[a-z]{2,5}";

	public RegistrationAdminActionCommand(Connection connection, PrintStream printOut, BufferedReader buffReader,
			User user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.nextCommand = nextCommand;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			if (!(checkAdminName()) && validatePass() && validateEmailAdmin()) {

				registerAdmin();
			} else {
				throw new RegistrationException();
			}

			printOut.println("Successfull admin registration!");
			printOut.flush();
			return nextCommand;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RegistrationException e) {
			printOut.println(e.getMessage());
			return new RegistrationMenuCommand(connection, printOut, buffReader);
		} catch (AddUserExceptions e) {
			printOut.println(e.getMessage());
		}
		return null;
	}

	public boolean checkAdminName() throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement(
						String.format("SELECT adminName FROM admins WHERE adminName = '%s'", user.getUserName()))
				.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}

	public boolean validateEmailAdmin() throws SQLException {
		Pattern pattern1 = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher1 = pattern1.matcher(user.getEmail());

		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT email FROM admins WHERE email = '%s'", user.getEmail()))
				.executeQuery();

		if (!(resultSet.next()) && matcher1.matches()) {
			return true;
		}
		return false;
	}

	public boolean validatePass() throws SQLException {
		Pattern pattern1 = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher1 = pattern1.matcher(user.getPassword());

		return matcher1.matches();
	}

	public void registerAdmin() throws SQLException, AddUserExceptions {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO admins(adminName, password, phone, email) VALUES(?, ?, ?, ?)");
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getPhone());
		ps.setString(4, user.getEmail());

		if (ps.execute()) {
			throw new AddUserExceptions();
		}
	}
}