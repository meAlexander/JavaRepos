package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class AddProductsMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	
	public AddProductsMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Buy product menu: 1.Pizzas 2.Salads 3.Drinks 4.Main menu");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String buyProductAnswer = buffReader.readLine();
			return getNextCommand(buyProductAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (UnsupportedOperationException e) {
			printOut.flush();
			return new AddProductsMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	private Command getNextCommand(String buyProductAnswer) {
		System.out.println("Returning: " + buyProductAnswer);
		switch (buyProductAnswer) {
		case "Pizzas":
			return new AddProductPizzaCommand(connection, printOut, buffReader);
		case "Salads":
			return new AddProductSaladCommand(connection, printOut, buffReader);
		case "Drinks":
			return new AddProductDrinkCommand(connection, printOut, buffReader);
		case "Main menu":
			return new MainMenu(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}	
}