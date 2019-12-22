
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
import lms.listStudent.StudentListController;


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
    databaseHandler databaseHandler;
     
     private Boolean isInEditMode = Boolean.FALSE;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      databaseHandler = databaseHandler.getInstance();
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
          handleSEditOp();
            return;
        }
        
        
    
        String st = "INSERT INTO STUDENT VALUES ("
                + "'" + mId + "',"
                + "'" + mName + "',"
                + "'" + mSurname + "',"
                + "'" + mPassword + "'"
                + ")";
        System.out.println(st);
        if(databaseHandler.execAction(st)){
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
    public void inflateUIS(StudentListController.Student student){
        sName.setText(student.getName());
        sSurname.setText(student.getSurname());
        sUserId.setText(student.getId());
        sPassword.setText(student.getPassword());
        sUserId.setEditable(false);
        isInEditMode = Boolean.TRUE;
        
    }



    private void handleSEditOp() {
       StudentListController.Student student = new StudentListController.Student(sName.getText(),sSurname.getText(),sUserId.getText(),sPassword.getText());
       if(databaseHandler.updateStudent(student)){
           AlertMaker.showSimpleAlert("Student change", "Student was succsefully edited");
       }else{
           AlertMaker.showErrorMessage("Student change", "Stundent edition failed");
       }
        
    }
}
