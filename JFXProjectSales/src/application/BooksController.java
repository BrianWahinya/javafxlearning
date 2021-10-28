package application;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class BooksController {
	@FXML AnchorPane anchorPaneModal;
	@FXML TableView<Books> tableBooks;
	@FXML TableColumn<Books, String> colBookName;
	@FXML TableColumn<Books, String> colBookType;
	@FXML TableColumn<Books, String> colBookSubject;
	@FXML TableColumn<Books, String> colBookLevel;
	@FXML TableColumn<?, String> colBookTableActions;
	@FXML Button btnAddBooksModal;
	@FXML Button btnAddBooks;
	@FXML Button btnLoadBooks;
	@FXML Label lblBooksAvailable;
	
	DBObj dbObj;
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
	
	public void actionAddBook(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("BookAdd.fxml", "Add Book");		
	}
	
	public void actionGetAllBooks(ActionEvent event) throws SQLException {
		ObservableList<Books> list = getBookList();
		colBookName.setCellValueFactory(new PropertyValueFactory<Books, String>("filename"));
		colBookType.setCellValueFactory(new PropertyValueFactory<Books, String>("filetype"));
		colBookSubject.setCellValueFactory(new PropertyValueFactory<Books, String>("filesubject"));
		colBookLevel.setCellValueFactory(new PropertyValueFactory<Books, String>("filelevel"));
		tableBooks.setItems(list);
	}
	
	public ObservableList<Books> getBookList() throws SQLException{
		ObservableList<Books> bookList = FXCollections.observableArrayList();
		dbObj = new DBObj();
		try {
			//Establish connection
			Connection dbconn = dbObj.getConnection();
			String cstmt_getAllBooks = "{call GetAllFiles()}";
			CallableStatement cstmt = dbconn.prepareCall(cstmt_getAllBooks);
			cstmt.execute();
			ResultSet result = cstmt.getResultSet();
			Books books;
			int countResult = 0;
			while(result.next()) {
				countResult++;
				books = new Books(result.getString("filename"), result.getString("filetype"), result.getString("subjectid"), result.getString("levelid"));
				bookList.add(books);
			}
			lblBooksAvailable.setText(String.valueOf(countResult));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bookList;
	}
}
