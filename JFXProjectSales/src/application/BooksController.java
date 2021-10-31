package application;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
	@FXML TableColumn<Books, CheckBox> colBookTableActions;
	@FXML Button btnAddBooksModal;
	@FXML Button btnAddBooks;
	@FXML Button btnLoadBooks;
	@FXML Button btnDeleteBooks;
	@FXML Label lblBooksAvailable;
	
	List<String> checkedBoxes = new ArrayList<>();
	
	DBObj dbObj;
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
	
	public void actionAddBook(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("BookAdd.fxml", "Add Book");		
	}
	
	public void actionGetAllBooks(ActionEvent event) throws SQLException {
		generateAllBooks();
	}
	
	public void generateAllBooks() throws SQLException {
		ObservableList<Books> list = getBookList();
		colBookName.setCellValueFactory(new PropertyValueFactory<Books, String>("filename"));
		colBookType.setCellValueFactory(new PropertyValueFactory<Books, String>("filetype"));
		colBookSubject.setCellValueFactory(new PropertyValueFactory<Books, String>("filesubject"));
		colBookLevel.setCellValueFactory(new PropertyValueFactory<Books, String>("filelevel"));
		colBookTableActions.setCellValueFactory(new PropertyValueFactory<Books, CheckBox>("checkbox"));
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
				String fname = result.getString("filename");
				CheckBox checkbox = new CheckBox();
				checkbox.setId(fname);
				// create a event handler
	            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
	  
	                public void handle(ActionEvent e)
	                {
	                    if (checkbox.isSelected()) {
	                    	checkedBoxes.add(fname);
	                    } else {
	                    	checkedBoxes.remove(new String(fname));
	                    }
	                }
	  
	            };
	            checkbox.setOnAction(event);
				books = new Books(result.getString("filename"), result.getString("filetype"), result.getString("subjectid"), result.getString("levelid"), checkbox);
				bookList.add(books);
			}
			lblBooksAvailable.setText(String.valueOf(countResult));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bookList;
	}
	
	// Delete users (DELETE)
	public void actionDeleteBooks(ActionEvent event) {
		// System.out.println("To be deleted: " + checkedBoxes);
		List<String> deleteMessages = new ArrayList<>();
		dbObj = new DBObj();
		try {
			//Establish connection
			Connection dbconn = dbObj.getConnection();
			for (String i : checkedBoxes) {
				  // System.out.println(i);
				  String cstmt_deleteFile = "{call DeleteFile(?, ?)}";
				  CallableStatement cstmt = dbconn.prepareCall(cstmt_deleteFile);
				  cstmt.setString(1, i);
				  cstmt.setString(2, "Mimi");
				  cstmt.execute();
				  ResultSet result = cstmt.getResultSet();
				  while(result.next()) {
					  deleteMessages.add(result.getString("message"));
				  }
			}
			// System.out.println("Deleted messages: " + deleteMessages);
			generateAllBooks();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
