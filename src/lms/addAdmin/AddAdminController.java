
package lms.addAdmin;

import Alert.maker.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lms.database.databaseHandler;


public class AddAdminController implements Initializable {
    @FXML
    private JFXButton btnCancel;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXTextField nameAdmin;
    @FXML
    private JFXTextField surnameAdmin;
    @FXML
    private JFXTextField idAdmin;
    @FXML
    private JFXTextField passwordAdmin;
    databaseHandler databaseHandler;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         databaseHandler = databaseHandler.getInstance();
        btnCancel.setOnAction(e->{
            Stage stage  = (Stage) rootPane.getScene().getWindow();
            stage.close();
        });
       
    }   

    @FXML
    private void loadSaveButton(ActionEvent event) {
        String id = idAdmin.getText();
        String name = nameAdmin.getText();
        String surname = surnameAdmin.getText();
        String password = passwordAdmin.getText();
        
        if(id.isEmpty()||name.isEmpty()||surname.isEmpty()||password.isEmpty()){
            AlertMaker.showErrorMessage("Error", "Please enter all fields");
            return;
        }
        
        
        String qu = "INSERT INTO ADMIN VALUES ("
             +   "'"+id+"',"
             +   "'"+name+"',"
             +   "'"+surname+"',"
             +   "'"+password+"'"
             +")";
        System.out.println(qu);
        if(databaseHandler.execAction(qu)){
            AlertMaker.showSimpleAlert("Succses", "admin saved to succsfolly");
        }else{
            AlertMaker.showErrorMessage("Error", "Admin was not saved");
        }
    }
    
}
