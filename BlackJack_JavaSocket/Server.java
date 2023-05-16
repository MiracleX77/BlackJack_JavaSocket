package BlackJack_JavaSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private static final int PORT_NUMBER = 7600;



    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server started.");

            ExecutorService executorService = Executors.newCachedThreadPool();

            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected");

                executorService.execute(new ClientHandle(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
