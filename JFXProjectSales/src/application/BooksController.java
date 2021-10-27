package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class BooksController {
	@FXML AnchorPane anchorPaneModal;
	
	public void actionAddBook(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("BookAdd.fxml", "Add Book");		
	}
	
	public void actionGetAllBooks(ActionEvent event) {
		
	}
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
}
