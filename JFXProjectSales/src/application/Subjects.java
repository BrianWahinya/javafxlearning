package application;

import javafx.scene.control.CheckBox;

public class Subjects {
	private String subjectname;
	private CheckBox checkbox;
	
	public Subjects(String subjectname, CheckBox checkbox) {
		this.subjectname = subjectname;
		this.checkbox = checkbox;
	}
	
	public String getSubjectname() {
		return subjectname;
	}
	
	public CheckBox getCheckbox() {
		return checkbox;
	}
}
