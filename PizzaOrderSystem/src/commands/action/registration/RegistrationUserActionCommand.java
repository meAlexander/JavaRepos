package commands.action.registration;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.User;
import commands.Command;
import exceptions.FinalRegistrationExceptions;
import exceptions.RegistrationEmailException;
import exceptions.RegistrationNameException;
import exceptions.RegistrationPasswordException;

public class RegistrationUserActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private Command nextCommand;

	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^*&+=]).{8,}";
	private static final String EMAIL_PATTERN = "^[a-z]+[A-Za-z0-9_.-]{4,}+@[a-z]{2,6}+\\.[a-z]{2,5}";

	public RegistrationUserActionCommand(Connection connection, PrintStream printOut, User user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			validations();
			printOut.println("Successfull user registration!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RegistrationNameException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (RegistrationPasswordException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (RegistrationEmailException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		} catch (FinalRegistrationExceptions e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	private void validations() throws SQLException, RegistrationNameException, RegistrationPasswordException,
			RegistrationEmailException, FinalRegistrationExceptions {
		if (validateUserName()) {
			if (validatePass()) {
				if (validateUserEmail()) {
					registerUser();
				} else {
					throw new RegistrationEmailException();
				}
			} else {
				throw new RegistrationPasswordException();
			}
		} else {
			throw new RegistrationNameException();
		}
	}

	public boolean validateUserName() throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT username FROM users WHERE username = '%s'", user))
				.executeQuery();

		if (resultSet.next()) {
			return false;
		}
		return true;
	}

	public boolean validateUserEmail() throws SQLException {
		Pattern pattern1 = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher1 = pattern1.matcher(user.getEmail());

		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT email FROM users WHERE email = '%s'", user.getEmail()))
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

	public void registerUser() throws SQLException, FinalRegistrationExceptions {
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO users(username, password, phone, email) VALUES(?, ?, ?, ?)");
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getPhone());
		ps.setString(4, user.getEmail());

		if (ps.execute()) {
			throw new FinalRegistrationExceptions();
		}
	}
}