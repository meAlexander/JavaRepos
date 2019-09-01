package commands.action.buy;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import commands.Command;
import exceptions.ProductException;
import items.SaladItem;

public class BuyProductSaladActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private User user;
	private SaladItem salad;
	private Command nextCommand;

	public BuyProductSaladActionCommand(Connection connection, PrintStream printOut, SaladItem salad, User user,
			Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.user = user;
		this.salad = salad;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			checkSaladInfo();
			printOut.println("Product added to the basket!");
			printOut.flush();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ProductException e) {
			printOut.println(e.getMessage());
			printOut.flush();
		}
		return nextCommand;
	}

	public void addSalad(ResultSet resultSet) throws SQLException, ProductException {
		SaladItem newSalad = new SaladItem(resultSet.getString("salad_name"), salad.getAmount(),
				resultSet.getDouble("price"));
		user.getBasket().add(newSalad);
	}

	public void checkSaladInfo() throws SQLException, ProductException {
		ResultSet resultSet = connection
				.prepareStatement(
						String.format("SELECT salad_name, price FROM salads WHERE id = %d", salad.getSaladID()))
				.executeQuery();

		if (!resultSet.next()) {
			throw new ProductException();
		}
		addSalad(resultSet);
	}
}