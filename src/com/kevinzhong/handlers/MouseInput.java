package com.kevinzhong.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.kevinzhong.entity.Player;
import com.kevinzhong.main.GamePanel;
import com.kevinzhong.state.GameState;
import com.kevinzhong.state.State;

public class MouseInput implements MouseListener {

	private Player player;
	private GameState gameState;

	public MouseInput(Player p, GameState gs) {
		player = p;
		gameState = gs;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (State.getState() == gameState) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (!player.getPlacingBlocks())
					player.setBreakingBlocks(true);
				player.selectInventorySlot(GamePanel.getMainJFrame().getMousePosition());
			}

			if (e.getButton() == MouseEvent.BUTTON3) {
				if (!player.getBreakingBlocks())
					player.setPlacingBlocks(true);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (State.getState() == gameState) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				player.setBreakingBlocks(false);
			}

			if (e.getButton() == MouseEvent.BUTTON3) {
				player.setPlacingBlocks(false);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}
