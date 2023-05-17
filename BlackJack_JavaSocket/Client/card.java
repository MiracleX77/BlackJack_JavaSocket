package BlackJack_JavaSocket.Client;


import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class card {
    
    ImageIcon image = new ImageIcon();
    Random rand = new Random();
    int randomNumber = rand.nextInt(52) + 1;

    public card(){
        //JLabel MyImage = new JLabel(new ImageIcon(.png"));
    }
}
