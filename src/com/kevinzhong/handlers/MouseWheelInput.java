package com.kevinzhong.handlers;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.kevinzhong.entity.Player;
import com.kevinzhong.state.GameState;
import com.kevinzhong.state.State;

public class MouseWheelInput implements MouseWheelListener {

	private Player player;
	private GameState gameState;

	public MouseWheelInput(Player p, GameState gs) {
		player = p;
		gameState = gs;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (State.getState() == gameState) {
			// away
			if (e.getWheelRotation() < 0) {
				player.scrollHotbarSlot(1);
			}
			// towards
			else if (e.getWheelRotation() > 0) {
				player.scrollHotbarSlot(-1);
			}
		}
	}

}
