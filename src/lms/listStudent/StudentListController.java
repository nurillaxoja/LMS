
package lms.listStudent;

import Alert.maker.AlertMaker;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lms.Librorian.main.LibrorianMianController;
import lms.addBook.AddBookController;
import lms.addMember.AddMemberController;
import lms.addStudent.AddStudentController;
import lms.database.databaseHandler;
import lms.listBook.BookListController;
import lms.util.Util;


public class StudentListController implements Initializable {
    @FXML
    private AnchorPane sRootPane;
    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, String> nameCol;
    @FXML
    private TableColumn<Student, String> surnameCol;
    @FXML
    private TableColumn<Student, String> idCol;
    
    ObservableList<Student> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        intiCol();
        loadData();
    }    

    private void intiCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void loadData() {
        list.clear();
        databaseHandler handler = databaseHandler.getInstance();
        
        String qu = "SELECT * FROM STUDENT";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                
                list.add(new Student(name, surname, id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.setItems(list);
    }

    @FXML
    private void studentEditOp(ActionEvent event) {
         Student selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        //selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if(selectedForEdit == null){
            AlertMaker.showErrorMessage("No Student selected", "Please select Student to edit");
            return;
        }
        try { 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lms/addStudent/addStudent.fxml"));
            Parent parent = loader.load();
            
            AddStudentController controller = (AddStudentController)loader.getController();
            controller.inflateUI(selectedForEdit);
            
            Stage stage = new Stage(StageStyle.DECORATED);
            Util.setStage(stage);
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnCloseRequest((e)->{
                studentRefreshOp( new ActionEvent());
                
            });
            
            
        } catch (IOException ex) {
            Logger.getLogger(LibrorianMianController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void studentDeletOp(ActionEvent event) {
        
    }

    @FXML
    private void studentRefreshOp(ActionEvent event) {
        loadData();
    }
    
    public static class Student{
        private final SimpleStringProperty name;
        private final SimpleStringProperty surname;
        private final SimpleStringProperty id;
   
        Student(String name,String surname , String id){
            this.name = new SimpleStringProperty(name);
            this.surname = new SimpleStringProperty(surname);
            this.id = new SimpleStringProperty(id);
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
        
    
    }
    
    
}
