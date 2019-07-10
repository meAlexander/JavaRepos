package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.List;

import items.Item;

public class BuyProductMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private String user;
	private List<Item> basket;

	public BuyProductMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, String user, List<Item> basket) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
		this.basket = basket;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Buy product menu: 1.Pizza 2.Salad 3.Drink 4.View basket 5.Purchase 5.Main menu");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String buyProductAnswer = buffReader.readLine();
			return getNextCommand(buyProductAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (UnsupportedOperationException e) {
			printOut.flush();
			return new BuyProductMenuCommand(connection, printOut, buffReader, user, basket);
		}
		return null;
	}

	private Command getNextCommand(String buyProductAnswer) {
		System.out.println("Returning: " + buyProductAnswer);
		switch (buyProductAnswer) {
		case "Pizza":
			return new BuyProductPizzaCommand(connection, printOut, buffReader, user, basket);
		case "Salad":
			return new BuyProductSaladCommand(connection, printOut, buffReader, user, basket);
		case "Drink":
			return new BuyProductDrinkCommand(connection, printOut, buffReader, user, basket);
		case "View basket":
			return new ViewBasketCommand(printOut, basket);
		case "Purchase":
			return new PurchaseCommand(connection, printOut, user, basket);
		case "Main menu":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}