package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {
	@FXML AnchorPane anchorPane;
	@FXML TextField txtFieldUsernameOrEmail;
	@FXML PasswordField pwdField;
	@FXML Button btnLogin;
	@FXML Button btnGoToRegister;
	@FXML Button btnBackToMain;
	@FXML Label lblLoginErrors;
	
	// Register Button Action
	public void actionGoToRegister(ActionEvent event) {
		lblLoginErrors.setText("Btn Register clicked");
	}

	// Back Button Action
	public void actionBackToMain(ActionEvent event) throws IOException {
		lblLoginErrors.setText("Btn Back clicked");
		Parent mainStage = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainStage);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(mainScene);
		window.show();
	}
	
	// Login Button action
	public void actionLogin(ActionEvent event) throws SQLException {
		// lblLoginErrors.setText("Btn Login clicked");
		Window owner = txtFieldUsernameOrEmail.getScene().getWindow();
		
		// Throw and alert error in case any input box is empty
		if(txtFieldUsernameOrEmail.getText().isEmpty() || pwdField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Please enter all fields", "Form error");
			return;
		}
		
		// Retrieve the user input text
		String uNameEmail = txtFieldUsernameOrEmail.getText();
		String pwd = pwdField.getText();
		
		DBObj dbObj = new DBObj();
		boolean flag = dbObj.validateUser(uNameEmail, pwd);
		if(!flag) {
			// Throw and alert error in case any input box is empty
			String error = "Please enter correct username/email or password";
			lblLoginErrors.setText(error);
			infoBox(error, null, "Failed");
		}else {
			infoBox("Login successful!", null, "Success");
			try {
				FXMLLoader dashLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
				Parent root = dashLoader.load();
				
				Stage dashStage = new Stage();
				dashStage.setTitle("Dashboard");
				dashStage.setScene(new Scene(root));
				
				DashboardController dashController = (DashboardController) dashLoader.getController();
				dashController.setUserName(uNameEmail);
				
				dashStage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
			}catch(Exception e) {
				
			}
		}
		
	}
	
	public static void infoBox(String infoMsg, String headerText, String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(infoMsg);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}
	
	// Alert in case of errors
	public static void showAlert(Alert.AlertType alertType, Window owner, String msg, String title) {
		Alert alert = new Alert(alertType);
		alert.setContentText(msg);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.initOwner(owner);
		alert.show();
	}
	
	// Default Focus will be to the anchorPane and not textField
	public void resetFocus() {
		anchorPane.requestFocus();
	}
}
