package BlackJack_JavaSocket.Server;
import java.util.ArrayList;

public class MDataGame {
    private ArrayList<ArrayList<Integer>> players = new ArrayList<>();
    private Deck deck = new Deck();
    private boolean  status_player = true;
    private boolean  status_bot = true;
    private String result = "";
    private  int turn_player = 1;

    public ArrayList<ArrayList<Integer>> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<ArrayList<Integer>> players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public boolean isStatus_player() {
        return status_player;
    }

    public void setStatus_player(boolean status_player) {
        this.status_player = status_player;
    }

    public boolean isStatus_bot() {
        return status_bot;
    }

    public void setStatus_bot(boolean status_bot) {
        this.status_bot = status_bot;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTurn_player() {
        return turn_player;
    }

    public void setTurn_player(int turn_player) {
        this.turn_player = turn_player;
    }


}
