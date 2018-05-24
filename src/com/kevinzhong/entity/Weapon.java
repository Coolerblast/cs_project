package com.kevinzhong.entity;

import com.kevinzhong.gfx.Animation;

import java.awt.*;

public class Weapon extends Item {

    private int damage, attackSpeed;
    private Animation attackAnimation;

    private Rectangle attackArea;

    public Weapon(int t, boolean d) {
        super(t, d);
    }

    public Weapon(int t, boolean d, int x, int y) {
        super(t, d, x, y);
    }

    public Rectangle getAttackArea() {
        return attackArea;
    }

    public void setAttackArea(Rectangle attackArea) {
        this.attackArea = attackArea;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public Animation getAttackAnimation() {

        return attackAnimation;
    }

    public void setAttackAnimation(Animation attackAnimation) {
        this.attackAnimation = attackAnimation;
    }

}