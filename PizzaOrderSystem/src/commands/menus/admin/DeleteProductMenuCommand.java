package commands.menus.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.inputs.delete.GetDrinkInputDeleteCommand;
import commands.inputs.delete.GetPizzaInputDeleteCommand;
import commands.inputs.delete.GetSaladInputDeleteCommand;
import commands.menus.MainMenuCommand;
import exceptions.InputOptionException;

public class DeleteProductMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public DeleteProductMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Delete product menu: 1.Pizza 2.Salad 3.Drink 4.Admin menu 5.Main menu");
			printOut.println("Your input please: ");
			printOut.flush();

			String deleteProductAnswer = buffReader.readLine();
			return getNextCommand(deleteProductAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.flush();
			return new DeleteProductMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	private Command getNextCommand(String deleteProductAnswer) throws InputOptionException {
		switch (deleteProductAnswer) {
		case "Pizza":
		case "1":
			return new GetPizzaInputDeleteCommand(connection, printOut, buffReader);
		case "Salad":
		case "2":
			return new GetSaladInputDeleteCommand(connection, printOut, buffReader);
		case "Drink":
		case "3":
			return new GetDrinkInputDeleteCommand(connection, printOut, buffReader);
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