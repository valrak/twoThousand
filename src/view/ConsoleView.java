package view;

import rules.Field;

public class ConsoleView implements FieldView {

	@Override
	public void displayField(Field field) {
		for (int y = 0; y < field.getMaxY(); y++) {
			System.out.println("");
			for (int x = 0; x < field.getMaxX(); x++) {
				System.out.print(field.getTile(x, y).getValue()+" ");
			}
			
		}
		System.out.println("");
	}

}
