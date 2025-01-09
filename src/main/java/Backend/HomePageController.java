package Backend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePageController {

    private Stage stage;

    @FXML
    AnchorPane topPane;

    @FXML
    Label nomeUtenteLabel;

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