package BlackJack_JavaSocket.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT_NUMBER = 7600;

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server started.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected");

                Thread thread = new Thread(new ClientHandle(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
