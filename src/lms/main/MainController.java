
package lms.main;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lms.Librorian.main.LibrorianMianController;
import lms.util.Util;


public class MainController implements Initializable {
    @FXML
    private JFXComboBox<String> mainCombobox;

    ObservableList<String> list = FXCollections.observableArrayList("Admin","Librarian","Student");
    @FXML
    private AnchorPane rootPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainCombobox.setItems(list);
    }    

    @FXML
    private void mainCombobox(ActionEvent event) {
        
    }

    @FXML
    private void logInMain(ActionEvent event) {
        if(mainCombobox.getValue() == "Admin"){
           Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
            loadWindow("/lms/logInAdmin/logInAdmin.fxml", "Administrator");
        }
        if(mainCombobox.getValue() == "Librarian"){
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
            loadWindow("/lms/logIn/LogIn.fxml", "Librarian");
            
        }
        if(mainCombobox.getValue() == "Student"){
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
            loadWindow("/lms/logInStudent/logInStudent.fxml", "Student");
        }
    }
    private void loadWindow(String loc , String title){
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
}
