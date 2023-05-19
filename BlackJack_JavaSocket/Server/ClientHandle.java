package BlackJack_JavaSocket.Server;




import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ClientHandle implements Runnable{

    private final Socket clientSocket;

    public ClientHandle(Socket clientSocket){
        this.clientSocket = clientSocket;

    }

    @Override
    public void run() {
        MDataGame dataGame = new MDataGame();
        try{
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output,true);

            String message;

            while((message=reader.readLine())!=null) {
                switch (message) {
                    case "Y", "y" -> {
                        dataGame = new MDataGame();
                        setUpNewGame(dataGame);
                        writer.println("1" + responseToClient(dataGame));
                    }
                    case "hit" -> {

                        hitCard(dataGame);
                        ArrayList<ArrayList<Integer>> players = dataGame.getPlayers();
                        int new_card = players.get(1).get(players.get(1).size() - 1);
                        int new_score = players.get(1).get(0);
                        boolean status = dataGame.isStatus_player();
                        System.out.println(new_card);
                        writer.println("2" + new_card + " " + new_score + " "+status);
                    }
                    case "stand" -> {
                        dataGame.setTurn_player(0);
                        botStartProcess(dataGame);
                        writer.println("3" + dataGame.getPlayers());
                    }
                    case "result" -> {
                        if(dataGame.getTurn_player() == 2){
                            summaryResult(dataGame);
                            writer.println("4" + dataGame.getResult());
                        }
                    }
                    default -> writer.println("GG");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setUpNewGame(MDataGame dataGame){
        //clear data
        //set players
        ArrayList<ArrayList<Integer>> players = dataGame.getPlayers();
        for (int i=0;i<2;i++){
            ArrayList<Integer> cards_player = new ArrayList<>();
            cards_player.add(0);
            players.add(cards_player);
        }
        Deck deck = dataGame.getDeck();
        //built card deck
        deck.builtDeck();
        //[9, 1, 48, 31, 7, 0, 37, 40, 30, 25, 26, 2, 43, 6, 13, 19, 23, 50, 39, 33, 28, 46, 36, 8, 29, 42, 4, 16, 14, 17, 15, 51, 11, 32, 12, 47, 3, 5, 10, 24, 20, 49, 22, 45, 38, 34, 27, 21, 44, 41, 18, 35]
        System.out.println(Arrays.toString(deck.getDeck().toArray()));
        //deal card to player
        dealCardToPlayer(dataGame);
        System.out.println(Arrays.toString(players.toArray()));

        dataGame.setDeck(deck);
        dataGame.setPlayers(players);
    }

    public static void dealCardToPlayer(MDataGame dataGame){
        //[9, 1, 48, 31, 7, 0, 37, 40, 30, 25, 26, 2, 43, 6, 13, 19, 23, 50, 39, 33, 28, 46, 36, 8, 29, 42, 4, 16, 14, 17, 15, 51, 11, 32, 12, 47, 3, 5, 10, 24, 20, 49, 22, 45, 38, 34, 27, 21, 44, 41, 18, 35]
        //[[9, 48], [1, 31]]
        Deck deck = dataGame.getDeck();
        ArrayList<ArrayList<Integer>> players = dataGame.getPlayers();

        int index_player = 0;
        for (int i =0;i<4;i++){
            int num_card = deck.dealCard();
            players.get(index_player).add(num_card);
            updateSumScoreCard(dataGame,index_player,num_card);
            index_player++;
            if(index_player==players.size()){
                index_player=0;
            }
        }
        dataGame.setDeck(deck);
        dataGame.setPlayers(players);
    }

    public static void hitCard(MDataGame dataGame) {
        Deck deck = dataGame.getDeck();
        ArrayList<ArrayList<Integer>> players = dataGame.getPlayers();
        int index_player = dataGame.getTurn_player();

        int num_card = deck.dealCard();
        players.get(index_player).add(num_card);
        updateSumScoreCard(dataGame,index_player,num_card);

        dataGame.setDeck(deck);
        dataGame.setPlayers(players);
    }

    public static void botStartProcess(MDataGame dataGame){
        ArrayList<ArrayList<Integer>> players = dataGame.getPlayers();
        boolean status_bot = dataGame.isStatus_bot();
        int turn_player;

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
                hitCard(dataGame);
            }
            else if(bot_score==12){
                double probability = count_card_without10/count_deck;
                double random_num = random.nextDouble();
                if(random_num<=probability){
                    hitCard(dataGame);
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
                if(random_num<=probability){
                    hitCard(dataGame);
                }
                else{
                    turn_player=2;
                    break;
                }
            }
        }
        dataGame.setStatus_bot(status_bot);
        dataGame.setTurn_player(turn_player);
        dataGame.setPlayers(players);
    }

    public static void summaryResult(MDataGame dataGame){
        ArrayList<ArrayList<Integer>> players = dataGame.getPlayers();
        boolean status_bot = dataGame.isStatus_bot();
        String result = dataGame.getResult();

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
        dataGame.setResult(result);
    }
    public static void updateSumScoreCard(MDataGame dataGame,Integer index_player,Integer num_card){
        Deck deck = dataGame.getDeck();
        ArrayList<ArrayList<Integer>> players = dataGame.getPlayers();
        int turn_player = dataGame.getTurn_player();
        System.out.println(turn_player);
        boolean status_player = dataGame.isStatus_player();

        int sum_score = players.get(index_player).get(0);
        int new_value = deck.valueOfCard(num_card);
        int new_score = sum_score+new_value;
        if (new_score>21 && turn_player == 1){
            status_player = false;

        }
        players.get(index_player).set(0,new_score);

        dataGame.setStatus_player(status_player);
        dataGame.setDeck(deck);
        dataGame.setPlayers(players);
    }
    public static ArrayList<ArrayList<Integer>> responseToClient(MDataGame dataGame){
        ArrayList<ArrayList<Integer>> players = dataGame.getPlayers();
        Deck deck = dataGame.getDeck();

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
