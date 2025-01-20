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

public class MainPage extends Application {
    JDBC bd;

    @Override
    public void start(Stage stage) throws Exception {
        JDBC bd=new JDBC();
        bd.connect();

        //creazione del root contenente i dati della schermata iniziale e applicazione di questi alla scena
        Parent root = FXMLLoader.load(getClass().getResource("/Scene/MainPage.fxml"));
        Scene scenaIniziale = new Scene(root, Color.web("#E8F6F3"));

        //Aggiunta dell'icona e del titolo alla finestra
        Image icona = new Image("logoCorVita.jpeg");
        stage.getIcons().add(icona);
        stage.setTitle("CorVIta");

        //TEMP**questo comando serve a far sì che la finestra sia massimizzata
        stage.setMaximized(true);
        stage.setResizable(false);

        //link della scena allo stage e conseguente caricamento
        stage.setScene(scenaIniziale);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);});

    }

    public static void main(String[] args) {launch(args);}

    public void logout(Stage stage) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Stai per uscire");
        alert.setContentText("Sei sicuro di voler uscire?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Logout called");
            bd.disconnect();
            stage.close();
        }


    }

}