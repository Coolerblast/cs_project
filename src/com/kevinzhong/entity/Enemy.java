package com.kevinzhong.entity;

public abstract class Enemy extends Mob {
    private Mob target;

    /***
     * Default Constructor for a Enemy with the default health of 100, invincibilty time of 5 ticks, and knockback power of 5.
     * @param w Desired width of Enemy
     * @param h Desired height of Enemy
     * @param health Desired health of Enemy
     * @param invincibilityTime Desired invincibility time of Enemy(in ticks)
     * @param damage Desired damage of Enemy
     */
    public Enemy(int w, int h, int health, int invincibilityTime, int damage, int knockback) {
        super(w, h, health, invincibilityTime, damage, knockback);
    }

    /***
     * Default Constructor for a Enemy with the default health of 100, invincibilty time of 5 ticks, and knockback power of 5.
     * @param w Desired width of Mob
     * @param h Desired height of Mob
     */
    public Enemy(int w, int h) {
        this(w, h, 100, 5, 5, 5);
    }

    /**
     * Follows the set target
     */
    private void followTarget() {
        if (this.getBounds().getMinX() + this.getBounds().getWidth() / 2 < target.getBounds().getMinX() + target.getBounds().getWidth() / 2) {
            this.setRightAccel(true);
            this.setLeftAccel(false);
        } else {
            this.setLeftAccel(true);
            this.setRightAccel(false);
        }
    }

    /**
     * Same update methods as the one in the Mob class but with followTarger() implemented.
     */
    public void update() {
        followTarget();
        super.update();
    }

    /**
     * Sets the target for the entity
     *
     * @param target the target mob
     */
    public void setTarget(Mob target) {
        this.target = target;
    }
}
