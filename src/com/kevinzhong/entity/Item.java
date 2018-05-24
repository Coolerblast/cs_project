package com.kevinzhong.entity;

import com.kevinzhong.gfx.Animation;
import com.kevinzhong.gfx.ImageLoader;
import com.kevinzhong.gfx.SpriteSheet;
import com.kevinzhong.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Item extends Entity {
    private BufferedImage sprite;
    private boolean dropped;
    private boolean inInventory;
    private boolean stackable;
    private int amount = 1;
    private int type;
    private static int ItemSize = 12;

    public Item() {
        super(16, 16);
        getStackable();
    }

    public Item(int t, boolean d) {
        super(16, 16);
        type = t;
        dropped = d;
        getStackable();
    }

    public Item(int t, boolean d, int x, int y) {
        super(ItemSize, ItemSize);
        type = t;
        dropped = d;
        getStackable();
        super.setLocation(x, y);
    }

    public void update() {
        move();
        super.setxVel(super.getxVel() * DECELERATION_RATE);
    }

    public boolean isDropped() {
        return dropped;
    }

    public boolean isInInventory() {
        return inInventory;
    }

    public boolean isStackable() {
        return stackable;
    }

    public int getAmount() {
        return amount;
    }

    public int getType() {
        return type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static void StackStackableItems(LinkedList<Item> items) {
        int index;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isStackable()) {
                index = findCloseAndSimilar(items, i);
                if (index == -1)
                    continue;
                items.get(i).setLocation((items.get(i).getBounds().getX() + items.get(index).getBounds().getX()) / 2,
                        (items.get(i).getBounds().getY() + items.get(index).getBounds().getY()) / 2);
                items.get(i).setAmount(items.get(i).getAmount() + items.get(index).getAmount());
                items.get(i).jump();
                items.get(i).setxVel(Math.random() * 20 - 10);
                items.remove(index);

            }
        }
    }

    private static int findCloseAndSimilar(LinkedList<Item> items, int index) {
        for (int i = 0; i < items.size(); i++)
            if (items.get(i).isStackable() && i != index) {
                if (items.get(i).getType() == items.get(index).getType())
                    if (findDistance(items.get(i), items.get(index)) < Tile.getTileSize() * 2)
                        return i;
            }
        return -1;
    }

    public void setSprite(BufferedImage image) {
        this.sprite = image;
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public void drawSprite(Graphics2D g, int x, int y) {
        if(sprite != null)
        g.drawImage(sprite, x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight(), null);
    }

    public void render(Graphics2D g) {
        if(sprite != null)
        g.drawImage(sprite, (int) this.getBounds().getX(), (int) this.getBounds().getY(), sprite.getWidth(), sprite.getHeight(), null);

    }

    private void getStackable() {
        if (type == 0 || type == 1 || type == 2)
            stackable = true;
    }
}
