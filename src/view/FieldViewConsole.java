package view;

import models.GameField;

public class FieldViewConsole implements FieldView {

	GameField field;
	
	public FieldViewConsole(GameField field) {
		this.field = field;
	}
	
	@Override
	public void displayField() {
		for (int y = 0; y < field.getMaxY(); y++) {
			System.out.println("");
			for (int x = 0; x < field.getMaxX(); x++) {
				System.out.print(field.getTile(x, y).getValue()+" ");
			}
			
		}
		System.out.println("");
	}

}
