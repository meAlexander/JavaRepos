package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket socket = new Socket("localhost", 1211);
		
		Scanner consoleScanner = new Scanner(System.in);
		Scanner socketScanner = new Scanner(socket.getInputStream());
		PrintStream socketOut = new PrintStream(socket.getOutputStream());

		while(true) {
			String line = socketScanner.nextLine();
			System.out.println(line);
			
			if(line.equals("Your input please: ")) {
				String userInput = consoleScanner.nextLine();
				socketOut.println(userInput);
				socketOut.flush();
			}
		}
	}
}