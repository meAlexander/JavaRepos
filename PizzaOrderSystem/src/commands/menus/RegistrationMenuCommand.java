package commands.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.inputs.registration.GetAdminInputRegistrationCommand;
import commands.inputs.registration.GetUserInputRegistrationCommand;
import exceptions.InputOptionException;

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
		try {
			printOut.println("Registration menu: 1.User 2.Admin 3.Main menu");
			printOut.println("Your input please: ");
			printOut.flush();
			String userRegistrationAnswer = buffReader.readLine();

			return getNextCommand(userRegistrationAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.flush();
			return new RegistrationMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	private Command getNextCommand(String userRegistrationAnswer) throws InputOptionException {
		switch (userRegistrationAnswer) {
		case "User":
		case "1":
			return new GetUserInputRegistrationCommand(connection, buffReader, printOut);
		case "Admin":
		case "2":
			return new GetAdminInputRegistrationCommand(connection, buffReader, printOut);
		case "Main menu":
		case "3":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}