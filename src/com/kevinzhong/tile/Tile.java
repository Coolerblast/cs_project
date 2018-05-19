package com.kevinzhong.tile;

public class Tile {
    private int type, active, regenTime, lastActive, tick = 0;
    private boolean canRegenerate, regenerating;

    public Tile() {
        this.type = 0;
        this.active = 0;
        this.regenTime = 120;
        this.canRegenerate = false;
    }

    public Tile(Tile src) {
        this.type = src.getType();
        this.active = src.getActive();
        this.regenTime = src.getRegenTime();
        this.canRegenerate = src.getCanRegenerate();
    }

    public void copy(Tile from) {
        this.type = from.getType();
        this.active = from.getActive();
        this.regenTime = from.getRegenTime();
        this.regenerating = from.isRegenerating();
    }


    public void clear() {
        if (canRegenerate) {
            lastActive = active;
            tick = regenTime;
            regenerating = true;
        }
        setActive(0);
    }

    public void update() {
        if (tick > 0)
            System.out.println(tick);
        if (regenerating) {
            if (tick-- == 0)
                setActive(lastActive);
            if (tick < 0)
                regenerating = false;
        }
    }

    public void setType(int i) {
        type = i;
    }

    public void setActive(int i) {
        active = i;
    }

    public void setRegenTime(int regenTime) {

        this.regenTime = regenTime;
    }

    public void setRegenerating(boolean regenerating) {
        this.regenerating = regenerating;
    }

    public void setCanRegenerate(boolean canRegenerate) {
        this.canRegenerate = canRegenerate;
    }

    public int getType() {
        return type;
    }

    public int getActive() {
        return active;
    }

    public int getRegenTime() {
        return regenTime;
    }

    public boolean isRegenerating() {
        return regenerating;
    }

    public boolean getCanRegenerate() {
        return canRegenerate;
    }

    public static int TILE_SIZE = 16;

    public static int getTileSize() {
        return TILE_SIZE;
    }

}
