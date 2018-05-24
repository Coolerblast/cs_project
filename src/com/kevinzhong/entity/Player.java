package com.kevinzhong.entity;

import com.kevinzhong.gfx.Animation;
import com.kevinzhong.gfx.ImageLoader;
import com.kevinzhong.gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Mob {

    private int selected = 0;
    private Item[] inventory;
    private Rectangle[] inventoryButtons;
    private boolean placingBlocks = false;
    private boolean breakingBlocks = false;
    private boolean inventoryIsOpen = true;
    private static String playerSpriteLoc = "resources/spritesheets/move.png";
    private static String playerAttackLoc = "resources/spritesheets/attack.png";
    private int totalIDs = 4;
    private int inventoryColumns = 10;
    private int inventoryX = 20;
    private int inventoryY = 20;
    private int hotbarSlotSize = 50;
    private int invSize = (int) (hotbarSlotSize * .95);
    private int hotbarXGap = 15;
    private int hotbarLength = (hotbarSlotSize + hotbarXGap) * (inventoryColumns - 1) + hotbarSlotSize;
    private int yGap = (int) Math.round(hotbarXGap * .9);
    private int invGap = (int) Math.round((hotbarLength - invSize) / (double) (inventoryColumns - 1)) - invSize;

    public Player() {

        super(32, 48, 100, 30, 3, 4); // fix this
        super.setWalkAnimation(new Animation(new SpriteSheet(ImageLoader.loadImage(playerSpriteLoc)), 0, 0, 50, 50, 11));
        super.setAttackAnimation(new Animation(new SpriteSheet(ImageLoader.loadImage(playerAttackLoc)), 0, 0, 50, 50, 10));
    }

    public void init() {
        setMoveSpeed(5);
        setAttackSpeed(5);
        inventory = new Item[40];
        inventoryButtons = new Rectangle[40];
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = new Item();
            inventoryButtons[i] = new Rectangle();
        }

        inventory[0].setSprite(ImageLoader.loadImage("resources/textures/grass.png"));
    }

    public void drawInventory(Graphics2D g) {
        int row = inventoryIsOpen ? 4 : 1;
        int index = 0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < inventoryColumns; j++) {
                if (i == 0) {
                    g.setColor(new Color(125, 125, 125, 75));
                    g.fillRoundRect(inventoryX + ((hotbarSlotSize + hotbarXGap) * j), inventoryY + ((hotbarSlotSize + yGap) * i), hotbarSlotSize, hotbarSlotSize, 5, 5);
                    inventory[index++].drawSprite(g, inventoryX + ((hotbarSlotSize + hotbarXGap) * j) + hotbarSlotSize / 2, inventoryY + ((hotbarSlotSize + yGap) * i) + hotbarSlotSize / 2);
                    if (j == selected) {
                        g.setColor(Color.WHITE);
                        g.drawRoundRect(inventoryX + ((hotbarSlotSize + hotbarXGap) * j), inventoryY + ((hotbarSlotSize + yGap) * i), hotbarSlotSize, hotbarSlotSize, 5, 5);
                    }
                    continue;
                }
                g.setColor(new Color(100, 100, 100, 75));
                g.fillRoundRect(inventoryX + ((invSize + invGap) * j), inventoryY + ((invSize + yGap) * i), invSize, invSize, 5, 5);
                inventory[index++].drawSprite(g, inventoryX + ((invSize + invGap) * j) + invSize / 2, inventoryY + ((invSize + yGap) * i) + invSize / 2);
            }
    }

    public void selectInventorySlot(Point p) {
        int row = 4;
        int index = 0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < inventoryColumns; j++) {
                if (j == 0) {
                    inventoryButtons[index++].setRect(inventoryX + ((hotbarSlotSize + hotbarXGap) * j), inventoryY + ((hotbarSlotSize + yGap) * i), hotbarSlotSize, hotbarSlotSize);
                    continue;
                }
                inventoryButtons[index++].setRect(inventoryX + ((invSize + invGap) * j), inventoryY + ((invSize + yGap) * i), invSize, invSize);
            }

            if(inventoryIsOpen) {
                for (int i = 0; i < inventoryButtons.length; i++)
                    if (inventoryButtons[i].contains(p))
                        System.out.println(i);
                return;
            }
            for(int i = 0; i < inventoryColumns; i++)
                if (inventoryButtons[i].contains(p))
                    selected = i;
    }

    public Item[] getInventory() {
        return inventory;
    }

    public void setInventory(int i, Item item) {
        if (i < 0 || i > inventory.length) return;
        inventory[i] = item;
    }

    public void setPlacingBlocks(boolean b) {
        placingBlocks = b;
    }

    public void setBreakingBlocks(boolean b) {
        breakingBlocks = b;
    }

    public boolean getPlacingBlocks() {
        return placingBlocks;
    }

    public boolean getBreakingBlocks() {
        return breakingBlocks;
    }

    public String getSelected() {
        if (inventory[selected] == null)
            return "NULL";
        return inventory[selected].toString();
    }

    public void scrollHotbarSlot(int i) {
        if (i == 1)
            selected++;
        else if (i == -1)
            selected--;

        if (selected >= inventoryColumns)
            selected = 0;
        if (selected < 0)
            selected = inventoryColumns - 1;
    }

    public void setSelectedHotbarSlot(int i) {
        if (i > inventoryColumns - 1 || i < 0)
            return;

        selected = i;
    }

    public boolean isInventoryIsOpen() {
        return inventoryIsOpen;
    }

    public void setInventoryIsOpen(boolean inventoryIsOpen) {
        this.inventoryIsOpen = inventoryIsOpen;
    }

}