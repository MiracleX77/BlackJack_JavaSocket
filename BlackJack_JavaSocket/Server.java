package BlackJack_JavaSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Server {
    private static final int PORT_NUMBER = 7600;

    public static ArrayList<ArrayList<Integer>> players = new ArrayList<>();

    public static Deck deck = new Deck();

    public static boolean  status_player = true;
    public static boolean  status_bot = true;

    public static String result = "";

    public static int turn_player = 1;


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("Server started.");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connected");

        InputStream input = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output,true);

        String message;

        while((message=reader.readLine())!=null) {
            switch (message) {
                case "Y", "y" -> {
                    setUpNewGame();
                    writer.println("1" + responseToClient());
                }
                case "hit" -> {
                    hitCard();
                    int new_card = players.get(1).get(players.get(1).size() - 1);
                    int new_score = players.get(1).get(0);
                    boolean status = status_player;
                    writer.println("2" + new_card + " " + new_score + " "+status);
                }
                case "stand" -> {
                    turn_player = 0;
                    botStartProcess();
                    writer.println("3" + players);
                }
                case "result" -> {
                    if(turn_player == 2){
                        summaryResult();
                        writer.println("4" + result);
                    }
                }
                default -> writer.println("GG");
            }
        }
    }

    public static void setUpNewGame(){
        //clear data
        players.clear();
        deck.clearDeck();
        status_player = true;
        status_bot = true;
        turn_player = 1;
        result = "";
        //set players

        for (int i=0;i<2;i++){
            ArrayList<Integer> cards_player = new ArrayList<>();
            cards_player.add(0);
            players.add(cards_player);
        }
        //built card deck
        deck.builtDeck();
        //[9, 1, 48, 31, 7, 0, 37, 40, 30, 25, 26, 2, 43, 6, 13, 19, 23, 50, 39, 33, 28, 46, 36, 8, 29, 42, 4, 16, 14, 17, 15, 51, 11, 32, 12, 47, 3, 5, 10, 24, 20, 49, 22, 45, 38, 34, 27, 21, 44, 41, 18, 35]
        System.out.println(Arrays.toString(deck.getDeck().toArray()));
        //deal card to player
        dealCardToPlayer();
        System.out.println(Arrays.toString(players.toArray()));

    }

    public static void dealCardToPlayer(){
        //[9, 1, 48, 31, 7, 0, 37, 40, 30, 25, 26, 2, 43, 6, 13, 19, 23, 50, 39, 33, 28, 46, 36, 8, 29, 42, 4, 16, 14, 17, 15, 51, 11, 32, 12, 47, 3, 5, 10, 24, 20, 49, 22, 45, 38, 34, 27, 21, 44, 41, 18, 35]
        //[[9, 48], [1, 31]]
        int index_player = 0;
        for (int i =0;i<4;i++){
            int num_card = deck.dealCard();
            players.get(index_player).add(num_card);
            updateSumScoreCard(index_player,num_card);
            index_player++;
            if(index_player==players.size()){
                index_player=0;
            }
        }
    }

    public static void hitCard(){
        int index_player = turn_player;
        int num_card = deck.dealCard();
        players.get(index_player).add(num_card);
        updateSumScoreCard(index_player,num_card);
    }

    public static void botStartProcess(){
        Random random = new Random();
        double count_deck = 52.0;
        double count_card_without10 = count_deck - 16.0;
        while(true){

            double bot_score = players.get(0).get(0);
            System.out.println(bot_score);
            // เป็นการคิดว่าตัดสินใจของ bot โดยพื้นฐาน โดยอ้างอิง ความน่าจะเป็นที่จะ hit เเล้วจะรอด กับการ random ในความน่าจะเป็นอีกที
            // ความน่าจะเป็นนี้ไม่นับจาก กาดใน deck ที่เหลือ เเต่จะนับจาก กาดทั้งหมด
            // กรณี bot มี 12 คะเเนน ถ้าจะ hit ต้องได้น้อยกว่า 10 หรือก็คือ มี 36 ใบ ที่จะรอด ความน่าเป็นคือ 0.692
            // ดังนั้นจะเอา 0.75 มาใช้การ random ว่าจะอยู่ใน 0.75 นี้ไหม
            // ถ้าอยุ่ จะให้ hit เเต่ถ้าไม่ ก็ stand
            if (bot_score>=21){
                status_bot = false;
                turn_player=2;
                break;
            }
            else if(bot_score<=11){
                hitCard();
            }
            else if(bot_score==12){
                double probability = count_card_without10/count_deck;
                double random_num = random.nextDouble();
                System.out.println(probability);
                System.out.println(random_num);
                if(random_num<=probability){
                    hitCard();
                }
                else{
                    System.out.println("stand");
                    turn_player=2;
                    break;
                }
            }
            else{
                //Ex:1 score 15.0            Ex:2 score 16.0
                //0.46153846153846156        0.38461538461538464
                //0.7088605668929612         0.2088499502615465
                //stand                      hit 26.0

                //Ex:3 score 12.0
                //0.6923076923076923
                //0.46438069615727107
                //15.0
                //0.46153846153846156
                //0.5384492058625275
                //stand

                double probability =  (count_card_without10 - (4.0 * ( bot_score - 12.0))) /  count_deck;
                double random_num = random.nextDouble();
                System.out.println(probability);
                System.out.println(random_num);
                if(random_num<=probability){
                    hitCard();
                }
                else{
                    System.out.println("stand");
                    turn_player=2;
                    break;
                }
            }


        }



    }

    public static void summaryResult(){
        int score_bot = players.get(0).get(0);
        int score_player = players.get(1).get(0);
        if (score_player<score_bot && status_bot) {
            result = "lose";
        }
        else if (score_player == score_bot){
            result = "draw";
        }
        else{
            result = "win";
        }
    }
    public static void updateSumScoreCard(Integer index_player,Integer num_card){
        int sum_score = players.get(index_player).get(0);
        int new_value = deck.valueOfCard(num_card);
        int new_score = sum_score+new_value;
        if (new_score>21 && turn_player == 1){
            status_player = false;
        }
        players.get(index_player).set(0,new_score);
    }


    public static ArrayList<ArrayList<Integer>> responseToClient(){
        ArrayList<ArrayList<Integer>> response = new ArrayList<>();
        //copy arraylist
        for (ArrayList<Integer> sublist : players) {
            ArrayList<Integer> subResponse = new ArrayList<>(sublist);
            response.add(subResponse);
        }

        for (int i =0;i<response.get(0).size();i++){
            if(i==0){
                response.get(0).set(i,deck.valueOfCard(response.get(0).get(1)));
            } else if (i>=2) {
                response.get(0).remove(2);
            }
        }
        return response;
    }



}
