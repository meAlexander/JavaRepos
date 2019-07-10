package commands;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private String user;
	private List<Item> basket = new ArrayList<Item>();
	
	public UserCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, String user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}
	@Override
	public Command execute(Command parent) {
		
		return new BuyProductMenuCommand(connection, printOut, buffReader, user, basket);
	}
}