package application;

import java.io.IOException;

import javafx.event.ActionEvent;

public class LevelsControlller {
	
	public void actionAddLevel(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("LevelAdd.fxml", "Add Level");		
	}
	
	public void actionGetAllLevels(ActionEvent event) {
		
	}
}
