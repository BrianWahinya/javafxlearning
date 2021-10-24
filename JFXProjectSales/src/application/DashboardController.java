package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {
	@FXML private Label lblUsername;
	@FXML Button btnUsers;
	@FXML Button btnLevels;
	@FXML Button btnSubjects;
	@FXML Button btnBooks;
	@FXML Button btnLogout;
	
	public void setUserName(String username) {
		lblUsername.setText(username);
	}
	
	public void actionGetAllUsers(ActionEvent event) {
		
	}
	
	public void actionGetAllLevels(ActionEvent event) {
		
	}
	
	public void actionGetAllSubjects(ActionEvent event) {
		
	}
	
	public void actionGetAllBooks(ActionEvent event) {
		
	}
	
	public void actionLogout(ActionEvent event) {
		
	}

}
