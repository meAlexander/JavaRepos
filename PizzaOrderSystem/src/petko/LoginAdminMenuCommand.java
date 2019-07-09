package petko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class LoginAdminMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public LoginAdminMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Login admin menu: 1.Add products 2.Delete products 3.Main menu");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String adminMenuAnswer = buffReader.readLine();
			return getNextCommand(adminMenuAnswer);
		} catch (UnsupportedOperationException e) {
			printOut.flush();
			return new LoginAdminMenuCommand(connection, printOut, buffReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Command getNextCommand(String adminMenuAnswer) {
		System.out.println("Returning: " + adminMenuAnswer);
		switch (adminMenuAnswer) {
		case "Add Product":
			// return new BuyProductsCommand(connection, printOut, buffReader);
		case "Delete product":
			// return new DeleteProductCommand(connection, printOut, buffReader);
		case "Main menu":
			return new MainMenu(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}