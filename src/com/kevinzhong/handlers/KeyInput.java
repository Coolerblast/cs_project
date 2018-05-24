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

			if (e.getKeyCode() == KeyEvent.VK_Z) {
				player.attack();
			}

			if (e.getKeyCode() == KeyEvent.VK_Q) {
				player.setHealth(100);
			}

			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				player.setInventoryIsOpen(!player.isInventoryIsOpen());
				System.out.println(player.isInventoryIsOpen());
			}

			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
				System.exit(0);
			if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH)
					gameState.save();

			if (e.getKeyCode() == KeyEvent.VK_1) player.setSelectedHotbarSlot(0);
			if (e.getKeyCode() == KeyEvent.VK_2) player.setSelectedHotbarSlot(1);
			if (e.getKeyCode() == KeyEvent.VK_3) player.setSelectedHotbarSlot(2);
			if (e.getKeyCode() == KeyEvent.VK_4) player.setSelectedHotbarSlot(3);
			if (e.getKeyCode() == KeyEvent.VK_5) player.setSelectedHotbarSlot(4);
			if (e.getKeyCode() == KeyEvent.VK_6) player.setSelectedHotbarSlot(5);
			if (e.getKeyCode() == KeyEvent.VK_7) player.setSelectedHotbarSlot(6);
			if (e.getKeyCode() == KeyEvent.VK_8) player.setSelectedHotbarSlot(7);
			if (e.getKeyCode() == KeyEvent.VK_9) player.setSelectedHotbarSlot(8);
			if (e.getKeyCode() == KeyEvent.VK_0) player.setSelectedHotbarSlot(9);
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