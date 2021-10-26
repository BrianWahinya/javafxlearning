package application;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlLoader {
	private Pane view;
	
	public Pane getPage(String fileName) {
		try {
			URL fileUrl = DashboardController.class.getResource(fileName + ".fxml");
			if(fileUrl != null ) {
				view = new FXMLLoader().load(fileUrl);
			}
		}catch(Exception e) {
			
		}
		return view;
	}
}
