package com.kevinzhong.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.kevinzhong.entity.Entity;
import com.kevinzhong.entity.Player;
import com.kevinzhong.main.GamePanel;
import com.kevinzhong.state.GameState;
import com.kevinzhong.state.State;
import com.kevinzhong.tile.Tile;

public class KeyInput implements KeyListener {

	private Player player;
	private GameState gameState;

	public KeyInput(Player p, GameState gs) {
		player = p;
		gameState = gs;
	}

	public void keyPressed(KeyEvent e) {
		if (State.getState() == gameState) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP
					|| e.getKeyCode() == KeyEvent.VK_W) {
				player.jump();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.setLeftAccel(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				player.setRightAccel(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_Q) {
				player.setX(500 * Tile.getTileSize());
				player.setY(195 * Tile.getTileSize());
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				System.exit(0);
			if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH)
					gameState.save();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (State.getState() == gameState) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.setLeftAccel(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				player.setRightAccel(false);
			}
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}