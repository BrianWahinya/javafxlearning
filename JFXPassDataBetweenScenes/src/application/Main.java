package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent main = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(main);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Pass Data to Another Scene");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
