package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import exceptions.BuyProductException;

public class BuyProductDrinkCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private String user;
	private List<Item> basket;

	public BuyProductDrinkCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, String user, List<Item> basket) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
		this.basket = basket;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter drinkType and count you want to buy");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String drinkType = buffReader.readLine();
//			printOut.println("Your input please: ");
//			printOut.flush();
//			String brand = buffReader.readLine();
			printOut.println("Your input please: ");
			printOut.flush();
			int count = Integer.parseInt(buffReader.readLine());
			Item drink = new Item(drinkType, count);
			
			basket.add(drink);
			//buyDrink(drinkType, brand, user);
			printOut.flush();
			return parent;		
		} catch (IOException e) {
			e.printStackTrace();
		} 
//		catch (SQLException e) {
//			e.printStackTrace();
//		} catch (BuyProductException e) {
//			System.out.println(e.getMessage());
//		}
		return null;
	}

	public int getUserId() throws SQLException {
		ResultSet resultSet = connection
				.prepareStatement(String.format("SELECT id FROM users WHERE username = '%s'", this.user))
				.executeQuery();

		resultSet.next();
		return resultSet.getInt("id");
	}

	public void buyDrink(String drinkType, String brand)
			throws SQLException, IOException, BuyProductException {
		ResultSet resultSet = connection.prepareStatement(
				String.format("SELECT id FROM drinks WHERE drink_type = '%s' AND brand = '%s'", drinkType, brand))
				.executeQuery();

		if (!resultSet.next()) {
			throw new BuyProductException();
		}
		acceptDrinkOrder(resultSet.getInt("id"), getUserId());
	}

	public void acceptDrinkOrder(int drink_id, int userId) throws SQLException, BuyProductException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO orders(pizza_id, salad_id, drink_id, user_id) VALUES(NULL, NULL, ?, ?)");
		ps.setInt(1, drink_id);
		ps.setInt(2, userId);

		if (ps.execute()) {
			throw new BuyProductException();
		}
	}
}