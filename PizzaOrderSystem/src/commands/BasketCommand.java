package commands;

import java.io.PrintStream;
import java.util.List;

public class BasketCommand implements Command {
	private PrintStream printOut;
	private List<Item> basket;

	public BasketCommand(PrintStream printOut, List<Item> basket) {
		this.printOut = printOut;
		this.basket = basket;
	}

	@Override
	public Command execute(Command parent) {
		
		printOut.println("That`s all products");

		for (Item product : basket) {
			printOut.println(product.toString());
		}
		printOut.flush();
		return parent;
	}
}