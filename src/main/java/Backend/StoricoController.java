package Backend;

import Utils.Storico;
import Utils.StoricoDAO;
import Utils.UtenteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class StoricoController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView<String> listView;
    @FXML
    private Button idMostraTutto;
    private static int chiave;

    public  void setChiave(int chiave) {
        this.chiave = chiave;
    }

    public StoricoController() {}

    @FXML
    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            System.out.println("Cambio scena a: " + fxmlPath);
            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getVisualBounds().getWidth();
            double screenHeight = screen.getVisualBounds().getHeight();
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, screenWidth, screenHeight);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Errore durante il cambio di scena: " + e.getMessage());
        }
    }

    @FXML
    private void switchHomePage(ActionEvent event) throws IOException, SQLException {
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/HomePage.fxml"));
        root = loader.load();
        HomePageController controller = loader.getController();
        controller.caricaCartelle();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchStorico(ActionEvent event) throws IOException {
        System.out.println("Chiamata a switchStorico con event: " + event);
        switchScene(event, "/Scene/StoricoPage.fxml");
    }

    // Mostra lo storico
    @FXML
    private void showHistoric(ActionEvent event) throws Exception {
        try {
            StoricoDAO storicoDAO = new StoricoDAO();
            List<Storico> storici = storicoDAO.getStorico(chiave);
            listView.getItems().clear();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

            for (Storico storico : storici) {
                String formattedDate = dateFormat.format(storico.getDataModifica());
                String message = storico.getNomeMedico() + " " + storico.getCognomeMedico() +
                        " ha modificato la cartella clinica con ID " + storico.getIdCartellaClinica() +
                        " nella data " + formattedDate +
                        " con nota: " + storico.getNote();
                listView.getItems().add(message);
            }
        } catch (SQLException ex) {
            listView.getItems().clear();
            listView.getItems().add("Errore: " + ex.getMessage());
        }
    }
}
