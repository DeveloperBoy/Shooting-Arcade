package me.x1machinemaker1x.shootinggallery.api;

public enum Target {
	
	ONE("One"),
	TWO("Two"),
	THREE("Three");
	
	private String configname;
	
	Target(String configname) {
		this.configname = configname;
	}
	
	public String getConfigName() {
		return configname;
	}

}
