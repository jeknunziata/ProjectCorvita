package Backend;

import Utils.CartellaClinica;
import Utils.CartellaClinicaDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomePageController {

    @FXML
    Pane topPane;

    @FXML
    Label nomeUtenteLabel;
    private static int chiave;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private VBox cartelleContainer; // Un VBox definito nel file FXML per contenere i Pane delle cartelle

    @FXML
    private ScrollPane scrollPane; // ScrollPane definito
    @FXML
    private TextField barraDiRicerca; //barra di ricerca


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

    public void switchStorico(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/StoricoPage.fxml"));
        root = loader.load();
        StoricoController controller = loader.getController();
        controller.setChiave(chiave);
        switchScene(event, "/Scene/StoricoPage.fxml");
    }

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


    //il seguente metodo mi fa prima un check se la cartella clinica è presente o meno
    //se è presente, verrà messa nello storico

    public void aggiungiCartellaClinica(ActionEvent event) throws IOException {
        Stage popupStage = new Stage();
        VBox popupVBox = new VBox(10); // Spazio verticale di 10 pixel tra i figli
        TextField inputField = new TextField();
        Button submitButton = new Button("Inserisci");
        AtomicBoolean isTrovato = new AtomicBoolean(false);

        // Centrare il pulsante
        submitButton.setMaxWidth(Double.MAX_VALUE);
        popupVBox.setSpacing(10); // Spazio tra i nodi

        submitButton.setOnAction(e -> {
            String idCartella = inputField.getText();


            try {
                boolean trovato = CartellaClinicaDao.findById(idCartella);
                isTrovato.set(trovato);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (isTrovato.get()) {
                alert.setTitle("ID Cartella Clinica");
                alert.setHeaderText("ID trovato!");
                alert.setContentText("ID della cartella clinica: " + idCartella);
            } else {
                alert.setTitle("ID Cartella Clinica");
                alert.setHeaderText("ID non trovato");
                alert.setContentText("L'ID inserito non è stato trovato.");
            }

            // Handler per il click su OK
            alert.setOnHidden(eventAlert -> {
                if (isTrovato.get()) {
                    try {
                        // Esegui l'azione per mettere la cartella clinica nello storico
                        int id = Integer.parseInt(idCartella);
                        CartellaClinicaDao.aggiungiAllaStorico(id,chiave);
                        System.out.println("Cartella clinica aggiunta allo storico.");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            alert.showAndWait();
        });

        popupVBox.getChildren().addAll(new Label("Inserisci l'ID della cartella clinica:"), inputField, submitButton);
        Scene popupScene = new Scene(popupVBox, 300, 150);
        popupStage.setScene(popupScene);
        popupStage.show();
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
            pane.getStyleClass().add("contentPane");

            // Crea e configura le etichette per i dettagli della cartella
            Label IDLabel = new Label("ID:"+ cartella.getID());
            Label cognomeLabel = new Label("Cognome Paziente: " + cartella.getCognome());
            Label nomeLabel = new Label("Nome Paziente: " + cartella.getNome());
            Label dataLabel = new Label("Data Creazione: " + cartella.getData_modifica());
            Label lettoLabel = new Label("Letto: " + cartella.getLetto());
            Label cfLabel = new Label("CF: " + cartella.getCF());

            IDLabel.getStyleClass().add("cartellaContentInfo");
            cognomeLabel.getStyleClass().add("cartellaContentInfo");
            nomeLabel.getStyleClass().add("cartellaContentInfo");
            dataLabel.getStyleClass().add("cartellaContentInfo");
            lettoLabel.getStyleClass().add("cartellaContentInfo");
            cfLabel.getStyleClass().add("cartellaContentInfo");

            Button visualizzaButton = new Button("Visualizza");
            visualizzaButton.getStyleClass().add("modifyButton");
            visualizzaButton.setOnAction(event -> {
                try {
                    Screen screen = Screen.getPrimary();
                    double screenWidth = screen.getVisualBounds().getWidth();
                    double screenHeight = screen.getVisualBounds().getHeight();
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/VisualizzaCartellaClinica.fxml"));
                    root = loader.load();
                    VisualizzaCartellaClinicaController controller = loader.getController();
                    controller.caricaCartella(cartella.getID(),chiave);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root, screenWidth, screenHeight);
                    stage.setMaximized(true);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            });

            AnchorPane.setTopAnchor(IDLabel, 10.0);
            AnchorPane.setLeftAnchor(IDLabel, 10.0);

            AnchorPane.setTopAnchor(cognomeLabel, 10.0);
            AnchorPane.setLeftAnchor(cognomeLabel, 410.0);

            AnchorPane.setTopAnchor(nomeLabel, 10.0);
            AnchorPane.setLeftAnchor(nomeLabel, 810.0);

            AnchorPane.setTopAnchor(dataLabel, 100.0);
            AnchorPane.setLeftAnchor(dataLabel, 10.0);

            AnchorPane.setTopAnchor(lettoLabel, 100.0);
            AnchorPane.setLeftAnchor(lettoLabel, 410.0);

            AnchorPane.setTopAnchor(cfLabel, 100.0);
            AnchorPane.setLeftAnchor(cfLabel, 810.0);

            AnchorPane.setTopAnchor(visualizzaButton, 180.0);
            AnchorPane.setLeftAnchor(visualizzaButton, 1200.0);

            // Aggiunge tutte le etichette e il bottone al Pane
            pane.getChildren().addAll(IDLabel,cognomeLabel, nomeLabel, dataLabel, lettoLabel, cfLabel, visualizzaButton);

            // Aggiunge il Pane al contenitore principale
            cartelleContainer.getChildren().add(pane);
        }

    // Associa il VBox al ScrollPane (se non già fatto nel file FXML)
    scrollPane.setContent(cartelleContainer);

}
    public void ricerca() throws SQLException {
        String codiceFiscale = barraDiRicerca.getText();

        CartellaClinicaDao cartellaClinicaDao = new CartellaClinicaDao();
        List<CartellaClinica> cartelle = cartellaClinicaDao.cercaPerCF(codiceFiscale); // Metodo che restituisce le cartelle

        // Pulisce il contenitore prima di caricare nuovi dati
        cartelleContainer.getChildren().clear();

        for (CartellaClinica cartella : cartelle) {

            AnchorPane pane = new AnchorPane();
            pane.getStyleClass().add("contentPane");


            Label IDLabel = new Label("ID:"+ cartella.getID());
            Label cognomeLabel = new Label("Cognome Paziente: " + cartella.getCognome());
            Label nomeLabel = new Label("Nome Paziente: " + cartella.getNome());
            Label dataLabel = new Label("Data Creazione: " + cartella.getData_modifica());
            Label lettoLabel = new Label("Letto: " + cartella.getLetto());
            Label cfLabel = new Label("CF: " + cartella.getCF());

            IDLabel.getStyleClass().add("cartellaContentInfo");
            cognomeLabel.getStyleClass().add("cartellaContentInfo");
            nomeLabel.getStyleClass().add("cartellaContentInfo");
            dataLabel.getStyleClass().add("cartellaContentInfo");
            lettoLabel.getStyleClass().add("cartellaContentInfo");
            cfLabel.getStyleClass().add("cartellaContentInfo");

            Button visualizzaButton = new Button("Visualizza");
            visualizzaButton.getStyleClass().add("modifyButton");

            visualizzaButton.setOnAction(event -> {
                try {
                    Screen screen = Screen.getPrimary();
                    double screenWidth = screen.getVisualBounds().getWidth();
                    double screenHeight = screen.getVisualBounds().getHeight();
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/VisualizzaCartellaClinica.fxml"));
                    root = loader.load();
                    VisualizzaCartellaClinicaController controller = loader.getController();
                    controller.caricaCartella(cartella.getID(),chiave);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root, screenWidth, screenHeight);
                    stage.setMaximized(true);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            // Posiziona gli elementi nel Pane
            AnchorPane.setTopAnchor(IDLabel, 10.0);
            AnchorPane.setLeftAnchor(IDLabel, 10.0);

            AnchorPane.setTopAnchor(cognomeLabel, 10.0);
            AnchorPane.setLeftAnchor(cognomeLabel, 410.0);

            AnchorPane.setTopAnchor(nomeLabel, 10.0);
            AnchorPane.setLeftAnchor(nomeLabel, 810.0);

            AnchorPane.setTopAnchor(dataLabel, 100.0);
            AnchorPane.setLeftAnchor(dataLabel, 10.0);

            AnchorPane.setTopAnchor(lettoLabel, 100.0);
            AnchorPane.setLeftAnchor(lettoLabel, 410.0);

            AnchorPane.setTopAnchor(cfLabel, 100.0);
            AnchorPane.setLeftAnchor(cfLabel, 810.0);

            AnchorPane.setTopAnchor(visualizzaButton, 180.0);
            AnchorPane.setLeftAnchor(visualizzaButton, 1200.0);

            // Aggiunge tutte le etichette e il bottone al Pane
            pane.getChildren().addAll(IDLabel,cognomeLabel, nomeLabel, dataLabel, lettoLabel, cfLabel, visualizzaButton);

            // Aggiunge il Pane al contenitore principale
            cartelleContainer.getChildren().add(pane);
        }

        // Associa il VBox al ScrollPane (se non già fatto nel file FXML)
        scrollPane.setContent(cartelleContainer);

    }

//questo metodo sfrutta il metodo 'caricaCartelle' dove al click del pulsante 'RESET' mi ricarica tutte le cartelle cliniche presenti nel db
public void resettaCartelle(ActionEvent event) throws SQLException {
        caricaCartelle();
}




}