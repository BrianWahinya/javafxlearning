package application;

import javafx.scene.control.CheckBox;

public class Books {
	private String filename;
	private String filetype;
	private String filesubject;
	private String filelevel;
	private CheckBox checkbox;
	
	public Books(String filename, String filetype, String filesubject, String filelevel, CheckBox checkbox) {
		this.filename = filename;
		this.filetype = filetype;
		this.filesubject = filesubject;
		this.filelevel = filelevel;
		this.checkbox = checkbox;
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
}
