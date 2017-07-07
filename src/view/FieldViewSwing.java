package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import constants.Constants.Direction;
import models.GameField;
import rules.Rules;
import view.swingComponents.GameFieldPanel;

public class FieldViewSwing implements FieldView {

	Rules gameRules;
	JFrame frame;
	GameFieldPanel panel;
	
	FieldViewSwing(Rules gameRules) {
		this.gameRules = gameRules;
		initialize(gameRules.getField());
	}
	
	@Override
	public void displayField() {
		frame.repaint();
		frame.revalidate();
	}
	
	public void initialize(GameField field) {
//		SwingUtilities.invokeLater(new Runnable() {
//		public void run() {
//			createAndShowGUI();
//		}
//	});
		frame = new JFrame("Two thousand");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new GameFieldPanel();
		panel.setField(field);
		panel.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }
			
			@Override
			public void keyReleased(KeyEvent e) { }
			
			@Override
		    public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
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
		
		frame.add(panel);
		panel.setFocusable(true);
        panel.requestFocusInWindow();
        frame.pack();
        frame.setVisible(true);
		
		System.out.print("isEventDispatchThread()"+SwingUtilities.isEventDispatchThread());
	}
	
	public void playerMoveTilesEvent(Direction direction) {
		gameRules.playerMove(direction);
		displayField();
		
	}
}
