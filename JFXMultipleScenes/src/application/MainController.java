package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController {
	@FXML Button btnNext;
	@FXML Button btnCancel;
	@FXML Text txtBtnClicked;
	
	public void actionOnNext(ActionEvent event) throws IOException, InterruptedException {
		txtBtnClicked.setText("Button Next Clicked");
		
		// Thread.sleep(6000);
		Parent secondStage = FXMLLoader.load(getClass().getResource("SecondScene.fxml"));
		Scene secondScene = new Scene(secondStage);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(secondScene);
		window.show();
		
	}
	
	public void actionOnCancel(ActionEvent event) {
		txtBtnClicked.setText("Button Cancel Clicked");
	}
}
