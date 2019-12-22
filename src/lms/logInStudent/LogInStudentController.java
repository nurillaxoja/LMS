package lms.logInStudent;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lms.database.databaseHandler;
import lms.logIn.LogInController;
import lms.util.Util;


public class LogInStudentController implements Initializable {
    @FXML
    private Label logInLabel;
    @FXML
    public JFXTextField txtStudentUsername;
    @FXML
    private JFXPasswordField txtStudentPassword;
    @FXML
    private Label lblWrong;

    databaseHandler databaseHandler;
    @FXML
    private AnchorPane rootPane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         databaseHandler=databaseHandler.getInstance();
    }    
   
    @FXML
    private void LogInStudent(ActionEvent event) throws IOException {
        try {
            if(databaseHandler.isLogInStudent(txtStudentUsername.getText(), txtStudentPassword.getText())){
            Parent parent = FXMLLoader.load(getClass().getResource("/lms/Student/main/StudentMain.fxml"));
            Stage stage1 = (Stage) rootPane.getScene().getWindow();
            stage1.close();
            Stage stage = new Stage(StageStyle.DECORATED);
            Util.setStage(stage);
            stage.setTitle("Log in Student");
            stage.setScene(new Scene(parent));
            stage.show();
            }else{
                lblWrong.setText("Wrong ID or password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    private void StudentCancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
}
