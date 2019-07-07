package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientClass {

	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket socket = new Socket("localhost", 1211);

		Scanner scan1 = new Scanner(System.in);
		Scanner scan2 = new Scanner(socket.getInputStream());
		PrintStream printout = new PrintStream(socket.getOutputStream());

		System.out.println(scan2.nextLine());
		String option = scan1.nextLine();
		printout.println(option);

		if (option.equals("Log in")) {
			System.out.println(scan2.nextLine());
			printout.println(scan1.nextLine());
			printout.println(scan1.nextLine());

			System.out.println(scan2.nextLine());
			option = scan1.nextLine();
			printout.println(option);
			if (option.equals("Admin")) {
				System.out.println(scan2.nextLine());
				option = scan1.nextLine();
				printout.println(option);
				if (option.equals("Add product")) {
					System.out.println(scan2.nextLine());
					option = scan1.nextLine();
					printout.println(option);
					if (option.equals("Pizza")) {
						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());

						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());

						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());

						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());
					} else if (option.equals("Salad")) {
						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());

						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());

						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());
					} else if (option.equals("Drink")) {
						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());

						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());

						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());

						System.out.println(scan2.nextLine());
						printout.println(scan1.nextLine());
					}
				} else if (option.equals("Delete product")) {
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

					}
				} else if (option.equals("Update product")) {

				}
				System.out.println(scan2.nextLine());
			} else if (option.equals("User")) {
				System.out.println(scan2.nextLine());
				option = scan1.nextLine();
				printout.println(option);
				if (option.equals("View products")) {
					while (scan2.hasNext()) {
						System.out.println(scan2.nextLine() + "\n");
					}
				}else if(option.equals("Buy product")) {
					
				}
			}
		} else if (option.equals("Registration")) {
			System.out.println(scan2.nextLine());
			printout.println(scan1.nextLine());
			printout.println(scan1.nextLine());
			printout.println(scan1.nextLine());
			printout.println(scan1.nextLine());

			System.out.println(scan2.nextLine());
			printout.println(scan1.nextLine());

			System.out.println(scan2.nextLine());
		} else if (option.equals("View products")) {
			while (scan2.hasNext()) {
				System.out.println(scan2.nextLine() + "\n");
			}
		}
		
		socket.close();
		scan1.close();
		scan2.close();
	}
}