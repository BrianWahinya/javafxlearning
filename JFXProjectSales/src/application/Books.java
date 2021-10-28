package application;

public class Books {
	private String filename;
	private String filetype;
	private String filesubject;
	private String filelevel;
	
	public Books(String filename, String filetype, String filesubject, String filelevel) {
		this.filename = filename;
		this.filetype = filetype;
		this.filesubject = filesubject;
		this.filelevel = filelevel;
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
}
