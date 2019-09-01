package commands.menus.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.inputs.add.GetDrinkInputAddCommand;
import commands.inputs.add.GetPizzaInputAddCommand;
import commands.inputs.add.GetSaladInputAddCommand;
import commands.menus.MainMenuCommand;
import exceptions.InputOptionException;

public class AddProductMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public AddProductMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Add product menu: 1.Pizza 2.Salad 3.Drink 4.Admin menu 5.Main menu");
			printOut.println("Your input please: ");
			printOut.flush();

			String addProductAnswer = buffReader.readLine();
			return getNextCommand(addProductAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.flush();
			return new AddProductMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	private Command getNextCommand(String addProductAnswer) throws InputOptionException {
		switch (addProductAnswer) {
		case "Pizza":
		case "1":
			return new GetPizzaInputAddCommand(connection, printOut, buffReader);
		case "Salad":
		case "2":
			return new GetSaladInputAddCommand(connection, printOut, buffReader);
		case "Drink":
		case "3":
			return new GetDrinkInputAddCommand(connection, printOut, buffReader);
		case "Admin menu":
		case "4":
			return new LoggedInAdminMenuCommand(connection, printOut, buffReader);
		case "Main menu":
		case "5":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}