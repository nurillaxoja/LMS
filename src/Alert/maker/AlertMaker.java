
package Alert.maker;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lms.util.Util;

public class AlertMaker {
    public static void showErrorMessage(String title, String content) {
        Stage stage = new Stage();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        Util.setStage(stage);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void showSimpleAlert(String title, String content) {
        Stage stage = new Stage();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        Util.setStage(stage);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
