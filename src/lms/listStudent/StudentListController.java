    
package lms.listStudent;

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
import lms.addBook.AddBookController;
import lms.addMember.AddMemberController;
import lms.addStudent.AddStudentController;
import lms.database.databaseHandler;
import lms.listBook.BookListController;
import lms.util.Util;
import sun.plugin.javascript.navig.JSType;


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
    @FXML
    private TableColumn<Student, String> passwordCol;
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
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
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
                String password = rs.getString("password");
                
                list.add(new Student(name, surname, id,password));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.setItems(list);
    }

    @FXML
    private void studentEditOp(ActionEvent event) {
        Student selectedForEditon = tableView.getSelectionModel().getSelectedItem();
        if(selectedForEditon==null){
            AlertMaker.showErrorMessage("Select student error", "Select student first for eddition");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lms/addStudent/addStudent.fxml"));
            Parent parent = loader.load();
            
            AddStudentController controller = (AddStudentController)loader.getController();
            controller.inflateUIS(selectedForEditon);
            
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Student edit option");
            stage.setScene(new Scene(parent));
            Util.setStage(stage);
            stage.show();
            stage.setOnCloseRequest(e->{
                studentRefreshOp(new ActionEvent());
            });
            
        } catch (IOException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void studentDeletOp(ActionEvent event) {
        Student selectForDelete = tableView.getSelectionModel().getSelectedItem();
        if(selectForDelete == null){
            AlertMaker.showErrorMessage("Error", "Student not selected");
            return;
        }
        if(databaseHandler.getInstance().StudentIssedBook(selectForDelete)){
            AlertMaker.showErrorMessage("Student delet", "Student Issued book");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sudent delete");
            alert.setContentText("Are you sure tu delete student "+selectForDelete.getId()+" ?");
            Optional<ButtonType> answer = alert.showAndWait();
            if(answer.get()==ButtonType.OK){
               Boolean result = databaseHandler.getInstance().deleteStudent(selectForDelete);
               if(result){
                   AlertMaker.showSimpleAlert("Student deleted", "Student was Succsesfully deleted");
                   list.remove(selectForDelete);
               }
               else{
                   AlertMaker.showErrorMessage("Student delete", "Student "+selectForDelete.getId()+" was not deleted ");
               }
            }else{
                AlertMaker.showSimpleAlert("Delete cancel", "Deletion was succsesfully cancled");
            }
        }
    }

    @FXML
    private void studentRefreshOp(ActionEvent event) {
        loadData();
    }
    
    public static class Student{
        private final SimpleStringProperty name;
        private final SimpleStringProperty surname;
        private final SimpleStringProperty id;
        private final SimpleStringProperty password;
   
        public Student(String name,String surname , String id,String password){
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
