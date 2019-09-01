package commands.inputs.buy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.action.buy.BuyProductDrinkActionCommand;
import items.DrinkItem;

public class GetDrinkInputBuyCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;
	private Command nextCommand;

	public GetDrinkInputBuyCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user,
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
			printOut.println("Please enter drink id");
			printOut.println("Your input please: ");
			printOut.flush();
			int drinkID = Integer.parseInt(buffReader.readLine());

			printOut.println("Please enter amount");
			printOut.println("Your input please: ");
			printOut.flush();
			int count = Integer.parseInt(buffReader.readLine());

			DrinkItem drink = new DrinkItem(drinkID, count);
			return new BuyProductDrinkActionCommand(connection, printOut, drink, user, nextCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}