package commands.action.getProducts;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.User;
import commands.Command;
import commands.inputs.buy.GetSaladInputBuyCommand;

public class GetAllSaladsCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private User user;

	public GetAllSaladsCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, User user) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("That`s all available salads");
		List<String> allSalads = new ArrayList<>();
		try {
			allSalads.addAll(getSalads());
			for (String product : allSalads) {
				printOut.println(product);
			}
			printOut.flush();
			return new GetSaladInputBuyCommand(connection, printOut, buffReader, user, parent);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getSalads() throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM salads").executeQuery();
		List<String> saladsList = new ArrayList<>();
		while (resultSet.next()) {
			String salad = String.format("Salad id: %d, Salad name: %s, Ingredients: %s, Price: %.2f",
					resultSet.getInt("id"), resultSet.getString("salad_name"), resultSet.getString("ingredients"),
					resultSet.getDouble("price"));
			saladsList.add(salad);
		}
		return saladsList;
	}
}