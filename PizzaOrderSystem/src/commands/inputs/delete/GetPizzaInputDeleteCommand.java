package commands.inputs.delete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.action.delete.DeleteProductPizzaActionCommand;
import items.PizzaItem;

public class GetPizzaInputDeleteCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetPizzaInputDeleteCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
//			printOut.println("Please enter pizza name");
//			printOut.println("Your input please: ");
//			printOut.flush();
//			String pizzaName = buffReader.readLine();
//			
//			printOut.println("Please enter size");
//			printOut.println("Your input please: ");
//			printOut.flush();
//			String size = buffReader.readLine();
			
			printOut.println("Please enter pizza id");
			printOut.println("Your input please: ");
			printOut.flush();
			int pizzaID = Integer.parseInt(buffReader.readLine());
			
			PizzaItem pizza = new PizzaItem(pizzaID);
			return new DeleteProductPizzaActionCommand(connection, printOut, pizza, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}