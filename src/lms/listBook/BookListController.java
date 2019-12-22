
package lms.listBook;

import Alert.maker.AlertMaker;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lms.Librorian.main.LibrorianMianController;
import lms.addBook.AddBookController;
import lms.database.databaseHandler;
import lms.util.Util;


public class BookListController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book,String> titleCol;
    @FXML
    private TableColumn<Book,String> idCol;
    @FXML
    private TableColumn<Book,String> authorCol;
    @FXML
    private TableColumn<Book,String> publisherCol;
    @FXML
    private TableColumn<Book,Boolean> availiblCol;
    
    databaseHandler handler = databaseHandler.getInstance();
    ObservableList<Book> list = FXCollections.observableArrayList();
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }   
    private void initCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availiblCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }
    private void loadData() {
        list.clear();
        String qu = "SELECT * FROM BOOK";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String titlex = rs.getString("title");
                String author = rs.getString("author");
                String id = rs.getString("id");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");
                list.add(new Book(  titlex , id , author, publisher, avail));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().setAll(list);
    }

    

    @FXML
    private void bookDeleteOp(ActionEvent event) {
        Book selectedForDelete = tableView.getSelectionModel().getSelectedItem();
        if(selectedForDelete == null){
            AlertMaker.showErrorMessage("No book selected", "Please select book to delete");
            return;
        }
        
        if( databaseHandler.getInstance().BookAlreadyIssed(selectedForDelete)){
            AlertMaker.showErrorMessage("Book deleted", "Book  \'" +selectedForDelete.getTitle() +"\' already is issued ");
       }
        else{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting book");
        alert.setContentText("Are you sure to delete \""+selectedForDelete.getTitle()+"\" this book ?");
        Optional<ButtonType> ansewr = alert.showAndWait();
        if(ansewr.get() == ButtonType.OK){
            Boolean result = databaseHandler.getInstance().deleteBook(selectedForDelete);
            if(result){     
                AlertMaker.showSimpleAlert("Book deleted", "Book \'" +selectedForDelete.getTitle() +"\' was succsesfully deleted");
                list.remove(selectedForDelete);
            }
            else{
                 AlertMaker.showSimpleAlert("Book deleted", "Book  \'" +selectedForDelete.getTitle() +"\' was not deleted  ");
            }
        }else{
            AlertMaker.showSimpleAlert("Deletion Cancel", "Book deletion cancaled succsefully");
        }
        }
    } 

    @FXML
    private void bookEditOp(ActionEvent event) {
        Book selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if(selectedForEdit == null){
            AlertMaker.showErrorMessage("No book selected", "Please select book to edit");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lms/addBook/addBook.fxml"));
            Parent parent = loader.load();
            
            AddBookController controller = (AddBookController)loader.getController();
            controller.inflateUI(selectedForEdit);
            
            Stage stage = new Stage(StageStyle.DECORATED);
            Util.setStage(stage);
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnCloseRequest((e)->{
                listRefreshOp( new ActionEvent());
            }); 
        } catch (IOException ex) {
            Logger.getLogger(LibrorianMianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void listRefreshOp(ActionEvent event) {
        loadData();
    }

    public static class Book{
     private final SimpleStringProperty  title; 
     private final SimpleStringProperty  id; 
     private final SimpleStringProperty  author; 
     private final SimpleStringProperty  publisher; 
     private final SimpleBooleanProperty availability;
    public Book(String title,String id,String author,String publisher,Boolean avail){
         this.title = new SimpleStringProperty(title);
         this.id = new SimpleStringProperty(id);
         this.author = new SimpleStringProperty(author);
         this.publisher = new SimpleStringProperty(publisher);
         this.availability = new SimpleBooleanProperty(avail);
     }

        public String getTitle() {
            return title.get();
        }
        public String getId() {
            return id.get();
        }
         public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getAvailability() {
            return availability.get();
        }
    }
}
