package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.AddUserExceptions;
import exceptions.RegistrationException;

public class RegistrationAdminCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^*&+=]).{8,}";
	private static final String EMAIL_PATTERN = "^[a-z]+[A-Za-z0-9_.-]{4,}+@[a-z]{2,6}+\\.[a-z]{2,5}";

	public RegistrationAdminCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter adminName, password, phone, mail");
		printOut.println("Your input please: ");
		printOut.flush();
		
		String user, pass, phone, email;
		try {
			user = buffReader.readLine();
			printOut.println("Your input please: ");
			pass = buffReader.readLine();
			printOut.println("Your input please: ");
			phone = buffReader.readLine();
			printOut.println("Your input please: ");
			email = buffReader.readLine();

			if (!(checkAdminName(connection, user)) && validatePass(pass) && !(validateEmailAdmin(connection, email))) {

				registerAdmin(connection, user, pass, phone, email);
			} else {
				throw new RegistrationException();
			}
			printOut.flush();
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RegistrationException e) {
			System.out.println(e.getMessage());
		} catch (AddUserExceptions e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public boolean checkAdminName(Connection connection, String admin) throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT adminName FROM admins WHERE adminName = '%s'", admin))
				.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}

	public boolean checkAdminInfo(Connection connection, String pass, String admin) throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement(String
						.format("SELECT adminName FROM admins WHERE adminName = '%s' AND password = '%s'", admin, pass))
				.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;
	}

	public boolean validateEmailAdmin(Connection connection, String email) throws SQLException {
		Pattern pattern1 = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher1 = pattern1.matcher(email);

		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT email FROM admins WHERE email = '%s'", email)).executeQuery();

		if (resultSet.next() && matcher1.matches()) {
			return true;
		}
		return false;
	}

	public boolean validatePass(String pass) throws SQLException {
		Pattern pattern1 = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher1 = pattern1.matcher(pass);

		return matcher1.matches();
	}

	public void registerAdmin(Connection connection, String adminName, String password, String phone, String email)
			throws SQLException, AddUserExceptions {

		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO admins(adminName, password, phone, email) VALUES(?, ?, ?, ?)");
		ps.setString(1, adminName);
		ps.setString(2, password);
		ps.setString(3, phone);
		ps.setString(4, email);
		ps.execute();
//		if (!ps.execute()) {
//			throw new AddUserExceptions();
//		}
		printOut.println("Successfull registration!");
	}
}