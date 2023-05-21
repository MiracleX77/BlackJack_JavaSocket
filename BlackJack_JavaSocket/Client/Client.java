package BlackJack_JavaSocket.Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private static final int PORT_NUMBER= 7600;

    private static Socket socket;
    private static BufferedReader reader;

    private static PrintWriter writer;
    private static  Scanner scanner;

    private static  MainGui mainGui;


    private static ArrayList<ArrayList<Integer>> players = new ArrayList<>();

    public  void connect(String serIp,int serPort) throws IOException{
        socket = new Socket(serIp,serPort);
        System.out.println("Connected");
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output,true);
        scanner = new Scanner(System.in);
    }

    public MainGui genGui(){
            MainGui mainFrame = new MainGui(this);
            mainFrame.setVisible(true);
        return  mainFrame;
    }

    public static void sendToServer(String message){
        writer.println(message);
    }
    public static String recFromServer() throws IOException {
        return reader.readLine();
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect("localhost",PORT_NUMBER);
        MainGui mainGui = client.genGui();



        String message;
        while(true){
            players.clear();
            System.out.println("New game(Y/N): ");
            message= recFromServer();
            System.out.println(message);
            if(!Objects.equals(message,"N")) {
                while (true) {
//                    writer.println(message);
//                    message= reader.readLine();
                    if(message.charAt(0) == '1'){
                        System.out.println(message);
                        convertStringToArrayList(message);
                        mainGui.updateCardDealer(players.get(0).get(1));
                        mainGui.updateCardDealer(-1);
                        for(int i =1;i<players.get(1).size();i++){
                            mainGui.updateCardPlayer(players.get(1).get(i));
                        }
                        mainGui.updateScorePlayer(players.get(1).get(0));
                        mainGui.updateScoreDealer(players.get(0).get(0));

                        mainGui.updateGui();

                        System.out.println("*---------------------------------------------*");
                        System.out.println("Player 1 Card -> : " + players.get(0).get(1) + " ** ");
                        System.out.println("Player 1 Count Score -> : " + players.get(0).get(0));
                        System.out.println("*---------------------------------------------*");
                        System.out.print("Player 2 Card -> : ");
                        for(int i =1;i<players.get(1).size();i++){
                            System.out.print(players.get(1).get(i)+" ");
                        }
                        System.out.println("\nPlayer 2 Count Score -> : " + players.get(1).get(0));
                        System.out.println("*---------------------------------------------*");
                    }
                    else  if(message.charAt(0) == '2'){
                        System.out.println(message);
                        String messages = message.substring(1);
                        String[] str = messages.split(" ");
                        int hit_card = Integer.parseInt(str[0]);
                        int new_score = Integer.parseInt(str[1]);
                        boolean status_player = Boolean.parseBoolean(str[2]);

                        mainGui.updateCardPlayer(hit_card);
                        mainGui.updateScorePlayer(new_score);

                        mainGui.updateGui();

                        players.get(1).set(0,new_score);
                        players.get(1).add(hit_card);
                        System.out.println("*---------------------------------------------*");
                        System.out.println("Player 1 Card -> : " + players.get(0).get(1) + " ** ");
                        System.out.println("Player 1 Count Score -> : " + players.get(0).get(0));
                        System.out.println("*---------------------------------------------*");
                        System.out.print("Player 2 Card -> : ");
                        for(int i =1;i<players.get(1).size();i++){
                            System.out.print(players.get(1).get(i)+" ");
                        }
                        System.out.println("\nPlayer 2 Count Score -> : " + players.get(1).get(0));
                        System.out.println("Player 2 Status -> "+status_player);
                        System.out.println("*---------------------------------------------*");
                        if(!status_player){
                            System.out.println("-------------YOU LOSS!!---------");
                            break;
                        }
                    }
                    else  if(message.charAt(0) == '3'){
                        convertStringToArrayList(message);
                        for(int i =2;i<players.get(0).size();i++){
                            mainGui.updateCardDealer(players.get(0).get(i));
                        }
                        mainGui.updateScoreDealer(players.get(0).get(0));
                        mainGui.updateGui();

                        sendToServer("result");


                        System.out.println(message);
                        System.out.println("*---------------------------------------------*");
                        System.out.print("Player 1 Card -> : ");
                        for(int i =1;i<players.get(0).size();i++){
                            System.out.print(players.get(0).get(i)+" ");
                        }
                        System.out.println("\nPlayer 1 Count Score -> : " + players.get(0).get(0));
                        System.out.println("*---------------------------------------------*");
                        System.out.print("Player 2 Card -> : ");
                        for(int i =1;i<players.get(1).size();i++){
                            System.out.print(players.get(1).get(i)+" ");
                        }
                        System.out.println("\nPlayer 2 Count Score -> : " + players.get(1).get(0));
                        System.out.println("*---------------------------------------------*");
                    }
                    else  if(message.charAt(0) == '4'){
                        System.out.println("------------RESULT-----------------");
                        System.out.println("------------"+message.substring(1)+"-----------------");
                        System.out.println("*---------------------------------------------*");
                    }


                    else if(message.equals("GG")){
                        System.out.println(message);
                        break;
                    }
                    else {
                        System.out.println(message);
                    }
                    message = recFromServer();
                }
            }
            else {
                break;
            }

        }
        socket.close();

    }

    public static void convertStringToArrayList(String message){
        players.clear();
        //1[[35, 48], [43, 40]]
        String messages = message.substring(3,message.length()-2);
        //  35, 48], [43, 40
        String[] str = messages.split("], \\[");
        //  35, 48 and 43, 40
        for (String s : str){
            ArrayList<Integer> subList = new ArrayList<>();
            // 35 and 48
            String[] cards = s.split(", ");
            for(String card: cards){
                //convert string to int and add to subList
                subList.add(Integer.parseInt(card));
            }
            players.add(subList);
            //[[35, 48]]
        }
    }

}
