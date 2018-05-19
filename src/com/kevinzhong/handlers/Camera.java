package com.kevinzhong.handlers;

import com.kevinzhong.entity.Entity;
import com.kevinzhong.state.GameState;
import com.kevinzhong.tile.Tile;

/**
 *
 * Camera class depicts the relevant parts of action on the screen. It pans to lock onto the player in order for the player model to remain in view.
 */
public class Camera {

	private float x, y;
	private int width, height;

	/**
	 * Default Constructor
	 * @param x x position of camera
	 * @param y	y position of camera
	 * @param width width of view
	 * @param height height of view
	 */
	public Camera(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Follows the player
	 * @param player desired player to be followed
	 */
	public void update(Entity player) {

		if (x >= 0 && x <= GameState.getMaxX() * Tile.getTileSize() - width)
			x = (float) (player.getBounds().getCenterX() - width / 2);

		if (y >= 0 - height && y <= GameState.getMaxY() * Tile.getTileSize())
			y = (float) (player.getBounds().getCenterY() - height * .6);

		if (x < 0)
			x = 0;
		else if (x > GameState.getMaxX() * Tile.getTileSize() - width) {
			x = GameState.getMaxX() * Tile.getTileSize() - width;
		}
		
		if( y < 0 )
			y = 0;
		else if (y > GameState.getMaxY() * Tile.getTileSize() - height)
			y = GameState.getMaxY() * Tile.getTileSize() - height;

		//System.out.println(x + " " + y + "  " + GameState.getMaxX() + " " + GameState.getMaxY());
	}

	/**
	 * sets x-value of camera location.
	 * @param x desired x-value
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * sets the y-value of the camera location.
	 * @param y desired y-value
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * returns x-value
	 * @return x
	 */
	public float getX() {
		return x;
	}

	/**
	 * returns y-value
	 * @return y
	 */
	public float getY() {
		return y;
	}
}
