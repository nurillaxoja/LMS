
package lms.addBook;

import Alert.maker.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lms.database.databaseHandler;
import lms.listBook.BookListController;


public class AddBookController implements Initializable {
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    databaseHandler databaseHandler;
    @FXML
    private AnchorPane rootPane;
    private boolean isInEditMode = Boolean.FALSE;
 
            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = databaseHandler.getInstance();
        checkData();
    }    

    @FXML
    private void addBookButton(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        
        if(bookID.isEmpty()||bookAuthor.isEmpty()||bookName.isEmpty()||bookPublisher.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter all fields");
            alert.showAndWait();
            return;
        }
        if(isInEditMode){
            handleEditOp();
            return;
        }
        
        
        String qu = "INSERT INTO BOOK VALUES ("+
                "'" + bookID+ "',"+
                "'" + bookName+ "',"+
                "'" + bookAuthor+ "',"+
                "'" + bookPublisher+ "',"+
                        ""+true+""+
                    ")";
        System.out.println("qu");
        if (databaseHandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Succses");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
            
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData() {
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                String titlex = rs.getString("title");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void inflateUI(BookListController.Book book){
        title.setText(book.getTitle());
        id.setText(book.getId());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        id.setEditable(false);
        isInEditMode = Boolean.TRUE;
    }
    
    private void handleEditOp() {
        BookListController.Book book = new BookListController.Book(title.getText(),id.getText(),author.getText(),publisher.getText(),true);
        if(databaseHandler.updateBook(book))
        {
            AlertMaker.showSimpleAlert("Succsess", "Book updated");
        }else{
            AlertMaker.showErrorMessage("Failed", "Book was not updated");
        }
    }
    
}
