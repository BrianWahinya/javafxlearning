package application;

import java.util.Optional;
import java.util.concurrent.Callable;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MediaViewController {
	private Stage stage;
	
	private BorderPane borderPaneModal;
	private Button btnPlayPause;
	private HBox hbox;
	
	private Media media;
	private MediaView mediaView;
	private MediaPlayer mediaPlayer;
	
	private Slider mediaSlider;
	private Slider volumeSlider;
	private Label lblMediaTime;
	
	String mediaPath = "http://localhost/fileapp/fileuploads/1/1/linked.mp4";
	Boolean isPlaying;
	Boolean atEndOfVideo;
	Double currentVolume = 0.4;
	
	public void initialize() {
//		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//		Stage stage=(Stage) lblHack.getScene().getWindow();
		stage = new Stage();
		stage.setTitle("new stage");
		stage.initStyle(StageStyle.UTILITY);
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
			lblMediaTime = new Label("0.0");
			isPlaying = true;
			vboxLoading.setVisible(false);
			vboxLoading.managedProperty().bind(vboxLoading.visibleProperty());
			stage.sizeToScene();
		});
		mediaView = new MediaView(mediaPlayer);
		// Click event in the video
		mediaView.setOnMouseClicked(event -> {
			if(isPlaying) {
				isPlaying = false;
				btnPlayPause.setText("||");
				mediaPlayer.play();
			}else {
				isPlaying = true;
				btnPlayPause.setText(">");
				mediaPlayer.pause();
			}
		});
		borderPaneModal.setCenter(mediaView);
		
		// Button Play Pause
		btnPlayPause = new Button();
		btnPlayPause.setText("||");
		btnPlayPause.setOnAction(event -> {
			if(isPlaying) {
				isPlaying = false;
				btnPlayPause.setText("||");
				playVideo();
			}else {
				isPlaying = true;
				btnPlayPause.setText(">");
				pauseVideo();
			}
		});
		
		// Media slider progress time
		mediaSlider = new Slider();
		mediaSlider.setCursor(Cursor.HAND);
		mediaSlider.setPrefWidth(mediaView.getFitWidth());
		
		lblMediaTime = new Label();
		Label totalTime = new Label();
		
		bindCurrentTime();
		
		mediaPlayer.totalDurationProperty().addListener((ChangeListener<? super Duration>) new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration, Duration newDuration) {
				mediaSlider.setMax(newDuration.toSeconds());
				totalTime.setText(getTime(newDuration));
			}
		});
		
		mediaSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {
				if(!isChanging) {
					mediaPlayer.seek(Duration.seconds(mediaSlider.getValue()));
				}
			}
		});
		
		mediaSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				double currentTime = mediaPlayer.getCurrentTime().toSeconds();
				if(Math.abs(currentTime - newValue.doubleValue()) > 0.5) {
					mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
				}
			}
		});
		
		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {
				if(!mediaSlider.isValueChanging()) {
					mediaSlider.setValue(newTime.toSeconds());					
				}
			}
		});
		
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				btnPlayPause.setText(">");
				atEndOfVideo = true;
				mediaSlider.setValue(0);
				pauseVideo();
				bindCurrentTime();
			}
		});
		
		HBox timeHbox = new HBox(10);
		timeHbox.getChildren().addAll(mediaSlider, lblMediaTime, totalTime);
		
		// Volume adjust slider
		volumeSlider = new Slider();
		volumeSlider.setCursor(Cursor.HAND);
		volumeSlider.setMin(0);
		volumeSlider.setMax(1);
		volumeSlider.setValue(currentVolume);
		
		mediaPlayer.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
				
		hbox = new HBox(10);
		hbox.getChildren().addAll(btnPlayPause, volumeSlider);
		hbox.setPadding(new Insets(10));
		
		VBox vboxVideoActions = new VBox();
		vboxVideoActions.getChildren().addAll(timeHbox, hbox);
		borderPaneModal.setBottom(vboxVideoActions);
		
		mediaSlider.prefWidthProperty().bind(timeHbox.widthProperty().divide(1.2));
		timeHbox.prefWidthProperty().bind(vboxVideoActions.widthProperty());
		vboxVideoActions.prefWidthProperty().bind(borderPaneModal.widthProperty());
		
		Scene scene = new Scene(borderPaneModal);
		stage.setScene(scene);
		stage.show();
		
		stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
		
//		DoubleProperty width = mediaView.fitWidthProperty();
//		DoubleProperty height = mediaView.fitHeightProperty();
//		width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
//		height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		

		
	}
	
	public void bindCurrentTime() {
		lblMediaTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
			@Override
			public String call() throws Exception{
				return getTime(mediaPlayer.getCurrentTime()) + " / ";
			}
		}, mediaPlayer.currentTimeProperty()));
	}
	
	public String getTime(Duration time) {
		int hours = (int) time.toHours();
		int minutes = (int) time.toMinutes();
		int seconds = (int) time.toSeconds();
		
		if(seconds > 59) seconds = seconds % 60;
		if(minutes > 59) minutes = minutes % 60;
		if(hours > 24) hours = hours % 24;
		if(hours < 1) {
			return String.format("%02d:%02d", minutes, seconds);
		} else {
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}
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
	
	private void resetFocus() {
		mediaView.requestFocus();
	}
	
	public void playVideo() {
//		System.out.println("Button Play pressed");
		mediaPlayer.play();
	}
	
	public void pauseVideo() {
//		System.out.println("Button Pause pressed");
		mediaPlayer.pause();
	}
	
}