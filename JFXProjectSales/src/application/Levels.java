package application;

import javafx.scene.control.CheckBox;

public class Levels {
	private String levelname;
	private CheckBox checkbox;
	
	public Levels(String levelname, CheckBox checkbox) {
		this.levelname = levelname;
		this.checkbox = checkbox;
	}
	
	public String getLevelname() {
		return levelname;
	}
	
	public CheckBox getCheckbox() {
		return checkbox;
	}
}
