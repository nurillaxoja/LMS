package lms.util;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Util {
    private static final String IMAGE_LOC= "/Resurse/main_icon.png";
    public static void setStage(Stage stage){
        stage.getIcons().add(new Image(IMAGE_LOC));
        
    }
}
