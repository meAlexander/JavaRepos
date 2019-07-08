package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientClass {

	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket socket = new Socket("localhost", 1211);
		
		NewClient client = new NewClient(socket);
		
		client.method();
	}
}