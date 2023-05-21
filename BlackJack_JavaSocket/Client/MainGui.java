package BlackJack_JavaSocket.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui extends JFrame{
    private Client client;


    private int x_card_player = 100;
    private int y_card_player = 340;

    private int x_card_dealer = 100;
    private int y_card_dealer = 30;

    Color colorBackground = new Color(255, 255, 255);
    Font fontButton = new Font("Arial", Font.PLAIN ,25);
    Font fontText = new Font("Arial", Font.PLAIN ,40);
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

        //ต้องเเก้เป็นที่เก็บเเต่ละเครื่อง
        //String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\20.png";
//        String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\1.png";
        String imagePath = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/LOGO.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

        label.setIcon(resizedImageIcon);
        label.setBounds(550, 200, 200, 200);
        getContentPane().add(label);


        Font fontButton = new Font("Arial", Font.BOLD, 20);
        ob5.setFont(fontButton);
        ob5.setBounds(585, 400, 200, 50);
        getContentPane().add(ob5);

        buttonStart();
    }
    public void GameGui(){
        getContentPane().setBackground(new Color(13, 117, 13));
        builtHitButton();
        builtStandButton();
        //buildDeck();


    }
    public void builtHitButton(){
        bHit.setBounds(1100, 250, 100, 50);
        bHit.setFont(fontButton);
        bHit.setText("Hit");
        bHit.addActionListener(e -> {
            client.sendToServer("hit");
        });
        this.add(bHit);

    }
    public void builtAgainButton(){
        bAgain.setBounds(1015, 375, 150, 50);
        bAgain.setFont(fontButton);
        bAgain.setText("Again");
        bAgain.addActionListener(e ->{
        label.setVisible(true);
        ob5.setVisible(true);
        bStart.setVisible(true);
        remove(bAgain);//เริ่มเกมใหม่
        updateGui();

        });
    }
    public void builtStandButton(){
        bStand.setBounds(1100, 400, 100, 50);
        bStand.setFont(fontButton);
        bStand.setText("Stand");
        bStand.addActionListener(e -> {
            client.sendToServer("stand");
            remove(label_deal);
            x_card_dealer-=210;

            //again
            bAgain.setBounds(1015, 375, 150, 50);
            bAgain.setFont(fontButton);
            bAgain.setText("Again");
            //bAgain.addActionListener(e2 ->{
                //
            //});
            //button exit
            bExit.setBounds(1015, 450, 150, 50);
            bExit.setFont(fontButton);
            bExit.setText("Exit");
            //bExit.addActionListener(e -> {
            //    //กลับไปหน้าแรก หรือไม่ก็เด้งปิด
            //});
    
            //youWin
            String imagePath = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/win.png";
                //String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
            ImageIcon imageIcon = new ImageIcon(imagePath);
            label2.setIcon(imageIcon);
            label2.setBounds(950, 150, 800, 500);
            
            //youLost
            String imagePath1 = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/lost.png";
                    //String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
            ImageIcon imageIcon1 = new ImageIcon(imagePath);
            label3.setIcon(imageIcon1);
            label3.setBounds(950, 150, 800, 500);
            
            //youDraw
            String imagePath2 = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/draw.png";
                    //String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
            ImageIcon imageIcon2 = new ImageIcon(imagePath);
            label4.setIcon(imageIcon2);
            label4.setBounds(950, 150, 800, 500);
            
            //add(ob0); //play again
            remove(bHit);
            remove(bStand);
            add(bExit);
            add(bAgain);
            getContentPane().add(label2);

            //if (scoredealer > 22 ){
                //getContentPane().add(label2);
            //} else if (scoreplayer > 22 || scoredealer > scoreplayer){
                //getContentPane().add(label3);
            //} else if (scoredealer < scoreplayer){
                //getContentPane().add(label2);
            //} else {
                //getContentPane().add(label4);
            //}
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
        String imagePath = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/win.png";
            //String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        label2.setIcon(imageIcon);
        label2.setBounds(950, 150, 800, 500);
    }
    public void youLost(){
        String imagePath = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/Lost.png";
            //String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        label3.setIcon(imageIcon);
        label3.setBounds(950, 150, 800, 500);
    }
    public void youDraw(){
        String imagePath = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/draw.png";
            //String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        label4.setIcon(imageIcon);
        label4.setBounds(950, 150, 800, 500);
    }
    public void updateScoreDealer(Integer score){
        remove(ob2);
        ob2 = new JLabel();
        ob2.setText("Dealer : "+score);
        ob2.setBounds(75, 50, 200, 25);
        ob2.setFont(fontButton);
        this.add(ob2);

    }
    
    public void buildDeck(){
        String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
        //String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\back-red.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        JLabel label1 = new JLabel();
        label1.setIcon(imageIcon);
        label1.setBounds(70, 250, 200, 400);
        getContentPane().add(label1);
    }

    public void updateCardPlayer(Integer numcard) {
        String namecard = String.valueOf(numcard+1);
        String imagePath = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/" + namecard + ".png";
        //String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\" + namecard + ".png";
//        String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\" + namecard + ".png";
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
        String imagePath = "/Users/maitokairin/Documents/GitHub/BlackJack_JavaSocket/BlackJack_JavaSocket/Client/numcard/" + namecard + ".png";
        //String imagePath = "C:\\Users\\mrbig\\Documents\\BlackJack\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\" + namecard + ".png";
        //String imagePath = "C:\\Users\\miracle\\Documents\\GitHub\\BlackJack_JavaSocket\\BlackJack_JavaSocket\\Client\\numcard\\"+namecard+".png";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        label_deal = new JLabel();
        label_deal.setIcon(imageIcon);
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
            client.sendToServer("Y");

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




}
