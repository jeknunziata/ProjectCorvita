package Frontend;

import Utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainPage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //JDBC bd=new JDBC();
        //bd.connect();
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

        //TEMP**questo comando serve a far sì che la finestra sia massimizzata
        stage.setMaximized(true);



        //link della scena allo stage e conseguente caricamento
        stage.setScene(scenaIniziale);
        stage.show();

        //bd.disconnect();
    }

    public static void main(String[] args) {launch(args);}


}