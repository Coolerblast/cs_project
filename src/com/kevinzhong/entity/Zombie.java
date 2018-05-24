package com.kevinzhong.entity;

import com.kevinzhong.gfx.Animation;
import com.kevinzhong.gfx.ImageLoader;
import com.kevinzhong.gfx.SpriteSheet;

public class Zombie extends Enemy {
    private static String zombieWalkSpriteLoc = "resources/spritesheets/move.png";
    private static String zombieAttackSpriteLoc = "resources/spritesheets/attack.png";


    public Zombie(int w, int h, int health) {
        super(w, h, health, 5, 10, 10);
        super.setWalkAnimation(new Animation(new SpriteSheet(ImageLoader.loadImage(zombieWalkSpriteLoc)), 0, 0, 50, 50, 11  ));
        super.setAttackAnimation(new Animation(new SpriteSheet(ImageLoader.loadImage(zombieAttackSpriteLoc)), 0, 0, 50, 50, 10  ));

        setMoveSpeed(3);
    }

    public Zombie(int w, int h) {
        this(w, h, 100);
    }
}
