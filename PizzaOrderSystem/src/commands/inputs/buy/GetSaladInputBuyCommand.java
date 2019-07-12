package commands.inputs.buy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import client.User;
import commands.Command;
import commands.action.buy.BuyProductSaladActionCommand;
import items.SaladItem;

public class GetSaladInputBuyCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public GetSaladInputBuyCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Please enter salad name");
			printOut.println("Your input please: ");
			printOut.flush();
			String saladName = buffReader.readLine();
			
			printOut.println("Please enter price");
			printOut.println("Your input please: ");
			printOut.flush();
			int count = Integer.parseInt(buffReader.readLine());

			SaladItem salad = new SaladItem(saladName, count);
			return new BuyProductSaladActionCommand(connection, printOut, salad, user, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}