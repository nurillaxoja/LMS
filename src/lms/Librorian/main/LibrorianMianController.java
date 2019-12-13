    
package lms.Librorian.main;

import Alert.maker.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lms.database.databaseHandler;
import lms.util.Util;


public class LibrorianMianController implements Initializable {
    @FXML
    private HBox book_info;
    @FXML
    private HBox student_info;
    @FXML
    private TextField bookIdIn;
    @FXML
    private Text BookName;
    @FXML
    private Text BookAuthor;
    @FXML
    private Text BookStatus;
    
    databaseHandler databaseHandler;
    @FXML
    private TextField studentIdin;
    @FXML
    private Text studentName;
    @FXML
    private JFXTextField bookID;
    @FXML
    private ListView<String> IssueDataList;
    
    boolean isReadyForSubmisson = false;
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       JFXDepthManager.setDepth(book_info, 5);
       JFXDepthManager.setDepth(student_info, 5);
       databaseHandler = databaseHandler.getInstance();
    }    

    @FXML
    public void loadAddStudent(ActionEvent event) {
        loadWindow("/lms/addStudent/addStudent.fxml", "Add new Student");
        
    }
    @FXML
    public void loadStudentTabel(ActionEvent event) {
          loadWindow("/lms/listStudent/studentList.fxml", "Studen list");
    }
    @FXML
    public void loadAddBook(ActionEvent event) {
         loadWindow("/lms/addBook/addBook.fxml", "Add new Book");
    }
    @FXML
    public void loadBookTabel(ActionEvent event) {
         loadWindow("/lms/listBook/bookList.fxml", "Studen list");
    }
    
    
    
    public void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            Util.setStage(stage);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LibrorianMianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    private void loadBookInfo(ActionEvent event) {
        clearBookCache();
        String id = bookIdIn.getText();
        String qu = "SELECT * FROM BOOK WHERE id = '"+id+"'";
        ResultSet rs = databaseHandler.execQuery(qu);
        Boolean flag = false;
        try {
            while(rs.next()){
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvail");
               
                BookName.setText(bName);
                BookAuthor.setText(bAuthor);
                String status = (bStatus)?"Available":" Not Available";
                BookStatus.setText(status);
                
                
                flag = true;
            }
            if(!flag){
                BookName.setText("There is no such Book Available");
             
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibrorianMianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void clearBookCache(){
        BookName.setText(" ");
        BookAuthor.setText(" "); 
        BookStatus.setText("");
    }
    void clearStudentCache(){
        studentName.setText("");
        
    }
    
            
    
    @FXML
    private void loadStudentInfo(ActionEvent event) {
        clearStudentCache();
        String id = studentIdin.getText();
        String qu = "SELECT * FROM STUDENT WHERE id = '"+id+"'";
        ResultSet rs = databaseHandler.execQuery(qu);
        Boolean flag = false;
        try {
            while(rs.next()){
                String sName = rs.getString("name");
                //String bAuthor = rs.getString("author");
               // Boolean bStatus = rs.getBoolean("isAvail");
               
                studentName.setText(sName);
                // BookAuthor.setText(bAuthor);
                //String status = (bStatus)?"Available":" Not Available";
                // BookStatus.setText(status);
                
                
                flag = true;
            }
            if(!flag){
                studentName.setText("There is no such Student");
             
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibrorianMianController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void loadIssueOperation(ActionEvent event) {
        String studentId = studentIdin.getText();
        String bookId = bookIdIn.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue operatoin");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to issue book "+ BookName.getText()+"\n to"+
                studentName.getText()+"?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK){
            String str = "INSERT INTO ISSUE(studentId,bookId) VALUES ("
                    +"'"+ studentId +"',"
                    +"'"+ bookId +"')";
            String str2 = "UPDATE BOOK SET isAvail = false WHERE id = '" +bookId+"'";
            System.out.println(str+ " and "+str2);
            if(databaseHandler.execAction(str)&&databaseHandler.execAction(str2)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("SUCCESS");
                alert1.setHeaderText(null);
                alert1.setContentText("Book issue complete ");
                alert1.showAndWait();
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue operation failed");
                alert1.showAndWait();
            } 
        }else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Canceld");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue operation canceld");
                alert1.showAndWait();
        }
    
    }

    @FXML
    private void loadBookinfo2(ActionEvent event) {
        isReadyForSubmisson = false;
        ObservableList<String> issueData = FXCollections.observableArrayList();
             
        String id = bookID.getText();
        String qu = "SELECT * FROM ISSUE WHERE BOOKID = '"+ id+ "'";
        ResultSet rs = databaseHandler.execQuery(qu); 
        try {
            while(rs.next()){
                String sBookId = id; 
                String sStudentId = rs.getString("studentId");
                Timestamp sIssueTime = rs.getTimestamp("issueTime");
                Integer sRenewCount = rs.getInt("renew_count");
                issueData.add("Issue date and time :"+sIssueTime.toGMTString());
                issueData.add("Renew count :"+sRenewCount);
                
                issueData.add("\nBook informatrion \n\n");
                qu = "SELECT * FROM BOOK WHERE id = '" +sBookId+"'";
                //MASHIT TEPA HATO PAMOYMIU   ^^^^^      ERREO BOSA QARA 
                ResultSet r1= databaseHandler.execQuery(qu);
                while(r1.next()){
                        issueData.add("Book name :"+r1.getString("title"));
                        issueData.add("Book id :"+r1.getString("id"));
                        issueData.add("Book author :"+r1.getString("author"));
                        issueData.add("Book Publisher :"+r1.getString("publisher"));  
                }
                
                issueData.add("\n Student informatrion \n\n");
                qu = "SELECT * FROM STUDENT WHERE id = '" +sStudentId+"'";
                //MASHIT TEPA HATO PAMOYMIU   ^^^^^      ERREO BOSA QARA 
                r1= databaseHandler.execQuery(qu);
                while(r1.next()){
                    issueData.add("Member Name :"+r1.getString("name"));
                    issueData.add("Member Surname :"+r1.getString("surname"));
                    issueData.add("Member ID :"+r1.getString("id"));
                    
                }
                isReadyForSubmisson = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibrorianMianController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        IssueDataList.getItems().setAll(issueData);
    }

    @FXML
    private void loadSubmissionOP(ActionEvent event) {
       
        if(!isReadyForSubmisson){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Plsee select book to return");
                alert1.showAndWait();
            return;
        }
       
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Book return");
                alert2.setHeaderText(null);
                alert2.setContentText("Are you shure to retur this book");
        Optional<ButtonType> response2 = alert2.showAndWait();
        if(response2.get()== ButtonType.OK)
        {
        String id = bookID.getText();
        String ac1 = "DELETE FROM ISSUE WHERE BOOKID = '"+id+"'";
        String ac2 = "UPDATE BOOK SET  ISAVAIL = TRUE WHERE ID = '"+id+"'";
        
        if(databaseHandler.execAction(ac1)&&databaseHandler.execAction(ac2)){
            
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Book return");
                alert.setHeaderText(null);
                alert.setContentText("Book was returned");
                alert.showAndWait();
        }else{
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Book was not returned");
                alert.setHeaderText(null);
                alert.setContentText("Return failed");
                alert.showAndWait();
        }
        ///////
        }
        else{
            AlertMaker.showSimpleAlert("Book return", "Book Return cancled seccsfelly");
        }
         
        
    }

    @FXML
    private void loadRenewOp(ActionEvent event) {
        if(!isReadyForSubmisson){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Plsee select book to renew");
                alert1.showAndWait();
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm renew Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you shure about renew the book");
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK){
            String ac= "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP ,renew_count =renew_count+1 WHERE BOOKID  = '"+ bookID.getText()+"'";
            System.out.println(ac);
            if(databaseHandler.execAction(ac)){
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Book renew");
                alert1.setHeaderText(null);
                alert1.setContentText("Book renew was succses");
                alert1.showAndWait();
            }else{
                 Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Book was not renewed");
                alert1.setHeaderText(null);
                alert1.setContentText("Renew failed");
                alert1.showAndWait();
            }   
        }    
        else{
             Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Cancled");
                alert1.setHeaderText(null);
                alert1.setContentText("Renew cancled");
                alert1.showAndWait();
        }
        
    }    

    @FXML
    private void menuClose(ActionEvent event) {
       ((Stage)rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void menuAddBook(ActionEvent event) {
       
         loadWindow("/lms/addBook/addBook.fxml", "Add new Book");
    }
    @FXML
    private void menuAddStudent(ActionEvent event) {
         loadWindow("/lms/addStudent/addStudent.fxml", "Add new Student");
    }



    @FXML
    public void menuViewBook(ActionEvent event) {
        loadWindow("/lms/listBook/bookList.fxml", "Studen list");
          
    }

    @FXML
    public void menuViewStudent(ActionEvent event) {
        loadWindow("/lms/listStudent/studentList.fxml", "Studen list");
    }

    @FXML
    public void menuFullScreen(ActionEvent event) {
        Stage stage = ((Stage)rootPane.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    public void menuAbout(ActionEvent event) {
        loadWindow("/Resurse/About.fxml", "About Program");
    }

    @FXML
    private void loadSearchInNen(ActionEvent event) {
    
    }

    
    
  
}


    
