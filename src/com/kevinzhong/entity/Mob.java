package com.kevinzhong.entity;

import com.kevinzhong.gfx.Animation;

import java.awt.*;

public abstract class Mob extends Entity {

    private boolean leftAccel, rightAccel;
    private double moveSpeed;
    private Animation playerWalk;
    private boolean facingRight;
    private int health;

    public Mob(int w, int h, int health) {
        super(w, h);
        leftAccel = false;
        rightAccel = false;
        facingRight = true;
        this.health = health;
    }

    public Mob(int w, int h) {
        this(w, h, 100);
    }

    public void update() {
        move();

        if (rightAccel)
            super.setxVel(super.getxVel() + moveSpeed);
        if (leftAccel)
            super.setxVel(super.getxVel() - moveSpeed);

        facingRight = this.getxVel() > 0 ? true : false;

        if (!(leftAccel && rightAccel)) {
            super.setxVel(super.getxVel() * DECELERATION_RATE);
        }
        if (Math.abs(super.getxVel()) < 1)
            playerWalk.setCurrentFrame(0);
        else
            playerWalk.runAnimation();

        playerWalk.setSpeed((int) Math.abs(super.getxVel()));

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void render(Graphics2D g) {
        playerWalk.drawAnimation(g, (int) super.getBounds().getX(), (int) super.getBounds().getY(),
                (int) super.getBounds().getWidth(), (int) super.getBounds().getHeight(), facingRight);
    }

    public void setWalkAnimation(Animation playerWalk) {
        this.playerWalk = playerWalk;
    }

    public void setLeftAccel(boolean b) {
        leftAccel = b;
    }

    public void setRightAccel(boolean b) {
        rightAccel = b;
    }

    public void setMoveSpeed(double x) {
        moveSpeed = x;
    }

}
