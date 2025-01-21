package Backend;

import Utils.CartellaClinica;
import Utils.CartellaClinicaDao;
import Utils.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePageController {


    @FXML
    AnchorPane topPane;

    @FXML
    Label nomeUtenteLabel;
    private static int chiave;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private VBox cartelleContainer; // Un VBox definito nel file FXML per contenere i Pane delle cartelle

    @FXML
    private ScrollPane scrollPane; // ScrollPane definito nel file FXML
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
    public void switchVisualizzaCartellaClinica(ActionEvent event) throws IOException {
        switchScene(event,"/Scene/VisualizzaCartellaClinica.fxml" );
    }
    public void switchCreaCartellaClinica(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/CreaCartellaClinica.fxml"));
        root = loader.load();
        CreaCartellaClinicaController controller = loader.getController();
        Utente utente = new Utente();
        controller.setChiave(chiave);
        switchScene(event, "/Scene/CreaCartellaClinica.fxml");
    }
    public void switchAreaUtente(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/AreaUtente.fxml"));
        root = loader.load();
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        // Recupera il controller della nuova scena
        AreaUtenteController controller = loader.getController();
        controller.setUtente(chiave);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

   // public void metodoMock(){

    //}

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

    public void setChiave(int chiave) {
        this.chiave = chiave;
    }

    public int getChiave() {
        return chiave;
    }


    public void caricaCartelle() throws SQLException {
        // Inizializza il DAO per recuperare le cartelle cliniche
        CartellaClinicaDao cartellaClinicaDao = new CartellaClinicaDao();
        List<CartellaClinica> cartelle = cartellaClinicaDao.getCartelle(chiave); // Metodo che restituisce le cartelle

        // Pulisce il contenitore prima di caricare nuovi dati
        cartelleContainer.getChildren().clear();

        for (CartellaClinica cartella : cartelle) {
            // Crea un nuovo Pane per ogni cartella clinica
            AnchorPane pane = new AnchorPane();
            pane.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: #f9f9f9;");

            // Crea e configura le etichette per i dettagli della cartella
            Label cognomeLabel = new Label("Cognome Paziente: " + cartella.getCognome());
            Label nomeLabel = new Label("Nome Paziente: " + cartella.getNome());
            Label dataLabel = new Label("Data Creazione: " + cartella.getData_modifica());
            Label lettoLabel = new Label("Letto: " + cartella.getLetto());
            Label cfLabel = new Label("CF: " + cartella.getCF());

            // Crea il bottone per visualizzare la cartella clinica
            Button visualizzaButton = new Button("Visualizza");
            visualizzaButton.setOnAction(event -> {
                try {
                    switchVisualizzaCartellaClinica(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Posiziona gli elementi nel Pane
            AnchorPane.setTopAnchor(cognomeLabel, 10.0);
            AnchorPane.setLeftAnchor(cognomeLabel, 10.0);

            AnchorPane.setTopAnchor(nomeLabel, 40.0);
            AnchorPane.setLeftAnchor(nomeLabel, 10.0);

            AnchorPane.setTopAnchor(dataLabel, 70.0);
            AnchorPane.setLeftAnchor(dataLabel, 10.0);

            AnchorPane.setTopAnchor(lettoLabel, 100.0);
            AnchorPane.setLeftAnchor(lettoLabel, 10.0);

            AnchorPane.setTopAnchor(cfLabel, 130.0);
            AnchorPane.setLeftAnchor(cfLabel, 10.0);

            AnchorPane.setTopAnchor(visualizzaButton, 160.0);
            AnchorPane.setLeftAnchor(visualizzaButton, 10.0);

            // Aggiunge tutte le etichette e il bottone al Pane
            pane.getChildren().addAll(cognomeLabel, nomeLabel, dataLabel, lettoLabel, cfLabel, visualizzaButton);

            // Aggiunge il Pane al contenitore principale
            cartelleContainer.getChildren().add(pane);
        }

        // Associa il VBox al ScrollPane (se non già fatto nel file FXML)
        scrollPane.setContent(cartelleContainer);
    }




}