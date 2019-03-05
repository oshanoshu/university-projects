package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by acous on 8/23/2016.
 */
public class Renderer extends JPanel{

    //private static final long serialVersionUID = 1L;

    protected void paintComponent(Graphics g) {
        Main.main.repaint(g);
    }

}
