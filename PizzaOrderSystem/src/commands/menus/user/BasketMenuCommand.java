package commands.menus.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.PurchaseActionCommand;

public class BasketMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;
	private Command nextCommand;

	public BasketMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user,
			Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Basket menu: 1.Purchase 2.Delete product from basket 3.Buy product menu");
			printOut.println("Your input please: ");
			printOut.flush();

			String basketAnswer = buffReader.readLine();
			return getNextCommand(basketAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Command getNextCommand(String basketAnswer) {
		switch (basketAnswer) {
		case "Purchase":
		case "1":
			return new PurchaseActionCommand(connection, printOut, user, nextCommand);
		case "Delete product from basket":
		case "2":
			return nextCommand;
		case "Buy product menu":
		case "3":
			return nextCommand;
		default:
			return new BasketMenuCommand(connection, printOut, buffReader, user, nextCommand);
		}
	}
}