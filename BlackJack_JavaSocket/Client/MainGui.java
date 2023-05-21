package BlackJack_JavaSocket.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainGui extends JFrame{
    private Client client;

    //private final String full_path ="C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\";
    private final String full_path ="C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\";
    //private final String full_path ="/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/";
    private int x_card_player = 100;

    private int x_card_dealer = 100;

    Font fontButton = new Font("Arial", Font.PLAIN ,25);

    JButton bHit = new JButton();
    JButton bStand = new JButton();
    JButton bAgain = new JButton();
    JButton bExit = new JButton();
    JButton bStart = new JButton();

    JLabel label = new JLabel();
    JLabel ob1 = new JLabel();
    JLabel ob2 = new JLabel();
    JLabel ob5 = new JLabel("BLACK JACK");

    JLabel label_deal = new JLabel();
    JLabel label2 = new JLabel(); //win
    JLabel label3 = new JLabel(); //lost
    JLabel label4 = new JLabel(); //draw

    public static void main(String[] args){

    }

    public MainGui(Client client){
        this.client = client;

        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 255, 255));

        builtLOGO();
        builtText();
        buttonStart();
    }

    public void GameGui(){
        getContentPane().setBackground(new Color(13, 117, 13));
        builtHitButton();
        builtStandButton();
    }
    public void builtLOGO(){
        String imagePath = full_path+"LOGO.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

        label.setIcon(resizedImageIcon);
        label.setBounds(550, 200, 200, 200);
        getContentPane().add(label);
    }
    public void builtText(){
        Font fontButton = new Font("Arial", Font.BOLD, 20);
        ob5.setFont(fontButton);
        ob5.setBounds(585, 400, 200, 50);
        getContentPane().add(ob5);
    }
    public void builtHitButton(){
        bHit.setBounds(1100, 250, 100, 50);
        bHit.setFont(fontButton);
        bHit.setText("Hit");
        bHit.addActionListener(e -> {
            Client.sendToServer("hit");
        });
        this.add(bHit);

    }
    public void builtAgainButton(){
        remove(bHit);
        remove(bStand);
        bAgain.setBounds(1015, 375, 150, 50);
        bAgain.setFont(fontButton);
        bAgain.setText("Again");
        bAgain.addActionListener(e ->{
            dispose();
            client.genGui();
            updateGui();
        });
        getContentPane().add(bAgain);
    }
    public void builtExitButton(){
        bExit.setBounds(1015, 450, 150, 50);
        bExit.setFont(fontButton);
        bExit.setText("Exit");
        bExit.addActionListener(e ->{
            dispose(); // ลบเฟรม
        });
        getContentPane().add(bExit);
    }
    public void builtStandButton(){
        bStand.setBounds(1100, 400, 100, 50);
        bStand.setFont(fontButton);
        bStand.setText("Stand");
        bStand.addActionListener(e -> {
            Client.sendToServer("stand");
            remove(label_deal);
            x_card_dealer-=210;

        });
        this.add(bStand);
    } 
    public void updateScorePlayer(Integer score){
        remove(ob1);
        ob1 = new JLabel();
        ob1.setText("You : "+score);
        ob1.setBounds(75, 700,100, 25);
        ob1.setFont(fontButton);
        this.add(ob1);
    }
    public void youWin(){
        String imagePath = full_path+"win.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        label2.setIcon(imageIcon);
        label2.setBounds(950, 150, 800, 500);
        builtAgainButton();
        builtExitButton();
        getContentPane().add(label2);
    }
    public void youLost(){
        String imagePath = full_path+"lost.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        label3.setIcon(imageIcon);
        label3.setBounds(950, 150, 800, 500);
        builtAgainButton();
        builtExitButton();
        getContentPane().add(label3);
    }
    public void youDraw(){
        String imagePath = full_path+"draw.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        label4.setIcon(imageIcon);
        label4.setBounds(950, 150, 800, 500);
        builtAgainButton();
        builtExitButton();
        getContentPane().add(label4);
    }
    public void updateScoreDealer(Integer score){
        remove(ob2);
        ob2 = new JLabel();
        ob2.setText("Dealer : "+score);
        ob2.setBounds(75, 50, 200, 25);
        ob2.setFont(fontButton);
        this.add(ob2);

    }
    

    public void updateCardPlayer(Integer numcard) {
        String namecard = String.valueOf(numcard+1);
        String imagePath = full_path + namecard + ".png";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        int y_card_player = 340;
        label.setBounds(x_card_player, y_card_player, 200, 400);
        x_card_player += 210;

        getContentPane().add(label);


    }
    public void updateCardDealer(Integer numcard){
        String namecard = String.valueOf(numcard+1);
        if(numcard==-1){
            namecard = "Back-red";
        }
        String imagePath = full_path + namecard + ".png";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        label_deal = new JLabel();
        label_deal.setIcon(imageIcon);
        int y_card_dealer = 30;
        label_deal.setBounds(x_card_dealer, y_card_dealer, 200, 400);
        x_card_dealer+=210;
        getContentPane().add(label_deal);

    }
    public void buttonStart(){
        bStart.setOpaque(false);
        bStart.setFont(fontButton);
        bStart.setText("Start Game");
        add(bStart);

        bStart.addActionListener(e -> {
            Client.sendToServer("Y");
            label.setVisible(false);
            ob5.setVisible(false);
            bStart.setVisible(false);
            GameGui();
        });
        bStart.setBounds(550, 500, 200, 50);
        getContentPane().add(bStart);
    }

    public void updateGui(){
        revalidate();
        repaint();
    }
    public void resetGui(){
        getContentPane().removeAll();
        updateGui();
    }




}
