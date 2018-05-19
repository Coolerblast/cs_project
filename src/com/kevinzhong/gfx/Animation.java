package com.kevinzhong.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

/***
 * Creates an animation by using a spritesheet with the frames layed consecutively, and shifting the displayed image based on the frame count
 */

public class Animation {
    private int speed, index, count;
    private BufferedImage[] frames;
    private BufferedImage currentFrame;

    /***
     * Sets the spritesheet, frame number and total amount, and subimage of the animation
     * @param ss spritesheet
     * @param x x location of first pixel
     * @param y y location of first pixel
     * @param w width of frame
     * @param h height of frame
     * @param frames number of frames
     */
    public Animation(SpriteSheet ss, int x, int y, int w, int h, int frames) {
        this.frames = new BufferedImage[frames];
        for (int i = 0; i < this.frames.length; i++)
            this.frames[i] = ss.crop(x + w * i, y, w, h);
    }

    /***
     * Moves to the next frame of the animation at a set speed.
     * */
    public void runAnimation() {
        index++;
        if (index > 8 - speed) {
            index = 0;
            nextFrame();
        }
    }

    /***
     * Increases the frame number by 1, and loops it back to 0 if it's on the final frame.
     */
    private void nextFrame() {
        count++;
        if (count >= frames.length)
            count = 1;
        currentFrame = frames[count];
    }

    /***
     * Updates the frame based on the frame number.
     * @param current desired frame number
     */
    public void setCurrentFrame(int current) {
        this.count = current;
        currentFrame = frames[current];
    }

    /**
     * Gets the index of the current frame
     * @return returns the current index
     */
    public int getCurrent() {
        return count;
    }

    /**
     * Returns the length of the frames
     * @return
     */
    public int getMaxFrames() {
        return frames.length;
    }

    /***
     *  Displays the animation frame
     * @param g Graphics
     * @param midX middle x location of frame
     * @param botY lower y location of frame
     * @param xScale x scale of frame
     * @param yScale y scale of frame
     * @param flipped flips frame if true
     */
    public void drawAnimation(Graphics2D g, int midX, int botY, int xScale, int yScale, boolean flipped) {
        if (flipped)
            g.drawImage(currentFrame, midX - currentFrame.getWidth()/(2 / xScale), botY - currentFrame.getHeight(),
                    currentFrame.getWidth() * xScale, currentFrame.getHeight() * yScale, null);
        else
            g.drawImage(currentFrame, (midX - currentFrame.getWidth()/(2 / xScale)) + currentFrame.getWidth() * xScale, botY - currentFrame.getHeight(),
                    currentFrame.getWidth() * -xScale, currentFrame.getHeight() * yScale, null);
    }

    /***
     * Sets the speed of the animation.
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
