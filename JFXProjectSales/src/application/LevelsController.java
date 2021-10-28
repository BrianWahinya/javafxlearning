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

public class LevelsController {
	@FXML AnchorPane anchorPaneModal;
	@FXML TableView<Levels> tableLevels;
	@FXML TableColumn<Levels, String> colLevelname;
	@FXML TableColumn<?, String> colLevelTableActions;
	@FXML Button btnAddLevelsModal;
	@FXML Button btnAddLevels;
	@FXML Button btnLoadLevels;
	@FXML Label lblLevelsAvailable;
	
	DBObj dbObj;
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
	
	public void actionAddLevel(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("LevelAdd.fxml", "Add Level");
	}
	
	public void actionGetAllLevels(ActionEvent event) throws SQLException {
		ObservableList<Levels> list = getLevelList();
		colLevelname.setCellValueFactory(new PropertyValueFactory<Levels, String>("levelname"));
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
				levels = new Levels(result.getString("levelname"));
				levelList.add(levels);
			}
			lblLevelsAvailable.setText(String.valueOf(countResult));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return levelList;
	}
	
}
