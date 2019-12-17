package lms.listAdmin;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lms.addBook.AddBookController;
import lms.database.databaseHandler;

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
