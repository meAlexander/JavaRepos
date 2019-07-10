package commands.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.PurchaseCommand;
import commands.ViewBasketCommand;
import commands.inputs.buy.GetDrinkInputBuyCommand;
import commands.inputs.buy.GetPizzaInputBuyCommand;
import commands.inputs.buy.GetSaladInputBuyCommand;
import items.User;

public class BuyProductMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public BuyProductMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
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
			return new BuyProductMenuCommand(connection, printOut, buffReader, user);
		}
		return null;
	}

	private Command getNextCommand(String buyProductAnswer) {
		System.out.println("Returning: " + buyProductAnswer);
		switch (buyProductAnswer) {
		case "Pizza":
			return new GetPizzaInputBuyCommand(connection, printOut, buffReader, user);
		case "Salad":
			return new GetSaladInputBuyCommand(connection, printOut, buffReader, user);
		case "Drink":
			return new GetDrinkInputBuyCommand(connection, printOut, buffReader, user);
		case "View basket":
			return new ViewBasketCommand(printOut, user);
		case "Purchase":
			return new PurchaseCommand(connection, printOut, user);
		case "Main menu":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}