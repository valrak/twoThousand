package view.swingComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import models.Coordinates;
import models.GameField;
import models.Tile;

public class GameFieldPanel extends JPanel {

	private int PADDING = 4;
	
	private GameField field;
	
	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}

	public GameFieldPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (field != null) {
			drawGameField(field, g);
		}
	}
	
	private void drawGameField(GameField field, Graphics g) {
		LinkedHashMap<Coordinates, Tile> tiles = field.getTiles();
		for (Coordinates key : tiles.keySet()) {
			Tile tile = tiles.get(key);
			drawTileWithColor(getBounds(), g, key, tile.getValue());
		}
	}

	private void drawTileWithColor(Rectangle windowSize, Graphics g, Coordinates coord, int value) {
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2d.setRenderingHints(rh);
		int tileSize = (int) ((windowSize.getHeight() - PADDING) / 4);
		int x = coord.getX() * tileSize;
		int y = coord.getY() * tileSize;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y, tileSize, tileSize);
		g2d.setColor(Color.RED);
		g2d.drawRect(x, y, tileSize, tileSize);
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(value), x + tileSize/2, y + tileSize/2);
	}
}