package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

public class DeleteProductMenuCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	
	public DeleteProductMenuCommand(Connection connection, PrintStream printOut, BufferedReader buffReader) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Delete product menu: 1.Pizza 2.Salad 3.Drink 4.Main menu");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String deleteProductAnswer = buffReader.readLine();
			return getNextCommand(deleteProductAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (UnsupportedOperationException e) {
			printOut.flush();
			return new DeleteProductMenuCommand(connection, printOut, buffReader);
		}
		return null;
	}

	private Command getNextCommand(String deleteProductAnswer) {
		System.out.println("Returning: " + deleteProductAnswer);
		switch (deleteProductAnswer) {
		case "Pizza":
			return new DeleteProductPizzaCommand(connection, printOut, buffReader);
		case "Salad":
			return new DeleteProductSaladCommand(connection, printOut, buffReader);
		case "Drink":
			return new DeleteProductDrinkCommand(connection, printOut, buffReader);
		case "Main menu":
			return new MainMenu(connection, printOut, buffReader);
		default:
			throw new UnsupportedOperationException();
		}
	}
}