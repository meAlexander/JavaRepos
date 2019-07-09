package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import petko.Command;
import petko.MainMenu;

public class CommandFlowThread extends Thread{
	private Socket socket;
	private String address = "jdbc:mysql://localhost:3306/pizza_order";
	
	public CommandFlowThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		super.run();
		Command command = null;
		try {
			Connection connection = DriverManager.getConnection(address, "root", "123456");
			PrintStream printOut = new PrintStream(this.socket.getOutputStream());
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			command = new MainMenu(connection, printOut, buffReader);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Command previousCommand = null;
		while(command != null) {
			System.out.println("Next command: " + command.getClass());
			Command newCommand = command.execute(previousCommand);
			previousCommand = command;
			command = newCommand;
		}
	}
}