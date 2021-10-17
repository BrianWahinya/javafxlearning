package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {

	@FXML Label txtLabel;
	@FXML Button clickBtn;
	
	public void actionOnClickButton(ActionEvent event) {
		txtLabel.setText("Hello Mercy");
	}
}

