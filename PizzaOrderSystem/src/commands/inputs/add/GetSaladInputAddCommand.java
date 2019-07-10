package commands.inputs.add;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.actions.add.AddProductSaladActionCommand;
import items.SaladItem;

public class GetSaladInputAddCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetSaladInputAddCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
			printOut.println("Please enter salad name");
			printOut.println("Your input please: ");
			printOut.flush();
			String saladName = buffReader.readLine();
			
			printOut.println("Please enter ingrediens");
			printOut.println("Your input please: ");
			printOut.flush();
			String ingrediens = buffReader.readLine();
			
			printOut.println("Please enter price");
			printOut.println("Your input please: ");
			printOut.flush();
			double price = Double.parseDouble(buffReader.readLine());

			SaladItem salad = new SaladItem(saladName, price, ingrediens);
			return new AddProductSaladActionCommand(connection, printOut, salad, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}