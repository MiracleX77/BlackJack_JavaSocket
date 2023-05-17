package BlackJack_JavaSocket.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class Gui extends JFrame{
    int bWidth = 1280;
    int bHeight = 800;
    Color colorBackground = new Color(45,122,38);
    Color colorButton = new Color(250,162,27);
    Font fontButton = new Font("Mukta Vaani",Font.PLAIN,25);

    JButton bHit = new JButton();
    JButton bStand = new JButton();
    JButton bYes = new JButton();
    JButton bNo = new JButton();
    JButton bStart = new JButton();


    public Gui(){
        setSize(bWidth, bHeight);
        setTitle("Blackjack");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(colorBackground);

        setIconImage(Toolkit.getDefaultToolkit().getImage("LOGO.png"));

        pageMain();
        textMain();
        buttonStart();

       //yes();
       //no();
       hit();
       stand();
//       //newGame();
       scorePlayer();
       scoreDealer();
       //scoreResult(); //แค่ you win
       deck();
       setVisible(true);
       
    }
    public void hit(){
        bHit.setBounds(1100, 250, 100, 50);
       bHit.setBackground(colorButton);
       bHit.setFont(fontButton);
       bHit.setText("Hit");
       this.add(bHit);
    }
    public void stand(){
        bStand.setBounds(1100, 400, 100, 50);
       bStand.setBackground(colorButton);
       bStand.setFont(fontButton);
       bStand.setText("Stand");
       this.add(bStand);
    }
    public void yes(){
        bYes.setBounds(1100, 300, 75, 50);
       bYes.setBackground(colorButton);
       bYes.setFont(fontButton);
       bYes.setText("Yes");
       this.add(bYes);
    }
    public void no(){
        bNo.setBounds(1100, 375, 75, 50);
       bNo.setBackground(colorButton);
       bNo.setFont(fontButton);
       bNo.setText("No");
       this.add(bNo);
    }
    public void newGame(){
        JLabel ob0 = new JLabel("Play Again ?");
        ob0.setBounds(1075, 250,200, 27);
        ob0.setFont(fontButton);
        this.add(ob0);
    }
    public void scorePlayer(){
        JLabel ob1 = new JLabel("You : ");
        ob1.setBounds(75, 700,100, 18);
        ob1.setFont(fontButton);
        this.add(ob1);
    }
    public void scoreDealer(){
        JLabel ob2 = new JLabel("Dealer : ");
        ob2.setBounds(75, 50, 100, 18);
        ob2.setFont(fontButton);
        this.add(ob2);
    }
    public void scoreResult(){
        if (true){
        JLabel ob4 = new JLabel("You Win");
        ob4.setBounds(1095, 450, 100, 18);
        ob4.setFont(fontButton);
        this.add(ob4);
        } else if (false) {
        JLabel ob4 = new JLabel("You Lost");  
        ob4.setBounds(1095, 450, 100, 18);
        ob4.setFont(fontButton);
        this.add(ob4);
        }
    }
    public void deck(){
        String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        add(label);
    }
    public void pageMain(){
        String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\LOGO.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel label = new JLabel();
        label.setIcon(imageIcon); 
        add(label);
    }
    public void textMain(){
        JLabel ob5 = new JLabel("BLACK JACK");
        ob5.setBounds(580, 450, 400, 50);
        ob5.setFont(fontButton);
        add(ob5);
    }
    public void buttonStart(){
        bStart.setBounds(565, 550, 150, 50);
       bStart.setBackground(colorButton);
       bStart.setOpaque(false);
       bStart.setFont(fontButton);
       bStart.setText("Start Game");
       add(bStart);
    }
}
