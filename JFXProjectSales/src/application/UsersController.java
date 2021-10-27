package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class UsersController {
	@FXML AnchorPane anchorPaneModal;
	
	public void actionAddUser(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("UserAdd.fxml", "Add User/s");		
	}
	
	public void actionGetAllUsers(ActionEvent event) {
		
	}
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
}
