package application;

import java.io.IOException;

import javafx.event.ActionEvent;

public class SubjectsController {
	
	public void actionAddSubject(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("SubjectAdd.fxml", "Add Subject");		
	}
	
	public void actionGetAllSubjects(ActionEvent event) {
		
	}
}
