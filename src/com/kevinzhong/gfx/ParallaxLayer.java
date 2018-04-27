package com.kevinzhong.gfx;

import com.kevinzhong.handlers.Camera;
import com.kevinzhong.main.Game;
import com.kevinzhong.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParallaxLayer {
    private BufferedImage image;
    private int x, y, width, height, dx, dy, gap;
    private boolean left, right, up, down;

    public ParallaxLayer(BufferedImage image, int dx, int dy, int gap) {
        /*******************************************
         * Make dx and dy based on player movement *
         *******************************************/
        this.image = image;
        this.dx = dx;
        this.dy = dy;
        this.gap = gap;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        this.x = (GamePanel.getMainJFrame().getWidth() - this.width) / 2;
        this.y = 1000;

    }

    public ParallaxLayer(BufferedImage image, int dx, int dy) {
        this(image, dx, dy, 0);
    }

    public void setLeft() {
        left = true;
        right = false;
    }

    public void setRight() {
        right = true;
        left = false;
    }

    public void setUp() {
        up = true;
        down = false;
    }

    public void setDown() {
        down = true;
        up = false;
    }

    public void stopHorizontal() {
        left = right = false;
    }

    public void stopVertical() {
        up = down = false;
    }

    public void update() {
        if (left) x = (x + dx) % (width + gap);
        else if (right) x = (x - dx) % (width + gap);
        if(up) y = (y - dy) % (height + gap);
        else if(down) y = (y + dy) % (height + gap);

        if(y < 0)
            y = 0;

        if(y > height - GamePanel.getMainJFrame().getHeight())
            y = height - GamePanel.getMainJFrame().getHeight();
    }

    public void render(Graphics2D g, Camera cam) {
        g.translate(cam.getX(), cam.getY());
        if (x == 0)
            g.drawImage(image, 0, 0, GamePanel.getMainJFrame().getWidth(), height,
                    0, y, GamePanel.getMainJFrame().getWidth(), y + height, null);

        else if (x > 0 && x < GamePanel.getMainJFrame().getWidth()) {
            g.drawImage(image, x, 0, GamePanel.getMainJFrame().getWidth(), height,
                    0, y, GamePanel.getMainJFrame().getWidth() - x, y + height, null);
            g.drawImage(image, 0, 0, x, height,
                    width - x, y, width, y + height, null);
        }
        else if( x >= GamePanel.getMainJFrame().getWidth())
            g.drawImage(image, 0, 0, GamePanel.getMainJFrame().getWidth(), height,
                    width - x, y, width - x + GamePanel.getMainJFrame().getWidth(), y + height, null);

        else if(x < 0 && x >= GamePanel.getMainJFrame().getWidth() - width)
            g.drawImage(image, 0, 0, GamePanel.getMainJFrame().getWidth(), height,
                    -x, y, GamePanel.getMainJFrame().getWidth() - x, y + height, null);

        else if(x < GamePanel.getMainJFrame().getWidth() - width) {
            g.drawImage(image, 0, 0, width + x, height,
                    -x, y, width, y + height, null);
            g.drawImage(image, gap + width + x, 0, gap + GamePanel.getMainJFrame().getWidth(), height,
                    0, y, GamePanel.getMainJFrame().getWidth() - width - x, y + height, null);
        }
        g.translate(-cam.getX(), -cam.getY());
    }
}
