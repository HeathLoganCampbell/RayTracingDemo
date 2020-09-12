package me.justin.raycastdemo;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import me.justin.raycastdemo.inputs.InputListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Game extends Canvas implements Runnable
{
    private BufferedImage image;
    private int[] pixels;
    private InputListener inputListener = new InputListener();
    private int width, height;

    private int x = Main.WIDTH / 2;
    private int y = Main.HEIGHT / 2;

    public Game(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();

        this.width = width;
        this.height = height;

        this.addKeyListener(inputListener);
        this.addFocusListener(inputListener);

    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        this.requestFocus();

        Arrays.fill(this.pixels, 0x000000);

        this.pixels[x + y * this.width] = 0xFFFFFF;

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
            this.tick();
            this.render();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        if (this.inputListener.isPressed(KeyEvent.VK_W)) {
            this.y--;
        }

        if (this.inputListener.isPressed(KeyEvent.VK_S)) {
            this.y++;
        }

        if (this.inputListener.isPressed(KeyEvent.VK_A)) {
            this.x--;
        }

        if (this.inputListener.isPressed(KeyEvent.VK_D)) {
            this.x++;
        }
    }
}
