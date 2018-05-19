package com.kevinzhong.entity;

public abstract class Enemy extends Mob {
    private Mob target;

    public Enemy(int w, int h, int health, int invincibilityTime, int damage) {
        super(w, h, health, invincibilityTime, damage);
    }

    /***
     * Default Constructor for a Mob with the default health of 100
     * @param w Desired width of Mob
     * @param h Desired height of Mob
     */
    public Enemy(int w, int h) {
        this(w, h, 100, 5, 5);
    }

    public void followTarget() {
        if (this.getBounds().getMinX() + this.getBounds().getWidth() / 2 < target.getBounds().getMinX() + target.getBounds().getWidth() / 2) {
            this.setRightAccel(true);
            this.setLeftAccel(false);
        } else {
            this.setLeftAccel(true);
            this.setRightAccel(false);
        }
    }

    public void update() {
        followTarget();
        super.update();
    }

    /**
     * Sets the target for the entity
     * @param target the target mob
     */
    public void setTarget(Mob target) {
        this.target = target;
    }
}
