package commands.menus.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.action.getBasket.GetUserBasketActionCommand;
import commands.action.getOrders.GetUserOrdersActionCommand;
import commands.action.getProducts.GetAllDrinksCommand;
import commands.action.getProducts.GetAllPizzasCommand;
import commands.action.getProducts.GetAllSaladsCommand;
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
			printOut.println("Buy product menu: 1.Pizza 2.Salad 3.Drink 4.My orders 5.My basket 6.User menu 7.Main menu");
			printOut.println("Your input please: ");
			printOut.flush();
			
			String buyProductAnswer = buffReader.readLine();
			return getNextCommand(buyProductAnswer, parent);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InputOptionException e) {
			printOut.println("Wrong option!");
			printOut.flush();
			return new BuyProductMenuCommand(connection, printOut, buffReader, user);
		}
		return null;
	}

	private Command getNextCommand(String buyProductAnswer, Command parent) throws InputOptionException {
		switch (buyProductAnswer) {
		case "Pizza":
		case "1":
			return new GetAllPizzasCommand(connection, printOut, buffReader, user);
		case "Salad":
		case "2":
			return new GetAllSaladsCommand(connection, printOut, buffReader, user);
		case "Drink":
		case "3":
			return new GetAllDrinksCommand(connection, printOut, buffReader, user);
		case "My orders":
		case "4":
			return new GetUserOrdersActionCommand(connection, printOut, user);
		case "My basket":
		case "5":
			return new GetUserBasketActionCommand(connection, printOut, buffReader, user);
		case "User menu":
		case "6":
			return new LoggedInUserMenuCommand(connection, printOut, buffReader, user);
		case "Main menu":
		case "7":
			return new MainMenuCommand(connection, printOut, buffReader);
		default:
			throw new InputOptionException();
		}
	}
}