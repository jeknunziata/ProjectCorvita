package Backend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {


    @FXML
    AnchorPane topPane;

    @FXML
    Label nomeUtenteLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        System.out.println("Cambio scena a: " + fxmlPath);
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
    public void switchCreaCartellaClinica(ActionEvent event) throws IOException {
        switchScene(event, "/Scene/CreaCartellaClinica.fxml");
    }

    public void displayName(String username) {
        nomeUtenteLabel.setText(username);
    }

    //metodo per la conferma dell'uscita tramite l'apposito bottone
    public void logout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Stai per uscire");
        alert.setContentText("Sei sicuro di voler uscire?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) topPane.getScene().getWindow();
            System.out.println("Logout called");
            stage.close();
        }

    }
}