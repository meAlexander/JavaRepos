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

import exceptions.AddUserExceptions;
import exceptions.AddProductException;
import exceptions.BuyProductException;
import exceptions.DeleteProductException;
import exceptions.RegistrationException;
import exceptions.PurchaseException;
import exceptions.LoginException;

public class MyThread extends Thread {

	protected Socket socket;
	protected String address = "jdbc:mysql://localhost:3306/pizza_order";
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;
	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^*&+=]).{8,}";
	private static final String EMAIL_PATTERN = "^[a-z]+[A-Za-z0-9_.-]{4,}+@[a-z]{2,6}+\\.[a-z]{2,5}";
	protected Pattern pattern1;
	protected Matcher matcher1;

	public MyThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			Connection connection = DriverManager.getConnection(address, "root", "123456");
			PrintStream printout = new PrintStream(this.socket.getOutputStream());
			BufferedReader buffreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			menu(connection, printout, buffreader);

			buffreader.close();

		} catch (SQLException sql) {
			sql.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (LoginException wde) {
			System.out.println(wde.getMessage());
		} catch (RegistrationException re) {
			System.out.println(re.getMessage());
		} catch (BuyProductException bpe) {
			System.out.println(bpe.getMessage());
		} catch (PurchaseException p) {
			System.out.println(p.getMessage());
		} catch (AddProductException anpe) {
			System.out.println(anpe.getMessage());
		} catch (DeleteProductException dpe) {
			System.out.println(dpe.getMessage());
		} catch (AddUserExceptions anue) {
			System.out.println(anue.getMessage());
		}
	}

	public void menu(Connection connection, PrintStream printout, BufferedReader buffreader) throws IOException,
			SQLException, RegistrationException, LoginException, BuyProductException, PurchaseException,
			AddProductException, DeleteProductException, AddUserExceptions {
		printout.println("Choose option: 1.Log in 2.Registration 3.View products");
		String option = buffreader.readLine();

		if (option.equals("Log in")) {
			optionLogIn(connection, printout, buffreader);
		} else if (option.equals("Registration")) {
			optionRegistration(connection, printout, buffreader);
		} else if (option.equals("View products")) {
			getAllProducts(connection, printout);
		}
		printout.println("Successfull operation");
	}

	public void optionLogIn(Connection connection, PrintStream printout, BufferedReader buffreader)
			throws SQLException, IOException, LoginException, BuyProductException, PurchaseException, AddProductException, DeleteProductException {

		String option, user, pass;

		printout.println("Enter first username and then password: ");
		user = buffreader.readLine();
		pass = buffreader.readLine();

		printout.println("Log in options: 1.User 2.Admin");
		option = buffreader.readLine();

		if (option.equals("Admin")) {
			if (checkAdminInfo(connection, pass, user)) {
				printout.println("Choose option: 1.Add product 2.Delete product 3.Update product");
				option = buffreader.readLine();

				if (option.equals("Add product")) {
					addNewProduct(connection, printout, buffreader);
				} else if (option.equals("Delete product")) {
					deleteProduct(connection, printout, buffreader);
				} else if (option.equals("Update product")) {

				}
			} else {
				throw new LoginException();
			}
		} else if (option.equals("User")) {
			if (checkUserInfo(connection, pass, user)) {
				printout.println("Successfull log in. Two options: 1.Buy product 2.View products");
				option = buffreader.readLine();
				if (option.equals("View products")) {
					getAllProducts(connection, printout);
				} else if (option.equals("Buy product")) {
					// getAllProducts(connection, printout);
					buyProduct(connection, printout, buffreader, user);
				}
			} else {
				throw new LoginException();
			}
		}

		printout.println("Successfull operation!");
	}

	public void optionRegistration(Connection connection, PrintStream printout, BufferedReader buffreader)
			throws SQLException, IOException, RegistrationException, AddUserExceptions {

		String option, user, pass, phone, email;

		printout.println("Enter first username then password, phone and email: ");
		user = buffreader.readLine();
		pass = buffreader.readLine();
		phone = buffreader.readLine();
		email = buffreader.readLine();

		printout.println("Registration options: 1.User 2.Admin");
		option = buffreader.readLine();

		if (option.equals("Admin")) {
			if (!(checkAdminName(connection, user)) && validatePass(pass) && !(validateEmailAdmin(connection, email))) {

				registerAdmin(connection, user, pass, phone, email);
			} else {
				throw new RegistrationException();
			}
		} else if (option.equals("User")) {
			if (!(checkUserName(connection, user)) && validatePass(pass) && !(validateEmailUser(connection, email))) {

				registerUser(connection, user, pass, phone, email);
			} else {
				throw new RegistrationException();
			}
		}
		if(!ps.execute()) {
			throw new AddUserExceptions();
		}
		printout.println("Successfull registration!");
	}

	public void registerUser(Connection connection, String username, String password, String phone, String email)
			throws SQLException {

		ps = connection.prepareStatement("INSERT INTO users(username, password, phone, email)" + "VALUES(?, ?, ?, ?)");
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setString(3, phone);
		ps.setString(4, email);
	}

	public void registerAdmin(Connection connection, String adminName, String password, String phone, String email)
			throws SQLException {

		ps = connection
				.prepareStatement("INSERT INTO admins(adminName, password, phone, email)" + "VALUES(?, ?, ?, ?)");
		ps.setString(1, adminName);
		ps.setString(2, password);
		ps.setString(3, phone);
		ps.setString(4, email);
	}

	public boolean checkUserName(Connection connection, String user) throws SQLException {

		rs = connection.prepareStatement(String.format("SELECT username" + " FROM users WHERE username = '%s'", user))
				.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public boolean checkUserInfo(Connection connection, String pass, String user) throws SQLException {

		rs = connection.prepareStatement(String.format(
				"SELECT username, password" + " FROM users WHERE username = '%s' AND password = '%s'", user, pass))
				.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public boolean checkAdminName(Connection connection, String admin) throws SQLException {

		rs = connection
				.prepareStatement(String.format("SELECT adminName" + " FROM admins WHERE adminName = '%s'", admin))
				.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public boolean checkAdminInfo(Connection connection, String pass, String admin) throws SQLException {

		rs = connection.prepareStatement(String.format(
				"SELECT adminName, password" + " FROM admins WHERE adminName = '%s' AND password = '%s'", admin, pass))
				.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public boolean validateEmailUser(Connection connection, String email) throws SQLException {

		pattern1 = Pattern.compile(EMAIL_PATTERN);
		matcher1 = pattern1.matcher(email);

		rs = connection.prepareStatement(String.format("SELECT email" + " FROM users WHERE email = '%s'", email))
				.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public boolean validateEmailAdmin(Connection connection, String email) throws SQLException {

		pattern1 = Pattern.compile(EMAIL_PATTERN);
		matcher1 = pattern1.matcher(email);

		rs = connection.prepareStatement(String.format("SELECT email" + " FROM admins WHERE email = '%s'", email))
				.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public boolean validatePass(String pass) throws SQLException {
		pattern1 = Pattern.compile(PASSWORD_PATTERN);
		matcher1 = pattern1.matcher(pass);

		return matcher1.matches();
	}

	public void getAllProducts(Connection connection, PrintStream printout) throws SQLException {

		getPizzas(connection, printout);
		getSalads(connection, printout);
		getDrinks(connection, printout);
		
	}
	
	public void getPizzas(Connection connection, PrintStream printout) throws SQLException {
		rs = connection.prepareStatement("SELECT *" + " FROM pizzas").executeQuery();

		while (rs.next()) {
			printout.println(
					String.format("Pizza name: %s, Size: %s, Ingredients: %s, Price: %.2f", rs.getString("pizza_name"),
							rs.getString("size"), rs.getString("ingredients"), rs.getDouble("price")));
		}
	}
	
	public void getSalads(Connection connection, PrintStream printout) throws SQLException {
		rs = connection.prepareStatement("SELECT *" + "FROM salads").executeQuery();

		while (rs.next()) {
			printout.println(String.format("Salad name: %s, Ingredients: %s, Price: %.2f", rs.getString("salad_name"),
					rs.getString("ingredients"), rs.getDouble("price")));
		}
	}
	
	public void getDrinks(Connection connection, PrintStream printout) throws SQLException {
		rs = connection.prepareStatement("SELECT *" + "FROM drinks").executeQuery();

		while (rs.next()) {
			printout.println(String.format("Drink type: %s, Brand: %s, Quantity: %d, Price: %.2f",
					rs.getString("drink_type"), rs.getString("brand"), rs.getInt("quantity"), rs.getDouble("price")));
		}
	}

	public void addNewProduct(Connection connection, PrintStream printout, BufferedReader bf)
			throws SQLException, IOException, BuyProductException, AddProductException {
		printout.println("What product want to add to menu: 1.Pizza 2.Salad 3.Drink");
		String option = bf.readLine();

		if (option.equals("Pizza")) {
			addPizza(connection, printout, bf);
		} else if (option.equals("Salad")) {
			addSalad(connection, printout, bf);
		} else if (option.equals("Drink")) {
			addDrink(connection, printout, bf);
		} else {
			throw new BuyProductException();
		}
		
		if(!ps.execute()) {
			throw new AddProductException();
		};
	}

	public void addPizza(Connection connection, PrintStream printout, BufferedReader bf)
			throws SQLException, IOException {

		String pizzaName, size, ingredients;
		double price;

		printout.println("Enter pizza name: ");
		pizzaName = bf.readLine();

		printout.println("Enter size: ");
		size = bf.readLine();

		printout.println("Enter ingredients: ");
		ingredients = bf.readLine();

		printout.println("Enter pizza price: ");
		price = Double.parseDouble(bf.readLine());

		ps = connection
				.prepareStatement("INSERT INTO pizzas(pizza_name, size, ingredients, price)" + "VALUES(?, ?, ?, ?)");
		ps.setString(1, pizzaName);
		ps.setString(2, size);
		ps.setString(3, ingredients);
		ps.setDouble(4, price);
	}

	public void addSalad(Connection connection, PrintStream printout, BufferedReader bf)
			throws SQLException, IOException {

		String saladName, ingredients;
		double price;

		printout.println("Enter salad name: ");
		saladName = bf.readLine();

		printout.println("Enter ingredients: ");
		ingredients = bf.readLine();

		printout.println("Enter salad price: ");
		price = Double.parseDouble(bf.readLine());

		ps = connection.prepareStatement("INSERT INTO salads(salad_name, ingredients, price)" + "VALUES(?, ?, ?)");
		ps.setString(1, saladName);
		ps.setString(2, ingredients);
		ps.setDouble(3, price);
	}

	public void addDrink(Connection connection, PrintStream printout, BufferedReader bf)
			throws SQLException, NumberFormatException, IOException {

		String drinkType, brand;
		int quantity;
		double price;

		printout.println("Enter drink type: ");
		drinkType = bf.readLine();

		printout.println("Enter brand: ");
		brand = bf.readLine();

		printout.println("Enter quantity: ");
		quantity = Integer.parseInt(bf.readLine());

		printout.println("Enter drink price: ");
		price = Double.parseDouble(bf.readLine());

		ps = connection
				.prepareStatement("INSERT INTO drinks(drink_type, brand, quantity, price)" + "VALUES(?, ?, ?, ?)");
		ps.setString(1, drinkType);
		ps.setString(2, brand);
		ps.setInt(3, quantity);
		ps.setDouble(4, price);
	}

	public void deleteProduct(Connection connection, PrintStream printout, BufferedReader bf)
			throws SQLException, IOException, DeleteProductException {
		printout.println("What product want to delete from menu: 1.Pizza 2.Salad 3.Drink");
		String option = bf.readLine();

		if (option.equals("Pizza")) {
			deletePizza(connection, printout, bf);
		} else if (option.equals("Salad")) {
			deleteSalad(connection, printout, bf);
		} else if (option.equals("Drink")) {
			deleteDrink(connection, printout, bf);
		} else {
			printout.println("No such product");
		}
		
		if(!ps.execute()) {
			throw new DeleteProductException();
		};
	}

	public void deletePizza(Connection connection, PrintStream printout, BufferedReader bf)
			throws SQLException, IOException {

		printout.println("Enter pizza name you want to delete: ");
		String pizzaName = bf.readLine();

		ps = connection.prepareStatement("DELETE FROM pizzas WHERE pizza_name = ?");
		ps.setString(1, pizzaName);
	}

	public void deleteSalad(Connection connection, PrintStream printout, BufferedReader bf)
			throws SQLException, IOException {

		printout.println("Enter salad name you want to delete: ");
		String saladName = bf.readLine();

		ps = connection.prepareStatement("DELETE FROM salads WHERE salad_name = ?");
		ps.setString(1, saladName);
	}

	public void deleteDrink(Connection connection, PrintStream printout, BufferedReader bf)
			throws SQLException, IOException {

		printout.println("Enter drink type and brand you want to delete: ");
		String drinkType = bf.readLine();
		String brand = bf.readLine();

		ps = connection.prepareStatement("DELETE FROM drinks WHERE drink_type = ? AND brand = ?");
		ps.setString(1, drinkType);
		ps.setString(2, brand);
	}

	public int getUserId(Connection connection, String username) throws SQLException {
		rs = connection.prepareStatement(String.format("SELECT id" + " FROM users WHERE username = '%s'", username))
				.executeQuery();

		rs.next();
		return rs.getInt("id");
	}

	public void buyProduct(Connection connection, PrintStream printout, BufferedReader bf, String username)
			throws SQLException, IOException, BuyProductException, PurchaseException {

		printout.println("What product want to buy: 1.Pizza 2.Salad 3.Drink");
		String option = bf.readLine();

		int userId = getUserId(connection, username);

		if (option.equals("Pizza")) {
			buyPizza(connection, printout, bf, userId);
		} else if (option.equals("Salad")) {
			buySalad(connection, printout, bf, userId);
		} else if (option.equals("Drink")) {
			buyDrink(connection, printout, bf, userId);
		} else {
			throw new BuyProductException();
		}
		
		if(!ps.execute()) {
			throw new PurchaseException();
		}
		printout.println("Your order/s is/are accepted! Delivery time: 25-30 min :)");
	}

	public void buyPizza(Connection connection, PrintStream printout, BufferedReader bf, int userId)
			throws SQLException, IOException, BuyProductException {

		printout.println("Enter pizza name and size you want to buy: ");
		String name = bf.readLine();
		String size = bf.readLine();

		rs = connection
				.prepareStatement(
						String.format("SELECT id" + " FROM pizzas WHERE pizza_name = '%s' AND size = '%s'", name, size))
				.executeQuery();

		if (!rs.next()) {
			throw new BuyProductException();
		}
		acceptPizzaOrder(connection, rs.getInt("id"), userId);
	}

	public void buySalad(Connection connection, PrintStream printout, BufferedReader bf, int userId)
			throws SQLException, IOException, BuyProductException {

		printout.println("Enter salad name you want to buy: ");
		String name = bf.readLine();

		rs = connection.prepareStatement(String.format("SELECT id" + " FROM salads WHERE salad_name = '%s'", name))
				.executeQuery();

		if (!rs.next()) {
			throw new BuyProductException();
		}
		acceptSaladOrder(connection, rs.getInt("id"), userId);
	}

	public void buyDrink(Connection connection, PrintStream printout, BufferedReader bf, int userId)
			throws SQLException, IOException, BuyProductException {

		printout.println("Enter drink type and brand you want to buy: ");
		String type = bf.readLine();
		String brand = bf.readLine();

		rs = connection.prepareStatement(
				String.format("SELECT id" + " FROM drinks WHERE drink_type = '%s' AND brand = '%s'", type, brand))
				.executeQuery();

		if (!rs.next()) {
			throw new BuyProductException();
		}
		acceptDrinkOrder(connection, rs.getInt("id"), userId);
	}

	public void acceptPizzaOrder(Connection connection, int pizza_id, int userId) throws SQLException {

		ps = connection.prepareStatement(
				"INSERT INTO orders(pizza_id, salad_id, drink_id, user_id)" + " VALUES(?, NULL, NULL, ?)");
		ps.setInt(1, pizza_id);
		ps.setInt(2, userId);
	}

	public void acceptSaladOrder(Connection connection, int salad_id, int userId) throws SQLException {

		ps = connection.prepareStatement(
				"INSERT INTO orders(pizza_id, salad_id, drink_id, user_id)" + " VALUES(NULL, ?, NULL, ?)");
		ps.setInt(1, salad_id);
		ps.setInt(2, userId);
	}

	public void acceptDrinkOrder(Connection connection, int drink_id, int userId) throws SQLException {

		ps = connection.prepareStatement(
				"INSERT INTO orders(pizza_id, salad_id, drink_id, user_id)" + " VALUES(NULL, NULL, ?, ?)");
		ps.setInt(1, drink_id);
		ps.setInt(2, userId);
	}
}