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
        //possibile instanziamento di un nuovo stage Stage stageIniziale = new Stage();

        //
        Parent root = FXMLLoader.load(getClass().getResource("/MainPage/MainPage.fxml"));
        Scene scenaIniziale = new Scene(root, Color.web("#E8F6F3"));

        String cssMainPage = this.getClass().getResource("/MainPage.css").toExternalForm();
        scenaIniziale.getStylesheets().add(cssMainPage);

        Image icona = new Image("logoCorVita.jpeg");
        stage.getIcons().add(icona);
        stage.setTitle("CorVIta");

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Per uscire dalla modalità schermo-intero premere F11");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));

        stage.setScene(scenaIniziale);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}