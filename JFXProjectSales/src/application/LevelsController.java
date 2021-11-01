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

public class LevelsController {
	@FXML AnchorPane anchorPaneModal;
	@FXML TableView<Levels> tableLevels;
	@FXML TableColumn<Levels, String> colLevelname;
	@FXML TableColumn<Levels, CheckBox> colLevelTableActions;
	@FXML Button btnAddLevelsModal;
	@FXML Button btnEditLevelModal;
	@FXML Button btnAddLevels;
	@FXML Button btnLoadLevels;
	@FXML Button btnDeleteLevels;
	@FXML Label lblLevelsAvailable;
	@FXML TextField txtFieldLevelName;
	@FXML Label lblError;
	
	List<String> checkedBoxes = new ArrayList<>();
	
	DBObj dbObj;
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
	
	public void actionAddLevelModalShow(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("LevelAdd.fxml", "Add Level");
	}
	
	public void actionGetAllLevels(ActionEvent event) throws SQLException {
		generateAllLevels();
	}
	
	public void generateAllLevels() throws SQLException {
		ObservableList<Levels> list = getLevelList();
		colLevelname.setCellValueFactory(new PropertyValueFactory<Levels, String>("levelname"));
		colLevelTableActions.setCellValueFactory(new PropertyValueFactory<Levels, CheckBox>("checkbox"));
		tableLevels.setItems(list);
	}
	
	public ObservableList<Levels> getLevelList() throws SQLException{
		ObservableList<Levels> levelList = FXCollections.observableArrayList();
		dbObj = new DBObj();
		try {
			//Establish connection
			Connection dbconn = dbObj.getConnection();
			String cstmt_getAllLevels = "{call GetAllLevels()}";
			CallableStatement cstmt = dbconn.prepareCall(cstmt_getAllLevels);
			cstmt.execute();
			ResultSet result = cstmt.getResultSet();
			Levels levels;
			int countResult = 0;
			while(result.next()) {
				countResult++;
				String lname = result.getString("levelname");
				CheckBox checkbox = new CheckBox();
				checkbox.setId(lname);
				// create a event handler
	            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
	  
	                public void handle(ActionEvent e)
	                {
	                    if (checkbox.isSelected()) {
	                    	checkedBoxes.add(lname);
	                    } else {
	                    	checkedBoxes.remove(new String(lname));
	                    }
	                }
	  
	            };
	            checkbox.setOnAction(event);
				levels = new Levels(result.getString("levelname"), checkbox);
				levelList.add(levels);
			}
			lblLevelsAvailable.setText(String.valueOf(countResult));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return levelList;
	}
	
	// Add Level (CREATE)
	public void actionAddLevel(ActionEvent event) throws SQLException {
		String levelname = txtFieldLevelName.getText();
		if(!levelname.isEmpty()) {
			dbObj = new DBObj();
			try {
				//Establish connection
				Connection dbconn = dbObj.getConnection();
				String cstmt_addLevel = "{call AddLevel(?, ?)}";
				CallableStatement cstmt = dbconn.prepareCall(cstmt_addLevel);
				cstmt.setString(1, levelname);
				cstmt.setString(2, "mimi");
				cstmt.execute();
				ResultSet result = cstmt.getResultSet();
				while(result.next()) {
					lblError.setText(result.getString("message"));
				}
				txtFieldLevelName.setText("");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			CustomErrors customError = new CustomErrors();
			String error = customError.EmptyFields();
			lblError.setText(error);
		}
	}
	
	// Edit level modal show
	public void actionEditLevelModalShow(ActionEvent event) throws IOException {
//		DashboardController dashControl = new DashboardController();
//		dashControl.openModalWindow("LevelAdd.fxml", "Add Level");
	}
	
	// Delete levels (DELETE)
	public void actionDeleteLevels(ActionEvent event) {
		// System.out.println("To be deleted: " + checkedBoxes);
		List<String> deleteMessages = new ArrayList<>();
		dbObj = new DBObj();
		try {
			//Establish connection
			Connection dbconn = dbObj.getConnection();
			for (String i : checkedBoxes) {
				  // System.out.println(i);
				  String cstmt_deleteLevel = "{call DeleteLevel(?)}";
				  CallableStatement cstmt = dbconn.prepareCall(cstmt_deleteLevel);
				  cstmt.setString(1, i);
				  cstmt.execute();
				  ResultSet result = cstmt.getResultSet();
				  while(result.next()) {
					  deleteMessages.add(result.getString("message"));
				  }
			}
			// System.out.println("Deleted messages: " + deleteMessages);
			generateAllLevels();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
