package utils.config;

import rules.GameParameters;

public class ConfigReaderHardcoded implements ConfigReader {

	@Override
	public GameParameters readConfig() {
		GameParameters gp = new GameParameters(4, 4);
		return gp;
	}

}
