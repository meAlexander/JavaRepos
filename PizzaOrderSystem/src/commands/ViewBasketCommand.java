package commands;

import java.io.PrintStream;

import client.User;
import items.Item;

public class ViewBasketCommand implements Command {
	private PrintStream printOut;
	private User user;

	public ViewBasketCommand(PrintStream printOut, User user) {
		this.printOut = printOut;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		
		printOut.println("That`s all products");

		for (Item item : user.getBasket()) {
			printOut.println(item.toString());
		}
		printOut.flush();
		return parent;
	}
}