package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SubjectsController {
	@FXML AnchorPane anchorPaneModal;
	
	public void actionAddSubject(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("SubjectAdd.fxml", "Add Subject");		
	}
	
	public void actionGetAllSubjects(ActionEvent event) {
		
	}
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
}
