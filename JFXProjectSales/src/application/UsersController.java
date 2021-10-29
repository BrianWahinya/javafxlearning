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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class UsersController{
	@FXML AnchorPane anchorPaneModal;
	@FXML TableView<Users> tableUsers;
	@FXML TableColumn<Users, String> colUsername;
	@FXML TableColumn<Users, String> colEmail;
	@FXML TableColumn<Users, String> colAccessLevel;
	@FXML TableColumn<?, String> colUserTableActions;
	@FXML Button btnAddUsersModal;
	@FXML Button btnAddUsers;
	@FXML Button btnLoadUsers;
	@FXML Label lblUsersAvailable;
	@FXML TextField txtFieldUsername;
	@FXML TextField txtFieldEmail;
	@FXML Label lblError;
	
	DBObj dbObj;
	
	// Reset the default focus element
	public void resetFocus(){
		anchorPaneModal.requestFocus();
	}
	
	// Action method to display add modal
	public void actionAddUser(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("UserAdd.fxml", "Add User/s");		
	}
	
	public void actionGetAllUsers(ActionEvent event) throws SQLException {
		generateAllUsers();
	}
	
	public void generateAllUsers() throws SQLException {
		ObservableList<Users> list = getUserList();
		colUsername.setCellValueFactory(new PropertyValueFactory<Users, String>("username"));
		colEmail.setCellValueFactory(new PropertyValueFactory<Users, String>("email"));
		colAccessLevel.setCellValueFactory(new PropertyValueFactory<Users, String>("useraccess"));
		tableUsers.setItems(list);
	}
	
	private ObservableList<Users> getUserList() throws SQLException{
		ObservableList<Users> userList = FXCollections.observableArrayList();
		dbObj = new DBObj();
		try {
			//Establish connection
			Connection dbconn = dbObj.getConnection();
			String cstmt_getAllUsers = "{call GetAllUsers()}";
			CallableStatement cstmt = dbconn.prepareCall(cstmt_getAllUsers);
			cstmt.execute();
			ResultSet result = cstmt.getResultSet();
			Users users;
			int countResult = 0;
			while(result.next()) {
				countResult++;
				users = new Users(result.getString("username"), result.getString("email"), result.getString("useraccess"));
				userList.add(users);
			}
			lblUsersAvailable.setText(String.valueOf(countResult));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	// Register users (CREATE)
	public void actionRegisterUser(ActionEvent event) throws SQLException {
		String username = txtFieldUsername.getText();
		String email = txtFieldEmail.getText();
		if(!username.isEmpty() && !email.isEmpty()) {
			dbObj = new DBObj();
			try {
				//Establish connection
				Connection dbconn = dbObj.getConnection();
				String cstmt_registerUser = "{call RegisterUser(?, ?)}";
				CallableStatement cstmt = dbconn.prepareCall(cstmt_registerUser);
				cstmt.setString(1, username);
				cstmt.setString(2, email);
				cstmt.execute();
				ResultSet result = cstmt.getResultSet();
				while(result.next()) {
					lblError.setText(result.getString("message"));
				}
				txtFieldUsername.setText("");
				txtFieldEmail.setText("");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			CustomErrors customError = new CustomErrors();
			String error = customError.EmptyFields();
			lblError.setText(error);
		}
	}

}
