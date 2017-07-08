package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;
import rules.Rules;
import view.swingComponents.GameFieldPanel;

public class FieldViewSwing implements FieldView, EventsListener, ActionListener {

	Rules gameRules;
	JFrame frame;
	GameFieldPanel gameFieldPanel;
	JPanel container;
	JPanel controls;
	JLabel scoreLabel;
	JButton newGameButton;

	FieldViewSwing(Rules gameRules) {
		this.gameRules = gameRules;
		gameRules.addListener(this);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initialize(gameRules.getField());
			}
		});
	}

	@Override
	public void displayField() {
		if (frame != null) {
			frame.repaint();
			frame.revalidate();
		}
	}

	public void initialize(GameField field) {
		frame = new JFrame("Two thousand");
		container = new JPanel();
		controls = new JPanel();
		gameFieldPanel = new GameFieldPanel();
		try {
			frame.setIconImage(ImageIO.read(new File("resources/icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoreLabel = new JLabel("Score: " + gameRules.getScore(), JLabel.RIGHT);
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(this);
		newGameButton.setActionCommand("newGame");
		newGameButton.setFocusable(false);
		gameFieldPanel.setField(field);
		
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		controls.add(newGameButton);
		controls.add(scoreLabel);
		gameFieldPanel.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case 37:
					playerMoveTilesEvent(Direction.LEFT);
					break;
				case 38:
					playerMoveTilesEvent(Direction.UP);
					break;
				case 39:
					playerMoveTilesEvent(Direction.RIGHT);
					break;
				case 40:
					playerMoveTilesEvent(Direction.DOWN);
					break;
				}
			}
		});

		frame.add(container);
		container.add(gameFieldPanel);
		container.add(controls);
		gameFieldPanel.setFocusable(true);
		gameFieldPanel.requestFocusInWindow();
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update() {
		String scoreText = "";
		if (gameRules.isLost()) {
			scoreText = "You Lost! Final score: " + gameRules.getScore();
		}
		else {
			scoreText = "Score: " + gameRules.getScore();
		}
		scoreLabel.setText(scoreText);
	}

	public void playerMoveTilesEvent(Direction direction) {
		gameRules.playerMove(direction);
		displayField();
		update();
	}

	@Override
	public void newTileEvent(Coordinates coord) {
		gameFieldPanel.registerNewTile(coord);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("newGame".equals(e.getActionCommand())) {
			gameRules.newGame();
			update();
			displayField();
		}
	}
}
