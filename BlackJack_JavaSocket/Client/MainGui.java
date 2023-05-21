package BlackJack_JavaSocket.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui extends JFrame{
    private Client client;


    private int x_card_player = 100;
    private int y_card_player = 400;

    private int x_card_dealer = 100;
    private int y_card_dealer = 100;

    Color colorBackground = new Color(255, 255, 255);
    Color colorButton = new Color(250,162,27);
    Font fontButton = new Font("Mukta Vaani",Font.PLAIN,25);
    JButton bHit = new JButton();
    JButton bStand = new JButton();
    JButton bYes = new JButton();
    JButton bNo = new JButton();
    JButton bStart = new JButton();

    JLabel label = new JLabel();
    JLabel ob1 = new JLabel();
    JLabel ob2 = new JLabel();
    JLabel ob5 = new JLabel("BLACK JACK");

    JLabel label_deal = new JLabel();

    public static void main(String[] args){

    }

    public MainGui(Client client){
        this.client = client;

        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 255, 255));

        //ต้องเเก้เป็นที่เก็บเเต่ละเครื่อง
//        String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\LOGO.png";
        String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\LOGO.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

        label.setIcon(resizedImageIcon);
        label.setBounds(500, 200, 200, 200);
        getContentPane().add(label);


        Font fontButton = new Font("Arial", Font.BOLD, 20);
        ob5.setFont(fontButton);
        ob5.setBounds(500, 400, 200, 50);
        getContentPane().add(ob5);

        buttonStart();
    }
    public void GameGui(){
        getContentPane().setBackground(new Color(9, 239, 30));
        builtHitButton();
        builtStandButton();
        buildDeck();


    }
    public void builtHitButton(){
        bHit.setBounds(1100, 250, 100, 50);
        bHit.setBackground(colorButton);
        bHit.setFont(fontButton);
        bHit.setText("Hit");
        bHit.addActionListener(e -> {
            client.sendToServer("hit");
        });
        this.add(bHit);

    }
    public void builtStandButton(){
        bStand.setBounds(1100, 400, 100, 50);
        bStand.setBackground(colorButton);
        bStand.setFont(fontButton);
        bStand.setText("Stand");
        bStand.addActionListener(e -> {
            client.sendToServer("stand");
            remove(label_deal);
            x_card_dealer-=210;
        });
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
    public void updateScorePlayer(Integer score){
        remove(ob1);
        ob1 = new JLabel();
        ob1.setText("You : "+score);
        ob1.setBounds(75, 850,100, 25);
        ob1.setFont(fontButton);
        this.add(ob1);


    }
    public void updateScoreDealer(Integer score){
        remove(ob2);
        ob2 = new JLabel();
        ob2.setText("Dealer : "+score);
        ob2.setBounds(75, 50, 200, 25);
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
    public void buildDeck(){
        //String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
        String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        JLabel label1 = new JLabel();
        label1.setIcon(imageIcon);
        label1.setBounds(70, 250, 200, 400);
        getContentPane().add(label1);
    }

    public void updateCardPlayer(Integer numcard) {
        String namecard = String.valueOf(numcard+1);
        //String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\" + namecard + ".png";
        String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\" + namecard + ".png";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        label.setBounds(x_card_player, y_card_player, 200, 400);
        x_card_player += 210;
        getContentPane().add(label);


    }
    public void updateCardDealer(Integer numcard){
        String namecard = String.valueOf(numcard+1);
        if(numcard==-1){
            namecard = "Back-red";
        }
        //String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\" + namecard + ".png";
        String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\"+namecard+".png";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        label_deal = new JLabel();
        label_deal.setIcon(imageIcon);
        label_deal.setBounds(x_card_dealer, y_card_dealer, 200, 400);
        x_card_dealer+=210;
        getContentPane().add(label_deal);

    }
    public void buttonStart(){
        bStart.setBounds(565, 550, 150, 50);
        bStart.setBackground(colorButton);
        bStart.setOpaque(false);
        bStart.setFont(fontButton);
        bStart.setText("Start Game");
        add(bStart);

        bStart.addActionListener(e -> {
            client.sendToServer("Y");

            label.setVisible(false);
            ob5.setVisible(false);
            bStart.setVisible(false);
            GameGui();
        });
        bStart.setBounds(500, 500, 200, 50);
        getContentPane().add(bStart);
    }

    public void updateGui(){
        revalidate();
        repaint();
    }




}
