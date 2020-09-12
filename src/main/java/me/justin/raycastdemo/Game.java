package me.justin.raycastdemo;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Game extends Canvas implements Runnable
{
    private BufferedImage image;
    private int[] pixels;

    public Game(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();



    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        this.requestFocus();

        Arrays.fill(this.pixels, 0xFF00FF);

        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(this.image, 0, 0, Main.WIDTH * Main.SCALE, Main.HEIGHT * Main.SCALE, null);
        g.dispose();
        bs.show();
    }

    public void run()
    {
        while(true)
        {
            this.render();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
