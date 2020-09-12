package me.justin.raycastdemo;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 45;
    public static final int SCALE = 16;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("RAYCAST");
        Game comp = new Game(WIDTH, HEIGHT);
        jFrame.add(comp);
        jFrame.pack();

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        jFrame.setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        Thread thread = new Thread(comp);
        thread.start();
    }
}
