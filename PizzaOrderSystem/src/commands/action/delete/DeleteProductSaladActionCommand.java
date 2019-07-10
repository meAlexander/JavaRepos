package commands.action.delete;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commands.Command;
import exceptions.DeleteProductException;
import items.SaladItem;

public class DeleteProductSaladActionCommand implements Command {
	private Connection connection;
	private PrintStream printOut;
	private SaladItem salad;
	private Command nextCommand;
	
	public DeleteProductSaladActionCommand(Connection connection, PrintStream printOut, SaladItem salad, Command nextCommand) {
		this.connection = connection;
		this.printOut = printOut;
		this.salad = salad;
		this.nextCommand = nextCommand;
	}

	@Override
	public Command execute(Command parent) {
		try {
			deleteSalad();
			printOut.println("Salad deleted!");
			printOut.flush();
			return nextCommand;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DeleteProductException e) {
			printOut.println(e.getMessage());
		}
		return null;
	}
	
	public void deleteSalad()
			throws SQLException, IOException, DeleteProductException {

		PreparedStatement ps = connection.prepareStatement("DELETE FROM salads WHERE salad_name = ?");
		ps.setString(1, salad.getName());
		
		if(ps.execute()) {
			throw new DeleteProductException();
		}
	}
}