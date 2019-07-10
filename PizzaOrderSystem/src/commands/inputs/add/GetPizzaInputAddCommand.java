package commands.inputs.add;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.actions.add.AddProductPizzaActionCommand;
import items.PizzaItem;

public class GetPizzaInputAddCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetPizzaInputAddCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Please enter pizza name");
			printOut.println("Your input please: ");
			printOut.flush();
			String pizzaName = buffReader.readLine();
			
			printOut.println("Please enter ingredients");
			printOut.println("Your input please: ");
			printOut.flush();
			String ingredients = buffReader.readLine();
			
			printOut.println("Please enter size");
			printOut.println("Your input please: ");
			printOut.flush();
			String size = buffReader.readLine();
			
			printOut.println("Please enter price");
			printOut.println("Your input please: ");
			printOut.flush();
			double price = Double.parseDouble(buffReader.readLine());
			
			PizzaItem pizza = new PizzaItem(pizzaName, ingredients, size, price);
			return new AddProductPizzaActionCommand(connection, printOut, pizza, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}