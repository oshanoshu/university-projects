package com.company;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;

public class ImagePanel extends JPanel{
    private Image background;


    public ImagePanel(String img)
    {
        this(new ImageIcon(img).getImage());


    }


    public ImagePanel(Image img) {
        background= img;

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        Main.main.repaint(g);

    }
}
