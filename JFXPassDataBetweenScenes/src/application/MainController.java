package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	@FXML Button btnSubmit;
	@FXML Button btnReset;
	@FXML TextField txtFdUsername;
	@FXML TextField txtFdId;
	@FXML TextField txtFdEmail;
	@FXML DatePicker datePickerDob;

	public void actionOnSubmit(ActionEvent event) throws IOException {
		Parent mainStage = FXMLLoader.load(getClass().getResource("DataDisplay.fxml"));
		Scene mainScene = new Scene(mainStage);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(mainScene);
		window.show();
	}
	
	public void actionOnReset(ActionEvent event) {
		txtFdUsername.setText("");
		txtFdId.setText("");
		txtFdEmail.setText("");
		datePickerDob.getEditor().clear();
	}
}
