package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MyThread extends Thread {

	protected Socket socket;
	protected String address = "jdbc:mysql://localhost:3306/pizza_order";

	public MyThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			Connection connection = DriverManager.getConnection(address, "root", "123456");
			PrintStream printout = new PrintStream(this.socket.getOutputStream());
			PreparedStatement ps = null;
			Scanner scan2 = new Scanner(socket.getInputStream());

			printout.println("Choose option: 1.Log in 2.Registration 3.View products");
			String option = scan2.nextLine();

			if (option.equals("Log in")) {
				// printout.println("Choose option: 1.User 2.Admin");

				printout.println("Choose option: 1.Add product 2.Delete product 3.Update product");
				option = scan2.nextLine();
				if (option.equals("Add product")) {
					addNewProduct(connection, printout, ps, scan2);
				} else if (option.equals("Delete product")) {
					deleteProduct(connection, printout, ps, scan2);
				} else if (option.equals("Update product")) {

				}
			} else if (option.equals("Registration")) {

			} else if (option.equals("View products")) {
				getAllProducts(connection, printout);
			}
			scan2.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void getAllProducts(Connection connection, PrintStream printout) throws SQLException {

		ResultSet rs = connection.prepareStatement("SELECT *" + "FROM pizzas").executeQuery();

		while (rs.next()) {
			printout.println(
					String.format("Pizza name: %s, Size: %s, Ingredients: %s, Price: %.2f", rs.getString("pizza_name"),
							rs.getString("size"), rs.getString("ingredients"), rs.getDouble("price")));
		}

		rs = connection.prepareStatement("SELECT *" + "FROM salads").executeQuery();

		while (rs.next()) {
			printout.println(String.format("Salad name: %s, Ingredients: %s, Price: %.2f", rs.getString("salad_name"),
					rs.getString("ingredients"), rs.getDouble("price")));
		}

		rs = connection.prepareStatement("SELECT *" + "FROM drinks").executeQuery();

		while (rs.next()) {
			printout.println(String.format("Drink name: %s, Brand: %s, Quantity: %d, Price: %.2f",
					rs.getString("drink_name"), rs.getString("brand"), rs.getInt("quantity"), rs.getDouble("price")));
		}
	}

	public void addNewProduct(Connection connection, PrintStream printout, PreparedStatement ps, Scanner scan2)
			throws SQLException {
		printout.println("What product want to add to menu: 1.Pizza 2.Salad 3.Drink");
		String option = scan2.nextLine();

		if (option.equals("Pizza")) {
			printout.println("Enter pizza name: ");
			String pizzaName = scan2.nextLine();

			printout.println("Enter size: ");
			String size = scan2.nextLine();

			printout.println("Enter ingredients: ");
			String ingredients = scan2.nextLine();

			printout.println("Enter pizza price: ");
			double price = scan2.nextDouble();

			ps = connection.prepareStatement(
					"INSERT INTO pizzas(pizza_name, size, ingredients, price)" + "VALUES(?, ?, ?, ?)");
			ps.setString(1, pizzaName);
			ps.setString(2, size);
			ps.setString(3, ingredients);
			ps.setDouble(4, price);

		} else if (option.equals("Salad")) {
			printout.println("Enter salad name: ");
			String saladName = scan2.nextLine();

			printout.println("Enter ingredients: ");
			String ingredients = scan2.nextLine();

			printout.println("Enter salad price: ");
			double price = scan2.nextDouble();
			
			ps = connection.prepareStatement("INSERT INTO salads(salad_name, ingredients, price)" + "VALUES(?, ?, ?)");
			ps.setString(1, saladName);
			ps.setString(2, ingredients);
			ps.setDouble(3, price);
		} else if (option.equals("Drink")) {
			printout.println("Enter drink name: ");
			String drinkName = scan2.nextLine();

			printout.println("Enter brand: ");
			String brand = scan2.nextLine();

			printout.println("Enter quantity: ");
			int quantity = scan2.nextInt();

			printout.println("Enter drink price: ");
			double price = scan2.nextDouble();

			ps = connection
					.prepareStatement("INSERT INTO drinks(drink_name, brand, quantity, price)" + "VALUES(?, ?, ?, ?)");
			ps.setString(1, drinkName);
			ps.setString(2, brand);
			ps.setInt(3, quantity);
			ps.setDouble(4, price);
		} else {
			printout.println("No such product");
		}
		ps.execute();
	}

	public void deleteProduct(Connection connection, PrintStream printout, PreparedStatement ps, Scanner scan2)
			throws SQLException {
		printout.println("What product want to delete from menu: 1.Pizza 2.Salad 3.Drink");
		String option = scan2.nextLine();

		if (option.equals("Pizza")) {
			printout.println("Enter pizza name you want to delete: ");
			String pizzaName = scan2.nextLine();

			ps = connection.prepareStatement("DELETE FROM pizzas WHERE pizza_name = ?");
			ps.setString(1, pizzaName);

		} else if (option.equals("Salad")) {
			printout.println("Enter salad name you want to delete: ");
			String saladName = scan2.nextLine();

			ps = connection.prepareStatement("DELETE FROM salads WHERE salad_name = ?");
			ps.setString(1, saladName);

		} else if (option.equals("Drink")) {
			printout.println("Enter drink name you want to delete: ");
			String drinkName = scan2.nextLine();

			ps = connection.prepareStatement("DELETE FROM drinks WHERE drink_name = ?");
			ps.setString(1, drinkName);
		} else {
			printout.println("No such product");
		}
		ps.execute();
	}
}
