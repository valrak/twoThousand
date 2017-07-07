package view;

import rules.Rules;
import rules.RulesStandard;

public class TwoThousand {
	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				createAndShowGUI();
//			}
//		});
		Rules gameRules = new RulesStandard();
		FieldView view = new FieldViewSwing(gameRules);
		
		view.displayField();
	}
}
