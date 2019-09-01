package commands.action.getBasket;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.menus.user.BasketMenuCommand;
import items.Item;

public class GetUserBasketActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public GetUserBasketActionCommand(Connection connection, PrintStream printOut, BufferedReader buffReader,
			User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println(String.format("%s basket", user.getUserName()));
		printOut.flush();
		userBasket();

		return new BasketMenuCommand(connection, printOut, buffReader, user, parent);
	}

	public void userBasket() {
		for (Item product : user.getBasket()) {
			printOut.println(product);
		}
	}
}