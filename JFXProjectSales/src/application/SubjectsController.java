package application;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class SubjectsController {
	@FXML AnchorPane anchorPaneModal;
	@FXML TableView<Subjects> tableSubjects;
	@FXML TableColumn<Subjects, String> colSubjectname;
	@FXML TableColumn<?, String> colSubjectTableActions;
	@FXML Button btnAddSubjectsModal;
	@FXML Button btnAddSubjects;
	@FXML Button btnLoadSubjects;
	@FXML Label lblSubjectsAvailable;
	
	DBObj dbObj;
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
	
	public void actionAddSubject(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("SubjectAdd.fxml", "Add Subject");		
	}
	
	public void actionGetAllSubjects(ActionEvent event) throws SQLException {
		ObservableList<Subjects> list = getSubjectList();
		colSubjectname.setCellValueFactory(new PropertyValueFactory<Subjects, String>("subjectname"));
		tableSubjects.setItems(list);
	}
	
	public ObservableList<Subjects> getSubjectList() throws SQLException{
		ObservableList<Subjects> subjectList = FXCollections.observableArrayList();
		dbObj = new DBObj();
		try {
			//Establish connection
			Connection dbconn = dbObj.getConnection();
			String cstmt_getAllSubjects = "{call GetAllSubjects()}";
			CallableStatement cstmt = dbconn.prepareCall(cstmt_getAllSubjects);
			cstmt.execute();
			ResultSet result = cstmt.getResultSet();
			Subjects subjects;
			int countResult = 0;
			while(result.next()) {
				countResult++;
				subjects = new Subjects(result.getString("subjectname"));
				subjectList.add(subjects);
			}
			lblSubjectsAvailable.setText(String.valueOf(countResult));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return subjectList;
	}
}
