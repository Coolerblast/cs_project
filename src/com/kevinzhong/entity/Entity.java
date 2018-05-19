package com.kevinzhong.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.kevinzhong.state.GameState;
import com.kevinzhong.tile.Tile;

public abstract class Entity {

    protected final double GRAVITY = .5;
    protected final double MAX_VERTICAL_VELOCITY = 50;
    protected final double MAX_HORIZONTAL_VELOCITY = 20;
    protected final float DECELERATION_RATE = 0.34f;
    private int SIDE_COLLISION_WIDTH, SIDE_COLLISION_HEIGHT;

    private double x, y, yVel, xVel;
    private boolean falling, canJump;

    private Rectangle entity;

    public Entity(double w, double h) {

        entity = new Rectangle((int) w, (int) h);

        SIDE_COLLISION_WIDTH = (int) Math.round(entity.getWidth() / 8);
        SIDE_COLLISION_HEIGHT = (int) Math.round(entity.getHeight() / 16);

        falling = true;
        canJump = false;

    }

    public void move() {

        if (xVel < 0) {
            if (xVel < -1 * MAX_HORIZONTAL_VELOCITY)
                xVel = -1 * MAX_HORIZONTAL_VELOCITY;
        } else {
            if (xVel > MAX_HORIZONTAL_VELOCITY)
                xVel = MAX_HORIZONTAL_VELOCITY;
        }
        if (falling && yVel <= MAX_VERTICAL_VELOCITY)
            yVel += GRAVITY;

        if (yVel > MAX_VERTICAL_VELOCITY)
            yVel = MAX_VERTICAL_VELOCITY;

        y += yVel;
        x += xVel;

        if (y > GameState.getMaxY() * Tile.getTileSize() - entity.getHeight()) {
            y = GameState.getMaxY() * Tile.getTileSize() - entity.getHeight();
            yVel = 0;
            canJump = true;
        } else if (y < 0) {
            y = 0;
            yVel = 0;
        }
        if (x < 0)
            x = 0;
        else if (x > GameState.getMaxX() * Tile.getTileSize() - entity.getWidth())
            x = GameState.getMaxX() * Tile.getTileSize() - entity.getWidth();

        entity.setLocation((int) x, (int) y);
    }

    public void jump() {
        if (canJump && yVel <= 3) {
            yVel -= entity.getHeight() * .25;
            canJump = false;
            falling = true;
        }
    }

    public double getX() {
        return x / Tile.getTileSize();
    }

    public double getY() {
        return y / Tile.getTileSize();
    }

    public double getyVel() {
        return yVel;
    }

    public double getxVel() {
        return xVel;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
        entity.setLocation((int) x, (int) y);
    }

    public Rectangle getBounds() {
        return entity;
    }

    public Rectangle getTopBounds() {
        return new Rectangle((int) entity.getX() + SIDE_COLLISION_WIDTH, (int) (entity.getY() - entity.getHeight() * 0),
                (int) entity.getWidth() - 2 * SIDE_COLLISION_WIDTH, (int) (entity.getHeight() * .5));
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) entity.getX() + SIDE_COLLISION_WIDTH,
                (int) (entity.getY() + entity.getHeight() / 2.0), (int) entity.getWidth() - 2 * SIDE_COLLISION_WIDTH,
                (int) (entity.getHeight() * .6));
    }

    public Rectangle getLeftBounds() {
        return new Rectangle((int) entity.getX(), (int) entity.getY() + SIDE_COLLISION_HEIGHT, SIDE_COLLISION_WIDTH,
                (int) entity.getHeight() - 2 * SIDE_COLLISION_HEIGHT);
    }

    public Rectangle getRightBounds() {
        return new Rectangle((int) (entity.getX() + entity.getWidth()) - SIDE_COLLISION_WIDTH,
                (int) entity.getY() + SIDE_COLLISION_HEIGHT, SIDE_COLLISION_WIDTH,
                (int) entity.getHeight() - 2 * SIDE_COLLISION_HEIGHT);
    }

    public void checkBlockCollision(LinkedList<Point> points) {
//        if(x >= (int)entity.getMinX() / Tile.getTileSize() && x <= (int)entity.getMaxX() / Tile.getTileSize()
//                && y <= (int)entity.getMinY() / Tile.getTileSize() && y >= (int) entity.getMaxY() / Tile.getTileSize() - 1)
//                    return;

        boolean top = false, left = false, right = false, bottom = false;
        Rectangle intersectsBottom = new Rectangle(),
                intersectsTop = new Rectangle(),
                intersectsLeft = new Rectangle(),
                intersectsRight = new Rectangle();
        for (Point p : points) {
            Rectangle b = new Rectangle(new Point((int) p.getX() * Tile.getTileSize(), (int) p.getY() * Tile.getTileSize()), new Dimension(Tile.getTileSize(), Tile.getTileSize()));

            if (!top) {
                top = this.getTopBounds().intersects(b.getBounds());
                intersectsTop = b;
            }
            if (!bottom) {
                bottom = this.getBottomBounds().intersects(b.getBounds());
                intersectsBottom = b;
            }
            if (!left) {
                left = this.getLeftBounds().intersects(b.getBounds());
                intersectsLeft = b;
            }
            if (!right) {
                right = this.getRightBounds().intersects(b.getBounds());
                intersectsRight = b;
            }
        }

        if (top) {
            if (bottom) {

                entity.setLocation((int) entity.getX(), (int) intersectsTop.getMaxY());
                this.y = intersectsTop.getMaxY();
            }
            yVel = 0;
            canJump = false;
        }

        if (bottom) {
            yVel = 0;
            entity.setLocation((int) entity.getX(), (int) (intersectsBottom.getMinY() - this.getBounds().getHeight()));
            this.y = intersectsBottom.getMinY() - this.getBounds().getHeight();
            canJump = true;
            falling = false;
        } else {
            if ((x == (int) this.x || x == (int) this.x + 1) && y == (int) this.y + 3)
                canJump = false;
            falling = true;
        }


        if (left ^ right) {
            if (left) {
                entity.setLocation((int) intersectsLeft.getMaxX(), (int) entity.getY());
                this.x = intersectsLeft.getMaxX();
                xVel = 0;
            }
            if (right) {
                entity.setLocation((int) (intersectsRight.getMinX() - this.getBounds().getWidth()), (int) entity.getY());
                this.x = intersectsRight.getMinX() - this.getBounds().getWidth();
                xVel = 0;
            }
        }
        // System.out.println(canJump);

    }

    protected static double findDistance(Entity a, Entity b) {
        return Math.sqrt(Math.pow((a.getBounds().getMinX() + a.getBounds().getWidth() / 2) - (b.getBounds().getMinX() + b.getBounds().getWidth() / 2), 2) +
                Math.pow((a.getBounds().getMinY() + a.getBounds().getHeight() / 2) - (b.getBounds().getMinY() + b.getBounds().getHeight() / 2), 2));
    }

    public abstract void render(Graphics2D g);

    public abstract void update();

}
