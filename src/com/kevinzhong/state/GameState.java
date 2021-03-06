package com.kevinzhong.state;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.swing.ImageIcon;

import com.kevinzhong.entity.*;
import com.kevinzhong.gfx.ImageLoader;
import com.kevinzhong.gfx.ParallaxEngine;
import com.kevinzhong.gfx.ParallaxLayer;
import com.kevinzhong.handlers.Camera;
import com.kevinzhong.main.GamePanel;
import com.kevinzhong.tile.Tile;
import com.kevinzhong.tilemap.TerrainGenerator;
import com.kevinzhong.util.ThreadPool;

public class GameState extends State {
    private int width, height;
    private static int maxTilesY, maxTilesX;
    private Player player;
    private Camera cam;
    private String saveName;
    private Tile[][] tile;
    private ArrayList<Point> points;
    private ArrayList<Mob> mobs;
    private LinkedList<Item> items;
    private int maxItems = 401;
    private ThreadPool pool;
    private Item onCursor;

    private ParallaxEngine parallaxEngine;

    private Zombie zombie;

    public GameState(int w, int h, Player p, Camera c, String save) {
        width = w;
        height = h;
        player = p;
        cam = c;
        saveName = save;
    }

    public void init() {
        player.init();
        points = new ArrayList<>();
        File save = new File(this.saveName);
        saveName = null;
        if (save.isFile()) {
            try {
                System.out.println("Loading save");
                Scanner in = new Scanner(save);
                setWorldSize(in.nextInt(), in.nextInt());
                tile = new Tile[maxTilesY][maxTilesX];
                for (int y = 0; y < tile.length; y++)
                    for (int x = 0; x < tile[0].length; x++) {
                        tile[y][x] = new Tile();
                    }
                for (Tile[] i : tile)
                    for (Tile t : i) {
                        String temp = in.next();
                        int active = Integer.parseInt(temp.substring(0, 2));
                        int type = Integer.parseInt(temp.substring(2, 6));
                        t.setActive(active);
                        t.setType(type);
                    }
                in.close();
            } catch (IOException e) {
                System.out.println("Save could not be read!");
            } catch (InputMismatchException e) {
                System.out.println("Problem occurred while loading save!");
            }
        } else {
            tile = new Tile[150][200];
            setWorldSize(tile[0].length, tile.length);
            for (int y = 0; y < tile.length; y++)
                for (int x = 0; x < tile[0].length; x++) {
                    tile[y][x] = new Tile();
                }
            System.out.println("Generating new world");
            TerrainGenerator.generateTerrain(new Long("47664444444"), tile);
        }
        player.setX(maxTilesX * Tile.getTileSize() / 2);
        player.setY(96 * Tile.getTileSize());
        items = new LinkedList<>();

        /*********************
         * TEMPORARY CODE!!! *
         *********************/
        BufferedImage bg1 = ImageLoader.loadImage("resources/mountains.png");
        BufferedImage after = new BufferedImage(bg1.getWidth() * 2, bg1.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(3.0, 3.0);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(bg1, after);

        parallaxEngine = new ParallaxEngine(new ParallaxLayer(after, 2, 3));
        pool = new ThreadPool();

        zombie = new Zombie(32, 48);
        zombie.setTarget(player);
        zombie.setX(maxTilesX * Tile.getTileSize() / 2 + 64);
        zombie.setY(96 * Tile.getTileSize());

        mobs = new ArrayList<>();

        mobs.add(player);
        mobs.add(zombie);
    }

    public void render(Graphics2D g) {
        parallaxEngine.render(g, cam);

        for (int y = (int) (cam.getY() / Tile.getTileSize()) - 1; y < (int) (cam.getY() + height)
                / Tile.getTileSize() + 1; y++)
            for (int x = (int) (cam.getX() / Tile.getTileSize()) - 1; x < (int) (cam.getX() + width)
                    / Tile.getTileSize() + 1; x++)
                if (!(x < 0 || y < 0 || x >= maxTilesX || y >= maxTilesY))
                    if (tile[y][x].getActive() != 0)
                        if (tile[y][x].getType() == 0)
                            g.drawImage(ImageLoader.loadImage("resources/textures/dirt.png"),
                                    x * Tile.getTileSize(), y * Tile.getTileSize(), Tile.getTileSize(),
                                    Tile.getTileSize(), null);
                        else if (tile[y][x].getType() == 1)
                            g.drawImage(ImageLoader.loadImage("resources/textures/stone.png"),
                                    x * Tile.getTileSize(), y * Tile.getTileSize(), Tile.getTileSize(),
                                    Tile.getTileSize(), null);
                        else if (tile[y][x].getType() == 2)
                            g.drawImage(ImageLoader.loadImage("resources/textures/grass.png"),
                                    x * Tile.getTileSize(), y * Tile.getTileSize(), Tile.getTileSize(),
                                    Tile.getTileSize(), null);
                        else if (tile[y][x].getType() == 3)
                            g.drawImage(ImageLoader.loadImage("resources/textures/plant.png"),
                                    x * Tile.getTileSize(), y * Tile.getTileSize(), Tile.getTileSize(),
                                    Tile.getTileSize(), null);

        player.render(g);
        zombie.render(g);

        for (Item i : items) {
            if (i.getBounds().getMinX() > cam.getX() && i.getBounds().getMaxX() < cam.getX() + width &&
                    i.getBounds().getMinY() > cam.getY() && i.getBounds().getMaxY() < cam.getY() + height)
                i.render(g);
        }
        // drawCollisionBounds(g);

        g.setColor(Color.GREEN);


//        for (Point p : points)
//            g.draw(new Rectangle(p));

        g.translate(cam.getX(), cam.getY());

        drawPlayerHealth(g);
        player.drawInventory(g);

        g.translate(-cam.getX(), -cam.getY());
    }

    public void update() {
        player.update();
        zombie.update();

//        if (player.getPlacingBlocks())
//            placeBlock(player.getCurrentBlock(), GamePanel.getMainJFrame().getContentPane().getMousePosition());
        if (player.getBreakingBlocks())
            breakBlock(GamePanel.getMainJFrame().getContentPane().getMousePosition());

        for (Mob mob : mobs) {
            LinkedList<Point> t = new LinkedList<>();
            for (int y = (int) mob.getY() - 1; y < (int) mob.getY() + 5; y++)
                for (int x = (int) mob.getX() - 1; x < (int) mob.getX() + 3; x++)
                    if (y < tile.length && x < tile[0].length && !(y < 0 || x < 0))
                        if (tile[y][x].getActive() == 1)
                            t.add(new Point(x, y));
            mob.checkBlockCollision(t);
        }

        for (Item i : items) {
            LinkedList<Point> t = new LinkedList<>();
            if (i.getBounds().getMinX() > cam.getX() && i.getBounds().getMaxX() < cam.getX() + width &&
                    i.getBounds().getMinY() > cam.getY() && i.getBounds().getMaxY() < cam.getY() + height) {
                i.update();
                for (int y = (int) i.getY() - 1; y < (int) i.getY() + 3; y++)
                    for (int x = (int) i.getX() - 1; x < (int) i.getX() + 1; x++)
                        if (y < tile.length && x < tile[0].length && !(y < 0 || x < 0))
                            if (tile[y][x].getActive() == 1)
                                t.add(new Point(x, y));
                i.checkBlockCollision(t);
            }
        }

        for (Tile[] a : tile)
            for (Tile t : a)
                t.update();

        Item.StackStackableItems(items);
        // System.out.println(player.getX() + " " + player.getY());
        // System.out.println(zombie.getX() + " " + zombie.getY());

        if (player.getxVel() < -1)
            parallaxEngine.setLeft();
        if (player.getxVel() > 1)
            parallaxEngine.setRight();
        if (player.getyVel() < -1)
            parallaxEngine.setUp();
        if (player.getyVel() > 1)
            parallaxEngine.setDown();

        if (Math.abs(player.getxVel()) < 1)
            parallaxEngine.stopHorizontal();
        if (Math.abs(player.getyVel()) < 1)
            parallaxEngine.stopVertical();
        if (Math.abs(player.getxVel()) > 1 || Math.abs(player.getyVel()) > 1)
            parallaxEngine.update();

        for (Mob a : mobs)
            if (Enemy.class.isInstance(a))
                if (a.getBounds().intersects(player.getBounds()))
                    a.damage(player);
    }

    public void placeBlock(int type, Point p) {
        if (p == null)
            return;
        Point mouseLocation = new Point((int) (p.getX() + cam.getX()), (int) (p.getY() + cam.getY()));
        Rectangle temp = new Rectangle(mouseLocation);
        temp.setSize(1, 1);

        int bx = (int) ((p.getX() + cam.getX()) / (double) Tile.getTileSize());
        int by = (int) ((p.getY() + cam.getY()) / (double) Tile.getTileSize());

        // Has to place next to active block
        if (by != 0 && bx != 0 && by != maxTilesY - 1 && bx != maxTilesX - 1)
            if (!(tile[by - 1][bx].getActive() != 0 || tile[by + 1][bx].getActive() != 0
                    || tile[by][bx - 1].getActive() != 0 || tile[by][bx + 1].getActive() != 0))
                return;
        Tile tile = new Tile();
        tile.setType(type);
        if (type == 3) {
            if (!(this.tile[by + 1][bx].getType() == 2))
                return;
            tile.setActive(2);
        } else {
            tile.setActive(1);
        }
        if (!player.getBounds().intersects(temp) && (this.tile[by][bx].getActive() == 0 || this.tile[by][bx].getType() == 3))
            this.tile[by][bx] = tile;
        else
            return;
        // System.out.println("Placed a block!");

        points.add(mouseLocation);
    }

    public void breakBlock(Point p) {
        if (p == null)
            return;
        Point mouseLocation = new Point((int) (p.getX() + cam.getX()), (int) (p.getY() + cam.getY()));
        Rectangle temp = new Rectangle(mouseLocation);
        temp.setSize(1, 1);

        int bx = (int) ((p.getX() + cam.getX()) / (double) Tile.getTileSize());
        int by = (int) ((p.getY() + cam.getY()) / (double) Tile.getTileSize());

        int type = tile[by][bx].getType();

        if (player.getBounds().intersects(temp) || this.tile[by][bx].getActive() == 0)
            return;

        if (type == 2 && tile[by - 1][bx].getType() == 3)
            tile[by - 1][bx].clear();
        this.tile[by][bx].clear();

        points.add(mouseLocation);

        // System.out.println("Broke a block!");
    }

    public void setOnCursor(Item item) {
        onCursor = item;
    }

    public Item getOnCursor() {
        return onCursor;
    }

    public void save() {
        pool.runTask(new Runnable() {
            @Override
            public void run() {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("saves/save.txt", "UTF-8");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                writer.println(tile[0].length);
                writer.println(tile.length);
                for (Tile[] b : tile)
                    for (Tile t : b) {
                        writer.print(String.format("%02d", t.getActive()));
                        writer.println(String.format("%04d", t.getType()));
                    }
                writer.close();
            }
        });
    }

    private void drawPlayerHealth(Graphics2D g) {
        int barLengthFromRight = 20;
        int barLengthFromTop = 30;
        int barWidth = 500;
        int barHeight = 30;

        Color missingColor = Color.black;
        Color healthColor = Color.red;
        Color borderColor = Color.white;

        g.setColor(missingColor);
        g.fillRect((int) (GamePanel.getMainJFrame().getWidth() - barWidth - barLengthFromRight), barLengthFromTop, barWidth, barHeight);
        g.setColor(healthColor);
        g.fillRect((int) ( GamePanel.getMainJFrame().getWidth() - barWidth - barLengthFromRight), barLengthFromTop, (int) (((double) player.getHealth() / player.getMaxHealth()) * barWidth), barHeight);
        g.setColor(borderColor);
        g.setStroke(new BasicStroke(2));
        g.drawRect((int) (GamePanel.getMainJFrame().getWidth() - barWidth - barLengthFromRight), barLengthFromTop, barWidth, barHeight);
    }

    public void drawCollisionBounds(Graphics2D g) {
        g.setColor(Color.green);
        for (int y = (int) (cam.getY() / Tile.getTileSize()) - 1; y < (int) (cam.getY() + height)
                / Tile.getTileSize() + 1; y++)
            for (int x = (int) (cam.getX() / Tile.getTileSize()) - 1; x < (int) (cam.getX() + width)
                    / Tile.getTileSize() + 1; x++)
                if (!(x < 0 || y < 0 || x >= maxTilesX || y >= maxTilesY))
                    if (tile[y][x].getActive() == 1) {
                        Rectangle temp = new Rectangle(x * Tile.getTileSize(), y * Tile.getTileSize(),
                                Tile.getTileSize(), Tile.getTileSize());
                        g.draw(temp.getBounds());
                    }
        g.setColor(Color.green);
        g.draw(player.getTopBounds());
        g.setColor(Color.blue);
        g.draw(player.getBottomBounds());
        g.setColor(Color.red);
        g.draw(player.getLeftBounds());
        g.draw(player.getRightBounds());
    }

    public static void setWorldSize(int x, int y) {
        maxTilesX = x;
        maxTilesY = y;
    }

    public static int getMaxX() {
        return maxTilesX;
    }

    public static int getMaxY() {
        return maxTilesY;
    }


}