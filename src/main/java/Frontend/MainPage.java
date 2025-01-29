package Frontend;

import Utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;

public class MainPage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Ottenere la connessione una sola volta (senza istanziare JDBC)
        Connection connection = JDBC.getConnection();

        if (connection == null) {
            showErrorDialog("Impossibile connettersi al database!", "Errore di connessione", "Si è verificato un problema durante il tentativo di connessione al database.");
            return; // Evita di continuare se la connessione fallisce
        }

        // Creazione del root contenente i dati della schermata iniziale e applicazione di questi alla scena
        Parent root = FXMLLoader.load(getClass().getResource("/Scene/MainPage.fxml"));
        Scene scenaIniziale = new Scene(root, Color.web("#E8F6F3"));

        // Aggiunta dell'icona e del titolo alla finestra
        Image icona = new Image("logoCorVita.jpeg");
        stage.getIcons().add(icona);
        stage.setTitle("CorVita");

        // Impostazioni della finestra
        stage.setMaximized(true);
        stage.setResizable(false);

        // Link della scena allo stage e conseguente caricamento
        stage.setScene(scenaIniziale);
        stage.show();

        // Gestione della chiusura dell'applicazione
        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Stai per uscire");
        alert.setContentText("Sei sicuro di voler uscire?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Logout effettuato.");
            JDBC.disconnect();  // Ora chiude correttamente la connessione
            stage.close();
        }
    }

    // Metodo per mostrare un dialogo di errore
    private void showErrorDialog(String message, String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
