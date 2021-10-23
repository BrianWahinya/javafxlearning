package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
	@FXML private Label lblUsername;
	
	public void setUserName(String username) {
		lblUsername.setText(username);
	}

}
