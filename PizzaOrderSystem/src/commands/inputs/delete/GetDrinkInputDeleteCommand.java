package commands.inputs.delete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.action.delete.DeleteProductDrinkActionCommand;
import items.DrinkItem;

public class GetDrinkInputDeleteCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetDrinkInputDeleteCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Please enter drink type");
			printOut.println("Your input please: ");
			printOut.flush();
			String drinkType = buffReader.readLine();
			
			printOut.println("Please enter brand");
			printOut.println("Your input please: ");
			printOut.flush();
			String brand = buffReader.readLine();
			
			DrinkItem drink = new DrinkItem(drinkType, brand);
			return new DeleteProductDrinkActionCommand(connection, printOut, drink, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}