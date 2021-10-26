package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController {
	@FXML private BorderPane dashBorderPane;
	@FXML private Label lblUsername;
	@FXML Button btnUsers;
	@FXML Button btnLevels;
	@FXML Button btnSubjects;
	@FXML Button btnBooks;
	@FXML Button btnLogout;
	@FXML MenuItem mnItemClose;
	
	Scene fxmlFile;
	Parent root;
	Stage window;
	
	// Show logged in user
	public void setUserName(String username) {
		lblUsername.setText(username);
	}
	
	/**
	 * Dashboard Modal method
	 */
	public void openModalWindow(String resource, String title) throws IOException {
		root = FXMLLoader.load(getClass().getResource(resource));
		fxmlFile = new Scene(root);
		window = new Stage();
		window.setScene(fxmlFile);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setAlwaysOnTop(true);
		window.setIconified(false);
		window.initStyle(StageStyle.UTILITY);
		window.setTitle(title);
		/*
		window.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if(!isNowFocused) {
				window.hide();
			}
		});
		*/
		window.showAndWait();
	}
		
	/**
	 * Users Dashboard
	 */
	public void actionLoadUsersDashboard(ActionEvent event) {
		FxmlLoader loader = new FxmlLoader();
		Pane view = loader.getPage("Users");
		dashBorderPane.setCenter(view);		
	}	
	
	/**
	 * Levels Dashboard
	 */
	public void actionLoadLevelsDashboard(ActionEvent event) {
		FxmlLoader loader = new FxmlLoader();
		Pane view = loader.getPage("Levels");
		dashBorderPane.setCenter(view);		
	}
		
	/**
	 * Subjects Dashboard
	 */
	public void actionLoadSubjectsDashboard(ActionEvent event) {
		FxmlLoader loader = new FxmlLoader();
		Pane view = loader.getPage("Subjects");
		dashBorderPane.setCenter(view);		
	}
	
	/**
	 * Books Dashboard
	 */
	public void actionLoadBooksDashboard(ActionEvent event) {
		FxmlLoader loader = new FxmlLoader();
		Pane view = loader.getPage("Books");
		dashBorderPane.setCenter(view);		
	}
	
	/**
	 * Dashboard Exit methods
	 */
	// Logout
	public void actionLogout(ActionEvent event) {
		
	}
	
	// Close button
	public void actionClose(ActionEvent event) throws IOException {
		Parent mainStage = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainStage);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(mainScene);
		window.show();
	}	
	
}
