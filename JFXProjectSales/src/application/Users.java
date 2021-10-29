package application;

import javafx.scene.control.CheckBox;

public class Users {
	private String username;
	private String email;
	private String useraccess;
	private CheckBox checkbox;
	
	public Users(String username, String email, String useraccess, CheckBox checkbox) {
		this.username = username;
		this.email = email;
		this.useraccess = useraccess;
		this.checkbox = checkbox;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUseraccess() {
		return useraccess;
	}
	
	public CheckBox getCheckbox() {
		return checkbox;
	}

}
