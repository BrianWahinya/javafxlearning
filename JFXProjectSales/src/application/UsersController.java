package application;

import java.io.IOException;

import javafx.event.ActionEvent;

public class UsersController {
	
	public void actionAddUser(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("UserAdd.fxml", "Add User/s");		
	}
	
}
