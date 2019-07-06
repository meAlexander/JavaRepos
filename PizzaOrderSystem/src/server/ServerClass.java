package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass {

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(1211);
		while (true) {

			Socket socket = server.accept();

			MyThread thread = new MyThread(socket);

			thread.start();
		}
	}
}