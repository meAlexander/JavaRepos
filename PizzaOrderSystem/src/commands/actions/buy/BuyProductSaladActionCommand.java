package commands.actions.buy;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commands.Command;
import exceptions.BuyProductException;
import items.SaladItem;
import items.User;

public class BuyProductSaladActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private SaladItem salad;
	private Command nextCommand;

	public BuyProductSaladActionCommand(Connection connection, PrintStream printOut, SaladItem salad, User user, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.salad = salad;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			buySalad();
			printOut.flush();
			return nextCommand;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BuyProductException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void buySalad() throws SQLException, IOException, BuyProductException {
		ResultSet rs = connection
				.prepareStatement(String.format("SELECT id FROM salads WHERE salad_name = '%s'", salad.getName())).executeQuery();

		if (!rs.next()) {
			throw new BuyProductException();
		}
		acceptSaladOrder();
	}

	public void acceptSaladOrder() throws SQLException, BuyProductException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO orders(itemName, count, username) VALUES(?, ?, ?)");
		ps.setString(1, salad.getName());
		ps.setInt(2, salad.getCount());
		ps.setString(3, user.getUserName());

		if (ps.execute()) {
			throw new BuyProductException();
		}
	}
}