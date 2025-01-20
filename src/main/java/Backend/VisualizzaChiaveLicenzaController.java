package Backend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.IOException;


public class VisualizzaChiaveLicenzaController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int chiaveLicenza;
    @FXML
    private Text textChiave;

    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        //essendo java buggato ci prendiamo la grandezza dello schermo per mantenere la grandezza massima della finestra
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        root = FXMLLoader.load(getClass().getResource(fxmlPath));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    public void switchLogin(ActionEvent event) throws IOException {
        switchScene(event, "/Scene/LoginScreen.fxml");
    }

    //metodo che inserisce un nuovo record nel database e restituisce il valore della chiave incrementata
    public void acquisizioneChiave(int chiaveLicenza) {
        this.chiaveLicenza = chiaveLicenza;

        //setto nella label il valore della chiave
        textChiave.setText("Ecco la tua chiave di licenza: " + String.valueOf(chiaveLicenza));

    }
}
