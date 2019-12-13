package lms.addMember;

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

public class AddMemberController implements Initializable {

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField surname;
    @FXML
    private JFXTextField userId;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private AnchorPane rootPane;
    
     databaseHandler handler;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = databaseHandler.getInstance();
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addMember(ActionEvent event) {
        String mName = name.getText();
        String mSurname = surname.getText();
        String mId = userId.getText();
        String mPassword = password.getText();
        Boolean flag = mName.isEmpty() || mSurname.isEmpty() || mId.isEmpty() || mPassword.isEmpty();
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter all fields");
            alert.showAndWait();
            return;
        }
    
        String st = "INSERT INTO MEMBER VALUES ("
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
}
