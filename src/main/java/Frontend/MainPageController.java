package Frontend;

import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Metodi necessari allo switch tra le pagine iniziali
    public void switchLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MainPage/LoginScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MainPage/MainPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void switchAcquisto(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MainPage/AcquistoLicenza.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchVisualizzaChiaveLicenza(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MainPage/VisualizzaChiaveLicenza.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
