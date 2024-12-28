package Frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //TEMP** possibile instanziamento di un nuovo stage: Stage stageIniziale = new Stage();

        //creazione del root contenente i dati della schermata iniziale e applicazione di questi alla scena
        Parent root = FXMLLoader.load(getClass().getResource("/MainPage/MainPage.fxml"));
        Scene scenaIniziale = new Scene(root, Color.web("#E8F6F3"));

        //aggiunta della pagina CSS alla scena
        String cssMainPage = this.getClass().getResource("/MainPage.css").toExternalForm();
        scenaIniziale.getStylesheets().add(cssMainPage);

        //Aggiunta dell'icona e del titolo alla finestra
        Image icona = new Image("logoCorVita.jpeg");
        stage.getIcons().add(icona);
        stage.setTitle("CorVIta");

        /* TEMP**questo comando serve a far sì che la finestra si apri in schermo intero all'avvio

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Per uscire dalla modalità schermo-intero premere F11");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));
         */

        //link della scena allo stage e conseguente caricamento
        stage.setScene(scenaIniziale);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}