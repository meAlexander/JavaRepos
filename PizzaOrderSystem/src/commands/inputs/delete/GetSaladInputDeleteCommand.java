package commands.inputs.delete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

import commands.Command;
import commands.action.delete.DeleteProductSaladActionCommand;
import items.SaladItem;

public class GetSaladInputDeleteCommand implements Command{
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;

	public GetSaladInputDeleteCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}
	
	@Override
	public Command execute(Command parent) {
		try {
//			printOut.println("Please enter salad name");
//			printOut.println("Your input please: ");
//			printOut.flush();
//			String saladName = buffReader.readLine();
			
			printOut.println("Please enter salad id");
			printOut.println("Your input please: ");
			printOut.flush();
			int saladID = Integer.parseInt(buffReader.readLine());
			
			SaladItem salad = new SaladItem(saladID);
			return new DeleteProductSaladActionCommand(connection, printOut, salad, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}