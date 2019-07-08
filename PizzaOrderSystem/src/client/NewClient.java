package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class NewClient {

	protected Socket socket;
	protected String option;

	public NewClient(Socket socket) {
		this.socket = socket;
	}

	public void method() throws IOException {
		Scanner scan1 = new Scanner(System.in);
		Scanner scan2 = new Scanner(socket.getInputStream());
		PrintStream printout = new PrintStream(socket.getOutputStream());

		System.out.println(scan2.nextLine());
		String option = scan1.nextLine();
		printout.println(option);

		if (option.equals("Log in")) {
			logIn(printout, scan1, scan2);
		} else if (option.equals("Registration")) {
			registration(printout, scan1, scan2);
		} else if (option.equals("View products")) {
			while (scan2.hasNext()) {
				System.out.println(scan2.nextLine() + "\n");
			}
		}

		socket.close();
		scan1.close();
		scan2.close();
	}

	public void registration(PrintStream printout, Scanner scan1, Scanner scan2) {
		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());
		printout.println(scan1.nextLine());
		printout.println(scan1.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
	}

	public void logIn(PrintStream printout, Scanner scan1, Scanner scan2) {
		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		option = scan1.nextLine();
		printout.println(option);
		if (option.equals("Admin")) {
			logInAdmin(printout, scan1, scan2);
		} else if (option.equals("User")) {
			logInUser(printout, scan1, scan2);
		}
	}

	public void logInUser(PrintStream printout, Scanner scan1, Scanner scan2) {
		System.out.println(scan2.nextLine());
		option = scan1.nextLine();
		printout.println(option);
		if (option.equals("View products")) {
			while (scan2.hasNext()) {
				System.out.println(scan2.nextLine());
			}
		} else if (option.equals("Buy product")) {
//		while (scan2.hasNext()) {
//			System.out.println(scan2.nextLine());
//		}
			System.out.println(scan2.nextLine());
			option = scan1.nextLine();
			printout.println(option);
			
			if (option.equals("Pizza")) {
				System.out.println(scan2.nextLine());
				printout.println(scan1.nextLine());
				printout.println(scan1.nextLine());

			} else if (option.equals("Salad")) {
				System.out.println(scan2.nextLine());
				printout.println(scan1.nextLine());

			} else if (option.equals("Drinks")) {

				System.out.println(scan2.nextLine());
				printout.println(scan1.nextLine());
				printout.println(scan1.nextLine());
			}
		}

		System.out.println(scan2.nextLine());
	}

	public void logInAdmin(PrintStream printout, Scanner scan1, Scanner scan2) {

		System.out.println(scan2.nextLine());
		option = scan1.nextLine();
		printout.println(option);
		if (option.equals("Add product")) {
			addProduct(printout, scan1, scan2);
		} else if (option.equals("Delete product")) {
			deleteProduct(printout, scan1, scan2);
		} else if (option.equals("Update product")) {

		}
		System.out.println(scan2.nextLine());
	}

	public void addProduct(PrintStream printout, Scanner scan1, Scanner scan2) {

		System.out.println(scan2.nextLine());
		option = scan1.nextLine();
		printout.println(option);

		if (option.equals("Pizza")) {
			addProductPizza(printout, scan1, scan2);
		} else if (option.equals("Salad")) {
			addProductSalad(printout, scan1, scan2);
		} else if (option.equals("Drink")) {
			addProductDrink(printout, scan1, scan2);
		}
	}
	

	private void addProductPizza(PrintStream printout, Scanner scan1, Scanner scan2) {
		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());		
	}

	private void addProductSalad(PrintStream printout, Scanner scan1, Scanner scan2) {
		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());		
	}
	
	private void addProductDrink(PrintStream printout, Scanner scan1, Scanner scan2) {
		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());

		System.out.println(scan2.nextLine());
		printout.println(scan1.nextLine());		
	}


	public void deleteProduct(PrintStream printout, Scanner scan1, Scanner scan2) {

		System.out.println(scan2.nextLine());
		option = scan1.nextLine();
		printout.println(option);

		if (option.equals("Pizza")) {
			System.out.println(scan2.nextLine());
			printout.println(scan1.nextLine());
		} else if (option.equals("Salad")) {
			System.out.println(scan2.nextLine());
			printout.println(scan1.nextLine());
		} else if (option.equals("Drink")) {
			System.out.println(scan2.nextLine());
			printout.println(scan1.nextLine());
			printout.println(scan1.nextLine());
		}
	}
}