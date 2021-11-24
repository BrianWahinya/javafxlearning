package application;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PdfViewController {
	Common common = new Common();
	
	private Stage stage;
//	private BorderPane borderPaneModal;
	private VBox vbox;
	
	public void initializePdfView() {
		WebView webView = new WebView();
		stage = new Stage();
		stage.getIcons().add(common.StageIcon());
		stage.setTitle("WebView...");
		stage.initStyle(StageStyle.UNIFIED);
//		borderPaneModal = new BorderPane();
		
		vbox = new VBox();
		vbox.getChildren().addAll(webView);
//		borderPaneModal.setCenter(vbox);
		
		Scene scene = new Scene(vbox, 800, 600);
		stage.setScene(scene);
		stage.show();
		
	}
}
