package application;

import java.io.IOException;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MediaViewController {
	private BorderPane borderPaneModal;
	private Button btnPlay;
	private Button btnPause;
	private HBox hbox;
	
	private Media media;
	private MediaView mediaView;
	private MediaPlayer mediaPlayer;
	
	String mediaPath = "http://localhost/fileapp/fileuploads/1/1/linked.mp4";
	
	private Stage stage;
	
	public void initialize() {
//		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//		Stage stage=(Stage) lblHack.getScene().getWindow();
		stage = new Stage();
		stage.setTitle("new stage");
		borderPaneModal = new BorderPane();
		
		VBox vboxLoading = new VBox();
		vboxLoading.setAlignment(Pos.CENTER);
		Label labelLoad = new Label("Loading...");
		ProgressBar progressBar = new ProgressBar();
		vboxLoading.getChildren().addAll(labelLoad, progressBar);
		borderPaneModal.setTop(vboxLoading);
		
		media = new Media(mediaPath);
		mediaPlayer = new MediaPlayer(media);		
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setOnReady(() -> {
			vboxLoading.setVisible(false);
			vboxLoading.managedProperty().bind(vboxLoading.visibleProperty());
			stage.sizeToScene();
		});
		mediaView = new MediaView(mediaPlayer);
		borderPaneModal.setCenter(mediaView);
		
		btnPlay = new Button(">");
		btnPlay.setOnAction(event -> {
			playVideo(event);
		});
		btnPause = new Button("||");
		btnPause.setOnAction(event -> {
			pauseVideo(event);
		});
		hbox = new HBox(10);
		hbox.getChildren().addAll(btnPlay, btnPause);
		hbox.setPadding(new Insets(10));
		borderPaneModal.setBottom(hbox);
		
		Scene scene = new Scene(borderPaneModal);
		stage.setScene(scene);
		stage.show();
		
		stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
		
//		DoubleProperty width = mediaView.fitWidthProperty();
//		DoubleProperty height = mediaView.fitHeightProperty();
//		width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
//		height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
	}
	
	private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Quit application");
        alert.setContentText(String.format("Close without saving?"));
        alert.initOwner(stage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        if(res.isPresent()) {
            if(res.get().equals(ButtonType.CANCEL))
                event.consume();
        }
        
        mediaPlayer.stop();
        mediaPlayer.dispose();
    }	
	
	public void resetFocus() {
//		borderPaneModal.requestFocus();
	}
	
	public void playVideo(ActionEvent event) {
//		System.out.println("Button Play pressed");
		mediaPlayer.play();
	}
	
	public void pauseVideo(ActionEvent event) {
//		System.out.println("Button Pause pressed");
		mediaPlayer.pause();
	}
	
}