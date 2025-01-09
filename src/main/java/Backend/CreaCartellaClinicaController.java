package Backend;

import Utils.Sintomi;
import Utils.SintomiDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CreaCartellaClinicaController {
    private Stage stage;
    private Scene scene;
    private Parent root;

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


    //Metodi necessari allo switch tra le pagine iniziali
    public void switchSintomiEMalattie(ActionEvent event) throws IOException, SQLException {
        SintomiDao sintomiDao = new SintomiDao();
        List<Sintomi> sintomiList=sintomiDao.getSintomi();
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/SintomieMalattie.fxml"));
        root = loader.load();
        // Recupera il controller della nuova scena
        SintomiEMalattieController controller = loader.getController();

        // Passa la lista dei sintomi al controller per caricare le checkbox
        controller.loadSintomiCheckboxes(sintomiList);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();


    }
    public void switchHomePage(ActionEvent event) throws IOException {
        switchScene(event, "/Scene/HomePage.fxml");
    }
}
