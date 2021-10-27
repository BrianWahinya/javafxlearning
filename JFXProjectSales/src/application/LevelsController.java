package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class LevelsController {
	@FXML AnchorPane anchorPaneModal;
	
	public void actionAddLevel(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("LevelAdd.fxml", "Add Level");
	}
	
	public void actionGetAllLevels(ActionEvent event) {
		
	}
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
	
}
