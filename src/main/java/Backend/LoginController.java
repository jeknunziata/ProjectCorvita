package Backend;

import Utils.UtenteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    PasswordField CodiceLogin;

    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {

        //Java presenta un bug che ci obbliga a mantenere la dimensione dello schermo tramite le prime 3 linee di codice
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        System.out.println(screenWidth);
        System.out.println(screenHeight);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setMaximized(true);

        /* scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
            }
        });
        */

        stage.setScene(scene);
        stage.show();
    }


    //Metodi necessari allo switch della pagina iniziale e al login con verifica dei dati
    public void switchMain(ActionEvent event) throws IOException {
        switchScene(event, "/Scene/MainPage.fxml");
    }

    public void Login(ActionEvent event) throws IOException, SQLException {

        int chiave = Integer.parseInt(CodiceLogin.getText());
        UtenteDAO dao = new UtenteDAO();
        if (dao.checkChiaveLicenza(chiave)){
            switchScene(event, "/Scene/HomePage.fxml");
        }


    }
}
