package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainController {
	@FXML Button btnGoToLogin;
	@FXML Button btnGoToRegister;
	Common common = new Common();
	
	public void actionGoToLogin(ActionEvent event) throws IOException {
		
		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
		Parent login = loginLoader.load();
		
		Stage loginStage = new Stage();
		loginStage.getIcons().add(common.StageIcon());
		loginStage.setTitle("Books App (Login)");
		loginStage.setScene(new Scene(login));
		
		LoginController loginController = (LoginController) loginLoader.getController();
		loginController.resetFocus();
		
		loginStage.show();
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
	
	public void actionGoToRegister(ActionEvent event) {
		
	}
}
