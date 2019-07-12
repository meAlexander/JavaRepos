package commands.inputs.add;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.action.add.AddProductDrinkActionCommand;
import items.DrinkItem;

public class GetDrinkInputAddCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetDrinkInputAddCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
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

			printOut.println("Please enter quantity");
			printOut.println("Your input please: ");
			printOut.flush();
			int quantity = Integer.parseInt(buffReader.readLine());

			printOut.println("Please enter price");
			printOut.println("Your input please: ");
			printOut.flush();
			double price = Double.parseDouble(buffReader.readLine());

			DrinkItem drink = new DrinkItem(drinkType, brand, price, quantity);
			return new AddProductDrinkActionCommand(connection, printOut, drink, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}