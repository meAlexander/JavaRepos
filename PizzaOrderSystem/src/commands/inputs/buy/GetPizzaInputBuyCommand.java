package commands.inputs.buy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.actions.buy.BuyProductPizzaActionCommand;
import items.PizzaItem;
import items.User;

public class GetPizzaInputBuyCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public GetPizzaInputBuyCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Please enter pizza name");
			printOut.println("Your input please: ");
			printOut.flush();
			String pizzaName = buffReader.readLine();
			
			printOut.println("Please enter size");
			printOut.println("Your input please: ");
			printOut.flush();
			String size = buffReader.readLine();
			
			printOut.println("Please enter count");
			printOut.println("Your input please: ");
			printOut.flush();
			int count = Integer.parseInt(buffReader.readLine());
			
			PizzaItem pizza = new PizzaItem(pizzaName, count, size);
			return new BuyProductPizzaActionCommand(connection, printOut, pizza, user, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}