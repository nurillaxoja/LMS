
package lms.Librorian.main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lms.database.databaseHandler;
import lms.util.Util;

public class LibrarianMainLoader extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LibrorianMian.fxml"));
        Scene scene = new Scene(root);
        Util.setStage(stage);
       
        
        stage.setScene(scene);
        stage.setTitle("Inha universitiy in Tashkent Librarina");
        stage.show();
        
        new Thread(() -> {
           databaseHandler.getInstance();
        }).start();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}