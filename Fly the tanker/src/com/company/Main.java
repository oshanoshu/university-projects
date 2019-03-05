package com.company;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

/**
 * Created by Aaron on 8/23/2016.
 */
public class Main implements ActionListener, MouseListener{

    public static Main main;
    private static boolean paused=false;
    public final int WIDTH = 800;
    public final int HEIGHT = 600;
    //public final long firsttime=System.currentTimeMillis();
    //public long secondtime=System.currentTimeMillis();
    private ImagePanel background;
    private JFrame jFrame;

    public Renderer renderer;
    public Characters character;
    public final int xA=270;
    public  int yA;
    public static int score, maxScore;



    public ArrayList<Characters> cloud;
    public Random rand;

    public boolean start = false, gameover = false, firstTime;


    public int tick;

    public Main() {

        Timer timer = new Timer(20, this);


            load(new File ("BestScore.txt"));
            jFrame = new JFrame();


            rand = new Random();

            jFrame.setTitle("Filip-Flop");
            //jFrame.add(renderer);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            background = new ImagePanel("game.jpg");
            jFrame.add(background);
            jFrame.setSize(WIDTH, HEIGHT);
            jFrame.setResizable(false);
            jFrame.addMouseListener(this);
            jFrame.setVisible(true);
            score=0;

            character = new Characters("pallu.png", xA, 270);
            cloud = new ArrayList<Characters>();


            addCloud(true);

            timer.start();






    }

    public void load(File file)
    {
        try
        {
            Scanner reader = new Scanner(file);
            while(reader.hasNext())
            {
                int value = reader.nextInt();
                if(value > maxScore)
                    maxScore = value;
            }
        }
        catch(IOException i )
        {
            System.out.println("Error. "+i);
        }
    }

    public void save()
    {
        FileWriter out;
        try
        {
            out = new FileWriter("BestScore.txt");
            out.write("" + maxScore);
            out.close();
        }
        catch(IOException i)
        {
            System.out.println("Error: "+i.getMessage());
        }
    }


    public void repaint(Graphics g) {
        //g.setColor(Color.black);
        //g.fillRect(0,0, WIDTH, HEIGHT);

        //g.setColor(Color.blue);
        //g.fillRect(0, HEIGHT - 100, WIDTH, 100);
        if (character != null)
            g.drawImage(character.getImage(), (int) character.getX(), (int) character.getY(), null);

        // g.setColor(Color.green);
        //g.fillRect(character.x, character.y, character.width, character.height);

        if (character.getY() >= HEIGHT || character.getY() < 0) {
            gameover = true;
        }

        for (Characters chars : cloud) {

            g.drawImage(chars.getImage(), (int) chars.getX(), (int) chars.getY(), null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 1, 100));
        if (!start) {
            g.drawString("Press to start!", 75, HEIGHT / 2);
        } else if (gameover) {
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("Game Over!", 200, HEIGHT / 2);
            g.drawString("Click to start again", 140, HEIGHT / 3);
        }
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 30, 500);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        if (score > maxScore)
            g.drawString("Best: " + score,650,500);
        else
            g.drawString("Best: " + maxScore,650,500);
        if(Main.paused)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Chiller",Font.BOLD,72));
            g.drawString("Paused",325,290);
            g.setFont(new Font("Chiller",Font.BOLD,30));
            g.drawString("Click to unpause.",320,340);
        }
    }

    public void addCloud(boolean start) {

        if (start) {
            cloud.add(new Characters("1234.png",WIDTH+cloud.size()*300,rand.nextInt(HEIGHT-76)));
        }
        else {
            cloud.add(new Characters("1234.png",cloud.get(cloud.size() - 1).getX() + 300, rand.nextInt(HEIGHT-120)));
        }
    }

    public void flap() {



        if(gameover) {
            score=0;
            character=new Characters("pallu.png",200,270);
            cloud.clear();

            addCloud(true);
            addCloud(true);
            addCloud(true);
            addCloud(true);
            addCloud(true);
            addCloud(true);
            addCloud(true);
            addCloud(true);
            gameover = false;
        }


        if (!start) {
            start = true;

        }
        else if (!gameover) {

            character.setPosition(xA,character.getY() - 50);
            tick = 0;


            
        }



        }




    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        //System.out.println("Space");

        if (start) {
            for (int i = 0; i < cloud.size(); i++) {
                Characters chars = cloud.get(i);
                chars.setPosition(cloud.get(i).getX()-speed, cloud.get(i).getY());
            }

            for (int i = 0; i < cloud.size(); i++) {
                Characters chars = cloud.get(i);
                if (cloud.get(i).getX() < 0) {
                    cloud.remove(cloud);

                    addCloud(false);

                }

            }

            for (int i=0;i<cloud.size();i++) {
                if (crash()) {
                    if(maxScore<score)
                    {
                        maxScore=score;
                        save();
                    }
                    gameover = true;
                    character.setPosition(character.getX()-speed,character.getY());
                }
            }


            tick ++;
            if(!gameover) {
                score++;    //the score of the player

            }

            if (tick % 2 == 0 && character.getY() < HEIGHT)
                    character.setPosition(xA, character.getY() + tick);


            if (gameover && character.getY() >= HEIGHT) {
                character.setPosition(character.getX()-speed,character.getY());
            }
        }

        background.repaint();
    }

    public static void main(String args[]) {
        main = new Main();
    }



    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

           flap();



    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {




    }

    @Override
    public void mouseExited(MouseEvent e) {


    }


    // to check if the character hits the cloud

    public boolean hits(int num) {
        Rectangle cloudcheck = new Rectangle((int)cloud.get(num).getX(),(int)cloud.get(num).getY(),106,76);
        Rectangle charactercheck = new Rectangle((int)character.getX(),(int)character.getY(),100,75);
        return cloudcheck.intersects(charactercheck);

    }

    //to confirm the crash to the program
    public boolean crash() {
        for (int i = 0; i < cloud.size(); i++)
            if (hits(i))
                return true;
        return false;
    }


}
