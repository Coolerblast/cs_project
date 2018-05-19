package com.kevinzhong.entity;

import com.kevinzhong.gfx.Animation;

import java.awt.*;

/***
 *  Creates methods used by all mobs, which include setting and editing health, speed, position, animations, direction, and acceleration
 */
public abstract class Mob extends Entity {

    private boolean leftAccel, rightAccel;
    private double moveSpeed, attackSpeed;
    private Animation playerWalk;
    private Animation playerAttack;
    private boolean facingRight, attacking;
    private int health, maxHealth, invincibilityTime, damage;
    private int ticks = 0;

    /***
     * Default Constructor for a Mob
     * @param w Desired width of mob
     * @param h Desired height of mob
     * @param health Desired health of mob
     * @param invincibilityTime ticks before they are susceptible to attacks
     */
    public Mob(int w, int h, int health, int invincibilityTime, int damage) {
        super(w, h);
        leftAccel = false;
        rightAccel = false;
        facingRight = true;
        this.health = health;
        this.maxHealth = health;
        this.invincibilityTime = invincibilityTime;
        this.damage = damage;
    }

    /***
     * Default Constructor for a Mob with the default health of 100
     * @param w Desired width of Mob
     * @param h Desired height of Mob
     */
    public Mob(int w, int h) {
        this(w, h, 100, 5, 5);
    }

    /**
     * Updates the mob, moving if leftAccel or rightAccel are true, running mob animations when moving.
     */
    public void update() {
        if(ticks > 0)
            ticks--;

        move();

        if (rightAccel)
            super.setxVel(super.getxVel() + moveSpeed);
        if (leftAccel)
            super.setxVel(super.getxVel() - moveSpeed);

        if (!attacking)
        facingRight = this.getxVel() > 0 ? true : false;

        if (!(leftAccel && rightAccel)) {
            super.setxVel(super.getxVel() * DECELERATION_RATE);
        }
        if (attacking) {
            playerAttack.runAnimation();
                if (playerAttack.getCurrent() == playerAttack.getMaxFrames() - 1) {
                    attacking = false;
                    playerAttack.setCurrentFrame(0);
            }
        } else if (Math.abs(super.getxVel()) < 1)
            playerWalk.setCurrentFrame(0);
        else
            playerWalk.runAnimation();

        playerWalk.setSpeed((int) Math.abs(super.getxVel()));

    }

    /**
     * Method for a mob attacking a target mob, the target
     * @param target target Mob
     */
    public void damage(Mob target) {
        target.takeDamage(this.getDamage());
    }
    /**
     * Method for when the mob gets hit, does not get damaged when ticks is greater than 0. If ticks is less than or equal to 0 it damages the mob and sets ticks to the invincibility time
     * @param x how much of the mob's health is gone
     */
    public void takeDamage(int x) {
        if(ticks > 0) return;

        ticks = invincibilityTime;
        this.setHealth(this.getHealth() - x);
    }

    /**
     * Sets attacking to true
     */
    public void attack() {
        attacking = true;
    }

    /**
     * Returns mob's attack damage
     * @return returns attack damage
     */
    public int getDamage() { return damage; }

    /**
     * Returns the health of the mob
     *
     * @return returns health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the maximum health of the mob
     *
     * @return max health value
     */
    public int getMaxHealth() { return maxHealth; }

    /**
     * Sets the attack damage of the mob
     * @param damage desired attack damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    /**
     * Sets the health of a Mob
     *
     * @param health desired health value
     */
    public void setHealth(int health) {
        this.health = health;
        if(this.health < 0) this.health = 0;
    }

    /**
     * Sets the maximum health of a Mob
     *
     * @param maxHealth desired max health value
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Draws the mob to the screen
     *
     * @param g
     */
    public void render(Graphics2D g) {
        if (attacking)
            playerAttack.drawAnimation(g, (int) (super.getBounds().getX() + super.getBounds().getWidth() / 2), (int) (super.getBounds().getY()),
                    2, 2, facingRight);
        else
            playerWalk.drawAnimation(g, (int) (super.getBounds().getX() + super.getBounds().getWidth() / 2), (int) (super.getBounds().getY()),
                    2, 2, facingRight);
    }

    /**
     * Sets the walking animation for the mob
     *
     * @param playerWalk takes desired Animation
     */
    public void setWalkAnimation(Animation playerWalk) {
        this.playerWalk = playerWalk;
        this.playerWalk.setCurrentFrame(0);
    }

    /**
     * Sets the attacking animation for the mob
     *
     * @param playerAttack takes desired Animation
     */
    public void setAttackAnimation(Animation playerAttack) {
        this.playerAttack = playerAttack;
        this.playerAttack.setCurrentFrame(0);
    }

    /**
     * Sets left side acceleration
     * @param b toggles on/off acceleration
     */
    public void setLeftAccel(boolean b) {
        leftAccel = b;
    }
    /**
     * Sets right side acceleration
     * @param b toggles on/off acceleration
     */
    public void setRightAccel(boolean b) {
        rightAccel = b;
    }

    /**
     * Sets the move speed.
     * @param x value of move speed
     */
    public void setMoveSpeed(double x) {
        moveSpeed = x;
    }
    /**
     * Sets the attack speed.
     * @param x value of attack speed
     */
    public void setAttackSpeed(double x) {
        attackSpeed = x;
    }

}
