package commands.inputs.buy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.actions.buy.BuyProductDrinkActionCommand;
import items.DrinkItem;
import items.User;

public class GetDrinkInputBuyCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public GetDrinkInputBuyCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
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
			
			printOut.println("Please enter count");
			printOut.println("Your input please: ");
			printOut.flush();
			int count = Integer.parseInt(buffReader.readLine());
			
			DrinkItem drink = new DrinkItem(drinkType, count, brand);
			return new BuyProductDrinkActionCommand(connection, printOut, drink, user, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}