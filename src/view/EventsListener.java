package view;

import models.Coordinates;

public interface EventsListener {
	void newTileEvent(Coordinates coordinates);
}
