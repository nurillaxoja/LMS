package lms.listMember;

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
import lms.database.databaseHandler;

public class MemberListController implements Initializable {

    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> surnameCol;
    @FXML
    private TableColumn<Member, String> id;
    
    databaseHandler handler = databaseHandler.getInstance();
    ObservableList<Member> list = FXCollections.observableArrayList();
   
    
    @Override
        public void initialize(URL url, ResourceBundle rb) {
        initCol();
        //databaseHandler handler = new databaseHandler();
        loadData();
        
    }
     private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setCellValueFactory(new PropertyValueFactory<>("surname"));
//        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    }
     
     private void loadData() {
        String qu = "SELECT * FROM MEMBER";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("id");
                String surname = rs.getString("surname");
              
          //      String publisher = rs.getString("publisher");
               
                list.add(new Member(name,  surname ,id ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().setAll(list);
    }
     

    public static class Member {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty surname;
//     private final SimpleStringProperty  publisher; 

        Member(String name, String id, String surname/*,String publisher,*/) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.surname = new SimpleStringProperty(surname);
//         this.publisher = new SimpleStringProperty(publisher);
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getSurname() {
            return surname.get();
        }

    }
}
