package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent main = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(main);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Multiple Scenes Project");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
