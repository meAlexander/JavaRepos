package commands.inputs.registration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.action.registration.RegistrationAdminActionCommand;

public class GetAdminInputRegistrationCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetAdminInputRegistrationCommand(Connection connection, BufferedReader buffReader, PrintStream printOut) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Please enter adminName");
			printOut.println("Your input please: ");
			printOut.flush();
			String adminName = buffReader.readLine();

			printOut.println("Please enter password");
			printOut.println("Your input please: ");
			printOut.flush();
			String password = buffReader.readLine();

			printOut.println("Please enter phone");
			printOut.println("Your input please: ");
			printOut.flush();
			String phone = buffReader.readLine();

			printOut.println("Please enter mail");
			printOut.println("Your input please: ");
			printOut.flush();
			String email = buffReader.readLine();

			User user = new User(adminName, password, phone, email);
			return new RegistrationAdminActionCommand(connection, printOut, buffReader, user, parent);
		} catch (IOException e) {
			printOut.println("Error input");
		}
		return null;
	}
}