package me.justin.raycastdemo;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import me.justin.raycastdemo.inputs.InputListener;
import me.justin.raycastdemo.levels.Level;

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
    private Level level;

    private int x = Main.WIDTH / 2;
    private int y = Main.HEIGHT / 2;
    private int veleX, veleY;
    private double angle = 0;

    public Game(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();

        this.width = width;
        this.height = height;

        this.addKeyListener(inputListener);
        this.addFocusListener(inputListener);

        this.level = new Level();

    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        this.requestFocus();

        Arrays.fill(this.pixels, 0x000000);

        this.level.render(this.pixels);

        this.pixels[x + y * this.width] = 0xFFFFFF;

        for (int i = 2; i < 5; i++) {
            this.pixels[(x + veleX * i) + (y + veleY * i) * this.width] = 0x00FF00;
        }

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
//        int xOffset = 0;
//        int yOffset = 0;
////        for (int i = 0; i < 5; i++) {
//        xOffset = (int) Math.ceil(Math.cos(this.angle / Math.PI * 0.5) * 4);
//        yOffset = (int) Math.ceil(Math.sin(this.angle / Math.PI * 0.5) * 4);
////            if(!(yOffset == 0 && xOffset == 0))
////                break;
////        }

//        System.out.println("Offsets: " + xOffset + ", " + yOffset);

        if (this.inputListener.isPressed(KeyEvent.VK_W)) {
            if (!this.level.collide(this.x + this.veleX, this.y + this.veleY)) {
                this.y += this.veleY;
                this.x += this.veleX;
            }
        }

        if (this.inputListener.isPressed(KeyEvent.VK_S)) {
            if (!this.level.collide(this.x - this.veleX, this.y - this.veleY)) {
                this.y -= this.veleY;
                this.x -= this.veleX;
            }
        }

        int speed = 2;

        if (this.inputListener.isPressed(KeyEvent.VK_A)) {
            this.angle -= 0.1;
            if(this.angle < 0)
                this.angle = 2 * Math.PI;
            this.veleX = (int) (Math.cos(this.angle) * speed);
            this.veleY = (int) (Math.sin(this.angle) * speed);
        }

        if (this.inputListener.isPressed(KeyEvent.VK_D)) {
            this.angle += 0.1;
            if(this.angle > 2 * Math.PI)
                this.angle = 0;
            this.veleX = (int) (Math.cos(this.angle) * speed);
            this.veleY = (int) (Math.sin(this.angle) * speed);
        }
    }
}
