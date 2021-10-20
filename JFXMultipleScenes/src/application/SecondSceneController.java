package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SecondSceneController {
	@FXML Button btnBackHome;
	
	public void actionGoBackHome(ActionEvent event) throws IOException {
		Parent mainStage = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene (mainStage);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(mainScene);
		window.show();
	}

}
