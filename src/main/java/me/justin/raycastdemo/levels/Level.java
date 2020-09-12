package me.justin.raycastdemo.levels;

import me.justin.raycastdemo.Main;

public class Level {
    private int[][] tiles = {
            {1,1,1,1,1,1,1,1},
            {1,0,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1},
    };
    private static final int TILE_SIZE = 8;

    public boolean collide(int x, int y) {
        System.out.println("CHECKING");
        int xTile = x / TILE_SIZE;
        int yTile = y /TILE_SIZE;
        System.out.println(xTile + " " + yTile);
        if (tiles[yTile][xTile] != 0) {
            return true;
        }
        return false;
    }

    public void drawPixel(int[] pixels, int x, int y) {
        for (int i = 0; i < TILE_SIZE; i++) {
            for (int j = 0; j < TILE_SIZE; j++) {
                pixels[i + (x * TILE_SIZE) + (j + (y * TILE_SIZE)) * Main.WIDTH] = 0xFF0000;
            }
        }
    }

    public void render(int[] pixels) {
        for (int i = 0; i < tiles.length; i++) {
            int[] row = tiles[i];
            for (int j = 0; j < row.length; j++) {
                int tileId = row[j];
                if (tileId == 1) {
                    drawPixel(pixels, j, i);
                }
            }
        }
    }
}
