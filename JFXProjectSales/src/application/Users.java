package application;

public class Users {
	private String username;
	private String email;
	private String useraccess;
	
	public Users(String username, String email, String useraccess) {
		this.username = username;
		this.email = email;
		this.useraccess = useraccess;
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

}
