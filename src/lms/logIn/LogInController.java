
package lms.logIn;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LogInController implements Initializable {
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void LogIn(ActionEvent event) {
    }

    @FXML
    private void Cancel(ActionEvent event) {
    }
    
}
