package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class BuyProductMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private String user;

	public BuyProductMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, String user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
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
			return new BuyProductMenuCommand(connection, printOut, buffReader, user);
		}
		return null;
	}

	private Command getNextCommand(String buyProductAnswer) {
		System.out.println("Returning: " + buyProductAnswer);
		switch (buyProductAnswer) {
		case "Pizzas":
			return new BuyProductPizzaCommand(connection, printOut, buffReader, user);
		case "Salads":
			return new BuyProductSaladCommand(connection, printOut, buffReader, user);
		case "Drinks":
			return new BuyProductDrinkCommand(connection, printOut, buffReader, user);
		case "Main menu":
			return new MainMenu(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}