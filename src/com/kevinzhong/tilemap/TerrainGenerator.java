package com.kevinzhong.tilemap;

import java.util.Random;

import com.kevinzhong.ID.TileID;
import com.kevinzhong.state.GameState;
import com.kevinzhong.tile.Tile;

public class TerrainGenerator {

    private static long Z;

    public static void generateTerrain(Long seed, Tile[][] tile) {
        generateFlat(tile);
//		System.out.println("Generating Grass");
//		generate(tile, TileID.Grass, seed, 0, 100, 100);
//		System.out.println("Generating Dirt");
//		generate(tile, TileID.Dirt, seed, 1, 100, 100);
//		System.out.println("Generating Stone");
//		generate(tile, TileID.Stone, seed, 10, 20, 20);
//		generatePlants(tile);
    }

    private static void generatePlants(Tile[][] tile) {
        for (int y = 0; y < tile.length; y++)
            for (int x = 0; x < tile[0].length; x++)
                if (tile[y][x].getType() == 2 && tile[y][x].getActive() == 1)
                    if (Math.random() > .5) {
                        tile[y - 1][x].setType(3);
                        tile[y - 1][x].setActive(2);
                    }

    }

    private static void generateFlat(Tile[][] tile) {
        int x = 0;
        while (x < GameState.getMaxX()) {
            tile[100][x].setType(2);
            tile[100][x].setActive(1);
            for (int i = 101; i < GameState.getMaxY(); i++) {
                tile[i][x].setType(0);
                tile[i][x].setActive(1);
                tile[i][x].setCanRegenerate(true);
            }
            x++;
        }
    }

    private static void generate(Tile[][] tile, int TileID, Long seed, int yDif, double wl, double amp) {
        Random random = new Random(seed);
        Z = (int) Math.floor(random.nextInt());
        double a = rand();
        double b = rand();
        double y;
        int x = 0;

        while (x < GameState.getMaxX()) {
            if (x % wl == 0) {
                a = b;
                b = rand();
                y = a * amp + GameState.getMaxY();
            } else {
                y = interpolate(a, b, x % wl / wl) * amp + GameState.getMaxY();
            }

            for (int i = (int) (y + yDif); i < GameState.getMaxY(); i++) {
                tile[i][x].setType(TileID);
                tile[i][x].setActive(1);
                tile[i][x].setCanRegenerate(true);
                //System.out.println(x + " " + i + " " + tile[i][x].getActive());
            }

            x++;
            // System.out.println(rand() + " " + Z);
            System.out.println(
                    "" + (int) (((x) / (double) (GameState.getMaxX()) * 10000) / 100D)
                            + "%");
        }
    }

    private static double rand() {
        int C = 1;
        Z = (Integer.MAX_VALUE / 2 * Z + C) % Integer.MAX_VALUE;
        return Z / (double) Integer.MAX_VALUE;
    }

    private static double interpolate(double pa, double pb, double px) {
        double ft = px * Math.PI, f = (1 - Math.cos(ft)) * 0.5;
        return pa * (1 - f) + pb * f;
    }
}
