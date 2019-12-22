package lms.listAdmin;

import Alert.maker.AlertMaker;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lms.Librorian.main.LibrorianMianController;
import lms.addAdmin.AddAdminController;
import lms.addBook.AddBookController;
import lms.database.databaseHandler;
import lms.util.Util;

public class AdminListController implements Initializable {

    ObservableList<Admin> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Admin> tableView;
    @FXML
    private TableColumn<Admin, String> colName;
    @FXML
    private TableColumn<Admin, String> colSurname;
    @FXML
    private TableColumn<Admin, String> colId;
    @FXML
    private TableColumn<Admin, String> colPassword;
    databaseHandler handler = databaseHandler.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void loadData() {
        list.clear();
        String qu = "SELECT * FROM ADMIN";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String id = rs.getString("id");
                String password = rs.getString("password");
                list.add(new Admin(name, surname, id, password));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().setAll(list);
    }

    @FXML
    private void ContextRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void ContextEdit(ActionEvent event) {
        Admin selectedForEdit =  (Admin) tableView.getSelectionModel().getSelectedItems();
        if(selectedForEdit == null){
            AlertMaker.showErrorMessage("No Admin selected", "Please select ADMIN to edit");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lms/addAdmin/addAdmin.fxml"));
            Parent parent = loader.load();
            
            AddAdminController controller = (AddAdminController)loader.getController();
            controller.inflateAdminUI(selectedForEdit);
            
            Stage stage = new Stage(StageStyle.DECORATED);
            Util.setStage(stage);
            stage.setTitle("Edit Admin");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnCloseRequest((e)->{
                ContextRefresh(event);
            }); 
        } catch (IOException ex) {
            Logger.getLogger(LibrorianMianController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
    

    @FXML
    private void ContextDelete(ActionEvent event) {
       Admin selectedForDelete = tableView.getSelectionModel().getSelectedItem();
        if(selectedForDelete == null){
            AlertMaker.showErrorMessage("No Admin selected", "Please select admin to delete");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Admin book");
        alert.setContentText("Are you sure to delete \""+selectedForDelete.getName()+"\" this administraror ?");
        Optional<ButtonType> ansewr = alert.showAndWait();
        if(ansewr.get() == ButtonType.OK){
            Boolean result = databaseHandler.getInstance().deleteAdmin(selectedForDelete);
            if(result){     
                AlertMaker.showSimpleAlert("Admin deleted", "Administrator \'" +selectedForDelete.getName()+"\' was succsesfully deleted");
                list.remove(selectedForDelete);
            }
            else{
                 AlertMaker.showSimpleAlert("Book deleted", "Book  \'" +selectedForDelete.getName()+"\' was not deleted  ");
            }
        }else{
            AlertMaker.showSimpleAlert("Deletion Cancel", "Book deletion cancaled succsefully");
        }
        ContextRefresh(event);
    }
    

    public static class Admin {

        private final SimpleStringProperty name;
        private final SimpleStringProperty surname;
        private final SimpleStringProperty id;
        private final SimpleStringProperty password;

        public Admin(String name,  String surname,String id, String password) {
            this.name = new SimpleStringProperty(name);
            this.surname = new SimpleStringProperty(surname);
            this.id = new SimpleStringProperty(id);
            this.password = new SimpleStringProperty(password);
        }

        public String getName() {
            return name.get();
        }

        public String getSurname() {
            return surname.get();
        }

        public String getId() {
            return id.get();
        }

        public String getPassword() {
            return password.get();
        }
    }
}