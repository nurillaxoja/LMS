
package lms.logIn;

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
import lms.Librorian.main.LibrorianMianController;
import lms.database.databaseHandler;
import lms.util.Util;

public class LogInController implements Initializable {
    @FXML
    private Label logInLabel;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private AnchorPane rootPane;
    databaseHandler databaseHandler ;
    LibrorianMianController lcontroller;
    @FXML
    private Label lblWrong;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = databaseHandler.getInstance();
        
    }    

    @FXML
    private void LogInLibrarian(ActionEvent event) throws IOException {
        try {
            if(databaseHandler.isLogInLibrarian(txtUsername.getText(),txtPassword.getText())){
            Parent parent = FXMLLoader.load(getClass().getResource("/lms/Librorian/main/LibrorianMian.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            Util.setStage(stage);
            stage.setTitle("Log in Lirarian");
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
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
}
