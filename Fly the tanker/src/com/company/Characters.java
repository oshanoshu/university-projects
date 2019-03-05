package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class Characters {
    private Image tank;   //The character of the game
    private double x;      // X-coordinate of the character
    private double y;       // Y-coordinate of the character


    //Constructor to create the image with x-coordinate and y-coordinate given

    public Characters(Image tank1, double xC, double yC) {
        tank = tank1;
        x = xC;
        y = yC;

    }

    public Characters(String path, double xC, double yC) {
        this(new ImageIcon(path).getImage(), xC, yC);
        //to get the image from the external file
    }

    public void setChar(String path) {
        tank = new ImageIcon(path).getImage();
    }

    public void setPosition(double xC, double yC) {
        x = xC;
        y = yC;
    }

    public void setY(double newY) {
        y = newY;
    }

    public void setX(double newX) {
        x = newX;
    }

    //Get methods which I'm also not commenting
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Image getImage() {
        return tank;
    }


}