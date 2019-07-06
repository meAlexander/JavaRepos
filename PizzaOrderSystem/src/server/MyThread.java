package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.NotValidDataException;
import exceptions.UserExistsException;

public class MyThread extends Thread {

	protected Socket socket;
	protected String address = "jdbc:mysql://localhost:3306/pizza_order";
	protected static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^*&+=]).{8,}";
	protected static final String EMAIL_PATTERN = "^[a-z]+[A-Za-z0-9_.-]{4,}+@[a-z]{2,6}+\\.[a-z]{2,5}";
	Pattern pattern1;
	Matcher matcher1;

	public MyThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			Connection connection = DriverManager.getConnection(address, "root", "123456");
			PrintStream printout = new PrintStream(this.socket.getOutputStream());
			BufferedReader buffreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PreparedStatement ps = null;
			ResultSet rs = null;

			menu(connection, printout, ps, rs, buffreader);

			buffreader.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NotValidDataException e) {
			System.out.println(e.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (UserExistsException e) {
			System.out.println(e.getMessage());
		}
	}

	public void menu(Connection connection, PrintStream printout, PreparedStatement ps, ResultSet rs,
			BufferedReader buffreader) throws IOException, SQLException, NotValidDataException, UserExistsException {
		printout.println("Choose option: 1.Log in 2.Registration 3.View products");
		String option = buffreader.readLine();

		if (option.equals("Log in")) {
			optionLogIn(connection, printout, ps, rs, buffreader);
		} else if (option.equals("Registration")) {
			optionRegistration(connection, printout, ps, rs, buffreader);
		} else if (option.equals("View products")) {
			getAllProducts(connection, printout, rs);
		}
		printout.println("Successfull operation");
	}

	public void optionRegistration(Connection connection, PrintStream printout, PreparedStatement ps, ResultSet rs,
			BufferedReader buffreader) throws SQLException, IOException, NotValidDataException, UserExistsException {

		String option;
		String username;
		String password;
		String email;

		printout.println("Enter first username and then password and email: ");
		username = buffreader.readLine();
		password = buffreader.readLine();
		email = buffreader.readLine();

		printout.println("Registration option: 1.Admin 2.User");
		option = buffreader.readLine();
		
		if (option.equals("Admin")) {
			if (!(checkAdminName(username, connection, rs)) && !(checkAdminPass(password, connection, rs))
					&& validateEmailAdmin(email, connection, rs)) {
				printout.println("Successfull registration");
			} else {
				throw new UserExistsException();
			}
		} else if (option.equals("User")) {
			if (!(checkUserName(username, connection, rs)) && !(checkUserPass(password, connection, rs))
					&& validateEmailUser(email, connection, rs)) {
				printout.println("Successfull registration");
			} else {
				throw new UserExistsException();
			}
		}
		
		menu(connection, printout, ps, rs, buffreader);
	}

	public void optionLogIn(Connection connection, PrintStream printout, PreparedStatement ps, ResultSet rs,
			BufferedReader buffreader) throws SQLException, IOException, NotValidDataException, UserExistsException {

		String option;
		String username;
		String password;

		printout.println("Enter first username and then password: ");
		username = buffreader.readLine();
		password = buffreader.readLine();

		printout.println("Choose option: 1.User 2.Admin");
		option = buffreader.readLine();

		if (option.equals("Admin")) {
			if (checkAdminName(username, connection, rs) && checkAdminPass(password, connection, rs)) {
				printout.println("Choose option: 1.Add product 2.Delete product 3.Update product");
				option = buffreader.readLine();

				if (option.equals("Add product")) {
					addNewProduct(connection, printout, ps, buffreader);
				} else if (option.equals("Delete product")) {
					deleteProduct(connection, printout, ps, buffreader);
				} else if (option.equals("Update product")) {

				} else {
					throw new NotValidDataException();
				}
			}
		} else if (option.equals("User")) {
			if (checkUserName(username, connection, rs) && checkUserPass(password, connection, rs)) {
				printout.println("Successfull log in. Two options: 1.Buy product 2.View products");
				option = buffreader.readLine();
				if (option.equals("View products")) {
					getAllProducts(connection, printout, rs);
				} else if (option.equals("Buy products")) {

				}
			} else {
				throw new NotValidDataException();
			}
		}
	}

	public boolean checkUserName(String user, Connection connection, ResultSet rs) throws SQLException {

		rs = connection.prepareStatement("SELECT username" + " FROM users").executeQuery();

		while (rs.next()) {
			if (user.equals(rs.getString("username"))) {
				return true;
			}
		}

		return false;
	}

	public boolean checkUserPass(String pass, Connection connection, ResultSet rs) throws SQLException {

		rs = connection.prepareStatement("SELECT password" + " FROM users").executeQuery();

		while (rs.next()) {
			if (pass.equals(rs.getString("password"))) {
				return true;
			}
		}
		return false;
	}

	public boolean validateEmailUser(String email, Connection connection, ResultSet rs) throws SQLException {

		pattern1 = Pattern.compile(EMAIL_PATTERN);
		matcher1 = pattern1.matcher(email);

		rs = connection.prepareStatement("SELECT email" + " FROM users").executeQuery();
		while (rs.next()) {
			if (!(email.equals(rs.getString("email"))) && matcher1.matches()) {
				return true;
			}
		}

		return false;
	}

	public boolean checkAdminName(String admin, Connection connection, ResultSet rs) throws SQLException {

		rs = connection.prepareStatement("SELECT adminName" + " FROM admins").executeQuery();

		while (rs.next()) {
			if (admin.equals(rs.getString("adminName"))) {
				return true;
			}
		}

		return false;
	}

	public boolean checkAdminPass(String pass, Connection connection, ResultSet rs) throws SQLException {

		rs = connection.prepareStatement("SELECT password" + " FROM admins").executeQuery();

		while (rs.next()) {
			if (pass.equals(rs.getString("password"))) {
				return true;
			}
		}
		return false;
	}

	public boolean validateEmailAdmin(String email, Connection connection, ResultSet rs) throws SQLException {

		pattern1 = Pattern.compile(EMAIL_PATTERN);
		matcher1 = pattern1.matcher(email);

		rs = connection.prepareStatement("SELECT email" + " FROM admins").executeQuery();
		while (rs.next()) {
			if (!(email.equals(rs.getString("email"))) && matcher1.matches()) {
				return true;
			}
		}

		return false;
	}

	public boolean validatePass(String pass, Connection connection, ResultSet rs) throws SQLException {
		pattern1 = Pattern.compile(PASSWORD_PATTERN);
		matcher1 = pattern1.matcher(pass);

		return matcher1.matches();
	}

	public void getAllProducts(Connection connection, PrintStream printout, ResultSet rs) throws SQLException {

		rs = connection.prepareStatement("SELECT *" + " FROM pizzas").executeQuery();

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
			printout.println(String.format("Drink type: %s, Brand: %s, Quantity: %d, Price: %.2f",
					rs.getString("drink_type"), rs.getString("brand"), rs.getInt("quantity"), rs.getDouble("price")));
		}
	}

	public void addNewProduct(Connection connection, PrintStream printout, PreparedStatement ps, BufferedReader bf)
			throws SQLException, IOException {
		printout.println("What product want to add to menu: 1.Pizza 2.Salad 3.Drink");
		String option = bf.readLine();

		if (option.equals("Pizza")) {
			printout.println("Enter pizza name: ");
			String pizzaName = bf.readLine();

			printout.println("Enter size: ");
			String size = bf.readLine();

			printout.println("Enter ingredients: ");
			String ingredients = bf.readLine();

			printout.println("Enter pizza price: ");
			double price = Double.parseDouble(bf.readLine());

			ps = connection.prepareStatement(
					"INSERT INTO pizzas(pizza_name, size, ingredients, price)" + "VALUES(?, ?, ?, ?)");
			ps.setString(1, pizzaName);
			ps.setString(2, size);
			ps.setString(3, ingredients);
			ps.setDouble(4, price);

		} else if (option.equals("Salad")) {
			printout.println("Enter salad name: ");
			String saladName = bf.readLine();

			printout.println("Enter ingredients: ");
			String ingredients = bf.readLine();

			printout.println("Enter salad price: ");
			double price = Double.parseDouble(bf.readLine());

			ps = connection.prepareStatement("INSERT INTO salads(salad_name, ingredients, price)" + "VALUES(?, ?, ?)");
			ps.setString(1, saladName);
			ps.setString(2, ingredients);
			ps.setDouble(3, price);
		} else if (option.equals("Drink")) {
			printout.println("Enter drink type: ");
			String drinkName = bf.readLine();

			printout.println("Enter brand: ");
			String brand = bf.readLine();

			printout.println("Enter quantity: ");
			int quantity = Integer.parseInt(bf.readLine());

			printout.println("Enter drink price: ");
			double price = Double.parseDouble(bf.readLine());

			ps = connection
					.prepareStatement("INSERT INTO drinks(drink_type, brand, quantity, price)" + "VALUES(?, ?, ?, ?)");
			ps.setString(1, drinkName);
			ps.setString(2, brand);
			ps.setInt(3, quantity);
			ps.setDouble(4, price);
		} else {
			printout.println("No such product");
		}
		ps.execute();
	}

	public void deleteProduct(Connection connection, PrintStream printout, PreparedStatement ps, BufferedReader bf)
			throws SQLException, IOException {
		printout.println("What product want to delete from menu: 1.Pizza 2.Salad 3.Drink");
		String option = bf.readLine();

		if (option.equals("Pizza")) {
			printout.println("Enter pizza name you want to delete: ");
			String pizzaName = bf.readLine();

			ps = connection.prepareStatement("DELETE FROM pizzas WHERE pizza_name = ?");
			ps.setString(1, pizzaName);

		} else if (option.equals("Salad")) {
			printout.println("Enter salad name you want to delete: ");
			String saladName = bf.readLine();

			ps = connection.prepareStatement("DELETE FROM salads WHERE salad_name = ?");
			ps.setString(1, saladName);

		} else if (option.equals("Drink")) {
			printout.println("Enter drink type you want to delete: ");
			String drinkName = bf.readLine();

			ps = connection.prepareStatement("DELETE FROM drinks WHERE drink_type = ?");
			ps.setString(1, drinkName);
		} else {
			printout.println("No such product");
		}
		ps.execute();
	}
}