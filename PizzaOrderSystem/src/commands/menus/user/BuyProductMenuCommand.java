package commands.menus.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.actions.GetUserOrdersActionCommand;
import commands.inputs.buy.GetDrinkInputBuyCommand;
import commands.inputs.buy.GetPizzaInputBuyCommand;
import commands.inputs.buy.GetSaladInputBuyCommand;
import commands.menus.MainMenuCommand;
import exceptions.InputOptionException;

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
		try {
			printOut.println("Buy product menu: 1.Pizza 2.Salad 3.Drink 4.My orders 5.User menu 6.Main menu");
			printOut.println("Your input please: ");
			printOut.flush();
			String buyProductAnswer = buffReader.readLine();

			return getNextCommand(buyProductAnswer, parent);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.flush();
			return new BuyProductMenuCommand(connection, printOut, buffReader, user);
		}
		return null;
	}

	private Command getNextCommand(String buyProductAnswer, Command parent) throws InputOptionException {
		switch (buyProductAnswer) {
		case "Pizza":
			return new GetPizzaInputBuyCommand(connection, printOut, buffReader, user);
		case "Salad":
			return new GetSaladInputBuyCommand(connection, printOut, buffReader, user);
		case "Drink":
			return new GetDrinkInputBuyCommand(connection, printOut, buffReader, user);
		case "My orders":
			return new GetUserOrdersActionCommand(connection, printOut, user);
		case "User menu":
			return new LoggedInUserMenuCommand(connection, printOut, buffReader, user);
		case "Main menu":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}