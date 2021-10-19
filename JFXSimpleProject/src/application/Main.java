package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	// Project without Scene builder
	Button button;
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage mainStage) throws Exception {
		mainStage.setTitle("Simple project");
		
		button = new Button();
		button.setText("Click me");
		
		// BorderPane layout = new BorderPane();
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		
		Scene scene = new Scene(layout, 400, 400);
		mainStage.setScene(scene);
		mainStage.show();
		
	}
}
