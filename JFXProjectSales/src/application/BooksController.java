package application;

import java.io.File;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BooksController {
	@FXML AnchorPane anchorPaneModal;
	@FXML TableView<Books> tableBooks;
	@FXML TableColumn<Books, String> colBookName;
	@FXML TableColumn<Books, String> colBookType;
	@FXML TableColumn<Books, String> colBookSubject;
	@FXML TableColumn<Books, String> colBookLevel;
	@FXML TableColumn<Books, CheckBox> colBookTableActions;
	@FXML TableColumn<Books, Button> colBookTableView;
	@FXML Button btnAddBooksModal;
	@FXML Button btnEditBookModal;
	@FXML Button btnAddBooks;
	@FXML Button btnLoadBooks;
	@FXML Button btnDeleteBooks;
	@FXML Button btnBookBrowse;
	@FXML Label lblBooksAvailable;
	@FXML TextField txtFieldBookName;
	@FXML TextField txtFieldBookLevel;
	@FXML TextField txtFieldBookSubject;
	@FXML TextField txtFieldBookDescription;
	@FXML TextField txtFieldBookFile;
	@FXML Label lblError;
	
	File file;
	String filenameOriginal;
	
	Stage window;
	private static Stage primStage;
	
	List<String> checkedBoxes = new ArrayList<>();
	
	DBObj dbObj;
	
	public void resetFocus() {
		anchorPaneModal.requestFocus();
	}
	
	public void actionAddBookModalShow(ActionEvent event) throws IOException {
		DashboardController dashControl = new DashboardController();
		dashControl.openModalWindow("BookAdd.fxml", "Add Book");
	}
	
	// View Video
	public void actionViewBookModalShow(ActionEvent event) throws IOException {		
		MediaViewController mediaviewController = new MediaViewController();
		mediaviewController.initialize();
	}
	
	// View PDF
	public void actionViewBookModalShowPdf(ActionEvent event) throws IOException {
		System.out.println("View Book Pdf: ");
		PdfViewController pdfViewController = new PdfViewController();
		pdfViewController.initializePdfView();
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
		colBookTableView.setCellValueFactory(new PropertyValueFactory<Books, Button>("btnview"));
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
				String ftype = result.getString("filetype");
				String flocation = result.getString("filelocation");
				CheckBox checkbox = new CheckBox();
				checkbox.setId(fname);
				// create a event handler
	            EventHandler<ActionEvent> checkboxEvent = new EventHandler<ActionEvent>() {
	  
	                public void handle(ActionEvent e)
	                {
	                    if (checkbox.isSelected()) {
	                    	checkedBoxes.add(fname);
	                    } else {
	                    	checkedBoxes.remove(new String(fname));
	                    }
	                }
	  
	            };
	            checkbox.setOnAction(checkboxEvent);
	            
	            // View column and button
				String img;
	            switch(ftype.toLowerCase()) {
		            case "pdf":
		            	img = "http://localhost/fileapp/img/pdficon2.png";
		            	break;
		            case "mp4":
		            	img = "http://localhost/fileapp/img/videoicon2.png";
		            	break;
		            case "3gp":
		            	img = "http://localhost/fileapp/img/videoicon2.png";
		            	break;
		            default:
		            	img = "http://localhost/fileapp/img/unknownicon2.png";
	            }
	            System.out.println(img);
	            ImageView iconview = new ImageView(img);
	            iconview.setPickOnBounds(true);
	            iconview.setFitHeight(20);
	            iconview.setFitWidth(20);
//	            iconview.setPreserveRatio(true);
	            Button btnview = new Button();
	            btnview.setId(fname);
	            btnview.setGraphic(iconview);
	            btnview.setPrefSize(20, 20);
	            btnview.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	            btnview.setOnAction(event -> {
//	            	System.out.println("Id: ");
	            	try {
						switch(ftype.toLowerCase()) {
				            case "pdf":
				            	actionViewBookModalShowPdf(event);
				            	break;
				            case "mp4":
				            	actionViewBookModalShow(event);
				            	break;
				            case "3gp":
				            	actionViewBookModalShow(event);
				            	break;
				            default:
				            	viewBookUnknown(event, fname, flocation);
					      }
	            	} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            });
	            
				books = new Books(result.getString("filename"), result.getString("filetype"), result.getString("subjectid"), result.getString("levelid"), checkbox, btnview);
				bookList.add(books);
			}
			lblBooksAvailable.setText(String.valueOf(countResult));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bookList;
	}
	
	// Add Book (CREATE)
	public void actionAddBook(ActionEvent event) throws SQLException, IOException, InterruptedException {		
		String bookname = txtFieldBookName.getText();
		String bookdescription = txtFieldBookDescription.getText();
		String booksubject = txtFieldBookSubject.getText();
		String booklevel = txtFieldBookLevel.getText();
		String uemail = "mimi@email.com";
		
		if(!bookname.isEmpty() && !bookdescription.isEmpty() && 
			!booksubject.isEmpty() && !booklevel.isEmpty() && file != null) {
			
			String typeChar = ".";
			int typeFindIndex = filenameOriginal.lastIndexOf(typeChar) + 1;
			String booktype = filenameOriginal.substring(typeFindIndex);
			
			String fileLocation = booklevel + "/" + booksubject + "/" + bookname + "." + booktype;
//			URL urlLoc = new URL("http://localhost/fileapp/fileuploads/" + booklevel + "/" + booksubject + "/" + bookname + "." + booktype);
//			String bookfilelocation = urlLoc.getPath();
//			System.out.println(urlLoc);
			
			dbObj = new DBObj();
			try {
				//Establish connection
				Connection dbconn = dbObj.getConnection();
				
			    /*
				// Creates HttpClient object with default configuration.
		        HttpClient httpClient = HttpClient.newHttpClient();

		        // Creates HttpRequest object and set the URI to be requested, 
		        // when not defined the default request method is the GET request.
		        HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create("http://localhost/fileapp/api/fileactions.php"))
		            .GET()
		            .build();

		        // Sends the request and print out the returned response.
		        HttpResponse<String> response = httpClient.send(request,
		            HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

		        System.out.println("Status Code: " + response.statusCode());
		        System.out.println("Headers    : " + response.headers().toString());
		        System.out.println("Body       : " + response.body());
		        */
		        
				String cstmt_addBook = "{call AddFile(?, ?, ?, ?, ?, ?, ?)}";
				CallableStatement cstmt = dbconn.prepareCall(cstmt_addBook);
				cstmt.setString(1, booktype);
				cstmt.setString(2, bookname);
				cstmt.setString(3, bookdescription);
				cstmt.setString(4, booksubject);
				cstmt.setString(5, booklevel);
				cstmt.setString(6, uemail);
				cstmt.setString(7, fileLocation);
				cstmt.execute();
				ResultSet result = cstmt.getResultSet();
				while(result.next()) {
					lblError.setText(result.getString("message"));
				}
				txtFieldBookName.setText("");
				txtFieldBookDescription.setText("");
				txtFieldBookSubject.setText("");
				txtFieldBookLevel.setText("");
				txtFieldBookFile.setText("");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			CustomErrors customError = new CustomErrors();
			String error = customError.EmptyFields();
			lblError.setText(error);
		}
	}
	
	// Browse for file
	public void actionBrowse(ActionEvent event) throws IOException {
		window = (Stage)((Node)event.getSource()).getScene().getWindow();
		setPrimaryStage(window);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select file");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Files (*.pdf,*docx,*doc,*txt)", "*.pdf", "*.docx", "*.doc", "*.txt"),
				new FileChooser.ExtensionFilter("Adobe Acrobat Document (*.pdf)", "*.pdf"),
				new FileChooser.ExtensionFilter("Microsoft Word Document (*.docx)", "*.docx"),
				new FileChooser.ExtensionFilter("Microsoft Word Document (*.doc)", "*.doc"),
				new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
		file = fileChooser.showOpenDialog(BooksController.getPrimaryStage());
		if(file != null) {
			String filepath = file.getAbsolutePath();
			String findChar = "\\";
			int findIndex = filepath.lastIndexOf(findChar) + 1;
//			filenameOriginal = filepath.substring(findIndex);
			filenameOriginal = file.getName();
			txtFieldBookFile.setText(filenameOriginal);
			txtFieldBookFile.setDisable(true);
		}
		
		/*
		BufferedImage bf;
		bf = ImageIO.read(file);
		Image image = SwingFXUtils.toFXImage(bf, null);
		imgView.setImage(image);
		*/
	}
	
	// Edit book modal show
	public void actionEditBookModalShow(ActionEvent event) throws IOException {
		System.out.println("Book Edit button clicked");
//		DashboardController dashControl = new DashboardController();
//		dashControl.openModalWindow("BookAdd.fxml", "Add Book");		
	}
	
	// Delete books (DELETE)
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
	
	
	// View Unknown
	public void viewBookUnknown(ActionEvent event, String bookid, String booklocation) {
		System.out.println("View Book Unknown: " + bookid + booklocation);
	}
	
	private void setPrimaryStage(Stage primStage) {
		BooksController.primStage = primStage;
	}
	
	public static Stage getPrimaryStage() {
		return primStage;
	}
		
}
