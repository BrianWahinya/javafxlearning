package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Books {
	private String filename;
	private String filetype;
	private String filesubject;
	private String filelevel;
	private CheckBox checkbox;
	private Button btnview;
	
	public Books(String filename, String filetype, String filesubject, String filelevel, CheckBox checkbox, Button btnview) {
		this.filename = filename;
		this.filetype = filetype;
		this.filesubject = filesubject;
		this.filelevel = filelevel;
		this.checkbox = checkbox;
		this.btnview = btnview;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getFiletype() {
		return filetype;
	}
	
	public String getFilesubject() {
		return filesubject;
	}
	
	public String getFilelevel() {
		return filelevel;
	}
	
	public CheckBox getCheckbox() {
		return checkbox;
	}
	
	public Button getBtnview() {
		return btnview;
	}

}
