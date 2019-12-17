package lms.Admin.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lms.Librorian.main.LibrorianMianController;
import lms.addMember.MamberAddLoader;


public class MainAdminController implements Initializable {
    LibrorianMianController LConntroller = new LibrorianMianController();
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnAddAdmin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAddAdmin.setOnAction(e->{
            LConntroller.loadWindow("/lms/addAdmin/addAdmin.fxml", "Add new Admin");
        
        });
        
    }    

    @FXML
    private void menuClose(ActionEvent event) {
        Stage stage = (Stage) (rootPane).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void menuAddAdmin(ActionEvent event) {
        LConntroller.loadWindow("/lms/addAdmin/addAdmin.fxml", "Add new Admin");
    }

    @FXML
    private void menuAddLibrarian(ActionEvent event) {
        LConntroller.loadWindow("/lms/addMember/addMember.fxml", "Add new librorian ");
       
        
    }

    @FXML
    private void menuAddStedent(ActionEvent event) {
        LConntroller.loadAddStudent(event);
    }

    @FXML
    private void menuViewAdmin(ActionEvent event) {
        LConntroller.loadWindow("/lms/listAdmin/adminList.fxml", "Administrators List");
    }

    @FXML
    private void menuViewLibrarian(ActionEvent event) {
        LConntroller.loadWindow("/lms/listMember/memberList.fxml", "Librarians list");
        
    }

    @FXML
    private void menuViewStudent(ActionEvent event) {
        LConntroller.menuViewStudent(event);
    }
     @FXML
    private void menuViewBook(ActionEvent event) {
        LConntroller.menuViewBook(event);
    }
    @FXML
    private void menuAbout(ActionEvent event) {
        LConntroller.menuAbout(event);
    }

    @FXML
    private void menuFullScreenMode(ActionEvent event) {
        Stage stage = ((Stage)rootPane.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    private void loadViewStudent(ActionEvent event) {
        LConntroller.loadStudentTabel(event);
        
    }

    @FXML
    private void loadViewBook(ActionEvent event) {
        LConntroller.loadBookTabel(event);
    }

    @FXML
    private void loadViewLibrorian(ActionEvent event) {
        LConntroller.loadWindow("/lms/listMember/memberList.fxml", "Librarians list");
    }

    @FXML
    private void loadViewAdmin(ActionEvent event) {
        LConntroller.loadWindow("/lms/listAdmin/adminList.fxml", "Administrators List");
    }
      
}
