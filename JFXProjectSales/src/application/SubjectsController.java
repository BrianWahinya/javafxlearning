package application;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class SubjectsController {
	@FXML AnchorPane anchorPaneModal;
	@FXML TableView<Subjects> tableSubjects;
	@FXML TableColumn<Subjects, String> colSubjectname;
	@FXML TableColumn<Subjects, CheckBox> colSubjectTableActions;
	@FXML Button btnAddSubjectsModal;
	@FXML Button btnAddSubjects;
	@FXML Button btnLoadSubjects;
	@FXML Button btnDeleteSubjects;
	@FXML Label lblSubjectsAvailable;
	@FXML TextField txtFieldSubjectName;
	@FXML Label lblError;
	
	List<String> checkedBoxes = new ArrayList<>();
	
	DBObj dbObj;
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
	
	public void actionAddSubjectModalShow(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("SubjectAdd.fxml", "Add Subject");		
	}
	
	public void actionGetAllSubjects(ActionEvent event) throws SQLException {
		generateAllSubjects();
	}
	
	public void generateAllSubjects() throws SQLException {
		ObservableList<Subjects> list = getSubjectList();
		colSubjectname.setCellValueFactory(new PropertyValueFactory<Subjects, String>("subjectname"));
		colSubjectTableActions.setCellValueFactory(new PropertyValueFactory<Subjects, CheckBox>("checkbox"));
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
				String sname = result.getString("subjectname");
				CheckBox checkbox = new CheckBox();
				checkbox.setId(sname);
				// create a event handler
	            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
	  
	                public void handle(ActionEvent e)
	                {
	                    if (checkbox.isSelected()) {
	                    	checkedBoxes.add(sname);
	                    } else {
	                    	checkedBoxes.remove(new String(sname));
	                    }
	                }
	  
	            };
	            checkbox.setOnAction(event);
				subjects = new Subjects(result.getString("subjectname"), checkbox);
				subjectList.add(subjects);
			}
			lblSubjectsAvailable.setText(String.valueOf(countResult));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return subjectList;
	}
	
	// Add Subject (CREATE)
	public void actionAddSubject(ActionEvent event) throws SQLException {
		String subjectname = txtFieldSubjectName.getText();
		if(!subjectname.isEmpty()) {
			dbObj = new DBObj();
			try {
				//Establish connection
				Connection dbconn = dbObj.getConnection();
				String cstmt_addSubject = "{call AddSubject(?, ?)}";
				CallableStatement cstmt = dbconn.prepareCall(cstmt_addSubject);
				cstmt.setString(1, subjectname);
				cstmt.setString(2, "mimi");
				cstmt.execute();
				ResultSet result = cstmt.getResultSet();
				while(result.next()) {
					lblError.setText(result.getString("message"));
				}
				txtFieldSubjectName.setText("");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			CustomErrors customError = new CustomErrors();
			String error = customError.EmptyFields();
			lblError.setText(error);
		}
	}
	
	// Delete users (DELETE)
	public void actionDeleteSubjects(ActionEvent event) {
		// System.out.println("To be deleted: " + checkedBoxes);
		List<String> deleteMessages = new ArrayList<>();
		dbObj = new DBObj();
		try {
			//Establish connection
			Connection dbconn = dbObj.getConnection();
			for (String i : checkedBoxes) {
				  // System.out.println(i);
				  String cstmt_deleteSubject = "{call DeleteSubject(?)}";
				  CallableStatement cstmt = dbconn.prepareCall(cstmt_deleteSubject);
				  cstmt.setString(1, i);
				  cstmt.execute();
				  ResultSet result = cstmt.getResultSet();
				  while(result.next()) {
					  deleteMessages.add(result.getString("message"));
				  }
			}
			// System.out.println("Deleted messages: " + deleteMessages);
			generateAllSubjects();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
