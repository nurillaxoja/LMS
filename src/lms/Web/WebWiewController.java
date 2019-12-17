
package lms.Web;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class WebWiewController implements Initializable {
    @FXML
    private WebView WebView;
    private WebEngine WebEngine;
    
    @FXML
    private JFXButton btnGoogle;
    @FXML
    private JFXButton btnYandex;
    @FXML
    private JFXButton btnLibrary;
    @FXML
    private JFXButton btnReload;
  
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       WebEngine = WebView.getEngine();
        
        btnGoogle.setOnAction(e->{
            WebEngine.load("https://www.google.com/");
        });
        btnLibrary.setOnAction(e->{
            WebEngine.load("https://b-ok.org/");
        });
        btnYandex.setOnAction(e->{
            WebEngine.load("https://yandex.com/");
        });
        btnReload.setOnAction(e->{
            WebEngine.reload();  
        });
    }    
}
