
package lms.Student.main;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lms.Librorian.main.LibrorianMianController;
import lms.database.databaseHandler;
import lms.logInStudent.LogInStudentController;
import lms.util.Util;


public class StudentMainController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField bookID;
    @FXML
    private ListView<String> IssueList;
    boolean isReadyForSubmisson = false;
    LogInStudentController loginScontr;
    
    databaseHandler databaseHandler;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       databaseHandler = databaseHandler.getInstance();
    }    

    @FXML
    private void menuLogOut(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
        loadWindow("/lms/main/main.fxml", "INHA Universitiy Lirary Managment System");
    }

    @FXML
    private void menuClose(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
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
    private void menuViewBook(ActionEvent event) {
        loadWindow("/lms/listBook/bookList.fxml", "Book list");
    }

    @FXML
    private void menuAbout(ActionEvent event) {
        loadWindow("/Resurse/About.fxml", "About Program");
    }

    @FXML
    private void loadViewBook(ActionEvent event) {
        loadWindow("/lms/listBook/bookList.fxml", "Book list");
    }

    @FXML
    private void loadPersonalInfo(ActionEvent event) {
        
    }

    @FXML
    private void loadWebView(ActionEvent event) {
        loadWindow("/lms/Web/webWiew.fxml", "Inha Universitiy Bowser");
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        bookID.setText(" ");
        isReadyForSubmisson = true;
        ObservableList<String> issueData = FXCollections.observableArrayList(); 
        String id = bookID.getText();
        String qu = "SELECT * FROM BOOK WHERE id = '"+id+"'";
        ResultSet rs = databaseHandler.execQuery(qu);
        Boolean flag = false;
        try {
            while(rs.next()){
                System.out.println("kerdi");
                String bName = rs.getString("title");
                issueData.add("Book name :"+bName);
                String bAuthor = rs.getString("author");
                issueData.add("Book author :" + bAuthor);
                String bPublisher = rs.getString("publisher");
                issueData.add("Book Publisher :" + bPublisher);
                Boolean bStatus = rs.getBoolean("isAvail");
                issueData.add("Book availability "+bStatus );
                flag = true;
                isReadyForSubmisson = true;
            }
            if(!flag){
                issueData.add("There is no such Book Available");
             
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibrorianMianController.class.getName()).log(Level.SEVERE, null, ex);
        }
        IssueList.getItems().setAll(issueData);
    }

    @FXML
    private void loadbtnIssue(ActionEvent event) {
      String studentId = loginScontr.txtStudentUsername.getText();
  
        String bookId = bookID.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue operatoin");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to issue book ?");
        
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
}
