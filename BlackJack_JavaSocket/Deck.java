package BlackJack_JavaSocket;

import java.util.*;

public class Deck {


    public ArrayList<Integer> deck = new ArrayList<>();

    public ArrayList<Integer> valueOfDeck =new ArrayList<>();

    public void builtDeck(){
        createValueCard();
        Random random = new Random();
        while(this.deck.size()!=52) {
            int num_random = random.nextInt(52);
            if (!this.deck.contains(num_random)) {
                this.deck.add(num_random);
            }
        }


    }
    public Integer dealCard(){
        int card_number = this.deck.get(0);
        this.deck.remove(0);
        return card_number;
    }

    public ArrayList<Integer> getDeck(){
        return this.deck;
    }

    public Integer valueOfCard(Integer num_card){
        return valueOfDeck.get(num_card);
    }

    public void clearDeck(){
        deck.clear();
    }

    public void createValueCard(){
        for(int i =0;i<4;i++){
            for (int j =1;j<11;j++){
                valueOfDeck.add(j);
            }
            valueOfDeck.add(10);
            valueOfDeck.add(10);
            valueOfDeck.add(10);
        }
        //[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,   card 0 - 12
        // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,   card 13 - 25
        // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,   card 26 - 38
        // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10]   card 39 - 51
    }

}
