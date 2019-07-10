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

public class BuyProductSaladCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private BufferedReader buffReader;
	private String user;
	private List<Item> basket;

	public BuyProductSaladCommand(Connection connection, PrintStream printOut, BufferedReader buffReader, String user, List<Item> basket) {
		this.connection = connection;
		this.printOut = printOut;
		this.buffReader = buffReader;
		this.user = user;
		this.basket = basket;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("Please enter salad and count you want to buy");
		printOut.println("Your input please: ");
		printOut.flush();

		try {
			String salad = buffReader.readLine();
			printOut.println("Your input please: ");
			printOut.flush();
			int count = Integer.parseInt(buffReader.readLine());
			Item saladItem = new Item(salad, count);
			
			basket.add(saladItem);
			//buySalad(salad);
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
		ResultSet rs = connection
				.prepareStatement(String.format("SELECT id FROM users WHERE username = '%s'", user))
				.executeQuery();

		rs.next();
		return rs.getInt("id");
	}

	public void buySalad(String salad) throws SQLException, IOException, BuyProductException {
		ResultSet rs = connection
				.prepareStatement(String.format("SELECT id FROM salads WHERE salad_name = '%s'", salad))
				.executeQuery();

		if (!rs.next()) {
			throw new BuyProductException();
		}
		acceptSaladOrder(rs.getInt("id"), getUserId());
	}

	public void acceptSaladOrder(int salad_id, int userId) throws SQLException, BuyProductException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO orders(pizza_id, salad_id, drink_id, user_id) VALUES(NULL, ?, NULL, ?)");
		ps.setInt(1, salad_id);
		ps.setInt(2, userId);

		if (ps.execute()) {
			throw new BuyProductException();
		}
	}
}