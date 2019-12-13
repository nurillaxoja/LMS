
package lms.addStudent;

import Alert.maker.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lms.database.databaseHandler;
import lms.listBook.BookListController;
import lms.listStudent.StudentListController;
import lms.listStudent.StudentListController.Student;
import sun.security.x509.InvalidityDateExtension;


public class AddStudentController implements Initializable {
    @FXML
    private AnchorPane sRootpane;
    @FXML
    private JFXTextField sName;
    @FXML
    private JFXTextField sSurname;
    @FXML
    private JFXTextField sUserId;
    @FXML
    private JFXTextField sPassword;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

     databaseHandler handler;
     private Boolean isInEditMode = false;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      handler = databaseHandler.getInstance();
    }    

    @FXML
    private void addStudent(ActionEvent event) {
        String mName = sName.getText();
        String mSurname = sSurname.getText();
        String mId = sUserId.getText();
        String mPassword = sPassword.getText();
        Boolean flag = mName.isEmpty() || mSurname.isEmpty() || mId.isEmpty() || mPassword.isEmpty();
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter all fields");
            alert.showAndWait();
            return;
        }
        if(isInEditMode)
        { 
//            isInEditMode();
            return;
        }
        
        
    
        String st = "INSERT INTO STUDENT VALUES ("
                + "'" + mId + "',"
                + "'" + mName + "',"
                + "'" + mSurname + "',"
                + "'" + mPassword + "'"
                + ")";
        System.out.println(st);
        if(handler.execAction(st)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Member saved");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error ocured");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        Stage stage = (Stage) sRootpane.getScene().getWindow();
        stage.close();
    }
    public void inflateUI(StudentListController.Student student){
        sName.setText(student.getName());
        sSurname.setText(student.getSurname());
        sUserId.setText(student.getId());
        isInEditMode = Boolean.TRUE;
        
    }

    private void updateStudent() {
        
       //Student student = new Student(sName.getText(),sSurname.getText(),sUserId.getText());
//        if(databaseHandler.updateBook(book))
//        {
//            AlertMaker.showSimpleAlert("Succsess", "Book updated");
//        }else{
//            AlertMaker.showErrorMessage("Failes", "Book was not updated");
//        } 
    }    
}
