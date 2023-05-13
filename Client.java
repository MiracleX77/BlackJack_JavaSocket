import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private static final int PORT_NUMBER= 7600;

    private static ArrayList<ArrayList<Integer>> players = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";

        Socket socket = new Socket(hostName,PORT_NUMBER);
        System.out.println("Connected");

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output,true);

        Scanner scanner = new Scanner(System.in);

        String message;
        while(true){
            players.clear();
            System.out.println("New game(Y/N): ");
            message= scanner.nextLine();

            if(!Objects.equals(message,"N")) {
                while (true) {
                    writer.println(message);
                    message= reader.readLine();
                    if(message.charAt(0) == '1'){
                        System.out.println(message);
                        convertStringToArrayList(message);

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
                        String messages = message.substring(1);
                        String[] str = messages.split(" ");
                        int hit_card = Integer.parseInt(str[0]);
                        int new_score = Integer.parseInt(str[1]);
                        boolean status_player = Boolean.parseBoolean(str[2]);
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
                        System.out.println(message.substring(1));
                        System.out.println("Turn -> Player 1 ");
                    }


                    else if(message.equals("GG")){
                        System.out.println(message);
                        break;
                    }
                    else {
                        System.out.println(message);
                    }
                    message= scanner.nextLine();
                }
            }
            else {
                break;
            }

        }
        socket.close();

    }

    public static void convertStringToArrayList(String message){
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
