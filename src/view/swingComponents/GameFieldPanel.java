package view.swingComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JPanel;

import models.Coordinates;
import models.GameField;
import models.Tile;

public class GameFieldPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int PADDING = 4;
	
	private GameField field;
	private ArrayList<Coordinates> newTiles = new ArrayList<>();
	
	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}

	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (field != null) {
			drawGameField(field, g);
		}
	}
	
	public void registerNewTile(Coordinates coord) {
		newTiles.add(coord);
	}
	
	private void drawGameField(GameField field, Graphics g) {
		Rectangle windowSize = getBounds();
		int tileSize = (int) ((windowSize.getHeight() - PADDING) / 4);
		drawGrid(tileSize, field, g);
		LinkedHashMap<Coordinates, Tile> tiles = field.getTiles();
		for (Coordinates key : tiles.keySet()) {
			Color color = Color.BLACK;
			if (newTiles.contains(key)) {
				color = Color.DARK_GRAY;
				newTiles.remove(key);
			}
			Tile tile = tiles.get(key);
			if (!tile.isZero()) {
				drawTileWithColor(tileSize, g, key, tile.getValue(), color);
			}
		}
	}

	private void drawGrid(int tileSize, GameField field, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, tileSize * field.getMaxX(), tileSize * field.getMaxY());
		g.setColor(Color.DARK_GRAY);
		for (int y = 0; y < field.getMaxY(); y++) {
			for (int x = 0; x < field.getMaxX(); x++) {
				g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
			}
		}
		
	}
	private void drawTileWithColor(int tileSize, Graphics g, Coordinates coord, int value, Color color) {
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2d.setRenderingHints(rh);
		int x = coord.getX() * tileSize;
		int y = coord.getY() * tileSize;
		g2d.setColor(color);
		g2d.fillRect(x, y, tileSize, tileSize);
		g2d.setColor(Color.RED);
		g2d.drawRect(x, y, tileSize, tileSize);
        g2d.setColor(Color.WHITE);
        // calculate font size and position
        int xratio = 3;
        int fontSizeRatio = 2;
        if (value >= 10) {
        	xratio = 2;
        }
        if (value >= 100) {
        	fontSizeRatio = 3;
        	xratio = 1;
        }
        Font font = g.getFont();
        int fontSize = tileSize / fontSizeRatio;
        font = new Font(font.getName(), Font.BOLD, fontSize);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(value), x + tileSize/2 - fontSize/xratio, y + tileSize/2 + fontSize/3);
	}
}