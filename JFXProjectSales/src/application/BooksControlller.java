package application;

import java.io.IOException;

import javafx.event.ActionEvent;

public class BooksControlller {
	
	public void actionAddBook(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("BookAdd.fxml", "Add Book");		
	}
	
	public void actionGetAllBooks(ActionEvent event) {
		
	}
}
