package Backend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AcquistoLicenzaController {
    @FXML
    private TextField textFieldCF;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldCognome;
    @FXML
    private TextField textFieldNumeroCarta;
    @FXML
    private TextField textFieldNomeTitolare;
    @FXML
    private TextField textFieldCognomeTitolare;
    @FXML
    private TextField textFieldDataScadenza;
    @FXML
    private TextField textFieldCVV;
    @FXML
    private Button compraButton;

    private String CFInserito;
    private String NomeInserito;
    private String CognomeInserito;
    private String NumeroCartaInserita;
    private String NomeTitolareInserito;
    private String CognomeTitolareInserito;
    private Date DataScadenzaInserita;
    private String CVVInserito;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        System.out.println("Inizializzazione del controller");
        compraButton.setOnAction(event -> {
            System.out.println("Pulsante 'Compra' cliccato");
            verifica();
        });
    }

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

    //in caso di successo nell'acquisto della chiave di licenza, cliccando il bottone 'Compra',
    //mi switcha in una nuova scena 'VisualizzaChiaveLicenza'.
    public void switchVisualizzaChiaveLicenza(ActionEvent event) throws IOException {
        switchScene(event, "/Scene/VisualizzaChiaveLicenza.fxml");
    }

    //switch alla MainPage quando si clicca il button 'Torna indietro':
    //(N.B.:da mettere in un'interfaccia)
    public void switchMainPage(ActionEvent event) throws IOException {
        switchScene(event, "/Scene/MainPage.fxml");
    }

    //il metodo "verifica" controlla i campi inseriti:

    public void verifica() {
        boolean isValid = true; // Variabile per tenere traccia della validità complessiva

        try {
            System.out.println("Inizio verifica dei campi");

            // Controlli sul CF
            CFInserito = textFieldCF.getText();
            textFieldCF.setText(CFInserito.toUpperCase());
            if (CFInserito.length() == 16) {
                textFieldCF.setStyle("-fx-border-color: none;");
            } else {
                textFieldCF.setStyle("-fx-border-color: red;");
                isValid = false;
            }

            // Controlli sul nome
            NomeInserito = textFieldNome.getText();
            if (!NomeInserito.isEmpty() && Character.isUpperCase(NomeInserito.charAt(0))) {
                textFieldNome.setStyle("-fx-border-color: none;");
            } else {
                textFieldNome.setStyle("-fx-border-color: red;");
                isValid = false;
            }

            // Controlli sul cognome
            CognomeInserito = textFieldCognome.getText();
            if (!CognomeInserito.isEmpty() && Character.isUpperCase(CognomeInserito.charAt(0))) {
                textFieldCognome.setStyle("-fx-border-color: none;");
            } else {
                textFieldCognome.setStyle("-fx-border-color: red;");
                isValid = false;
            }

            // Controlli sul numero di carta
            NumeroCartaInserita = textFieldNumeroCarta.getText();
            if (NumeroCartaInserita.length() == 16 && NumeroCartaInserita.matches("\\d+")) {
                textFieldNumeroCarta.setStyle("-fx-border-color: none;");
            } else {
                textFieldNumeroCarta.setStyle("-fx-border-color: red;");
                isValid = false;
            }


            //Controlli sul nome del titolare :
            NomeTitolareInserito = textFieldNomeTitolare.getText();

            if (!NomeTitolareInserito.isEmpty() && Character.isUpperCase(NomeTitolareInserito.charAt(0))) {
                textFieldNomeTitolare.setStyle("-fx-border-color: none;");
            } else {
                textFieldNomeTitolare.setStyle("-fx-border-color: red;");
                isValid = false;
            }

            //Controlli sul cognome del titolare :
            CognomeTitolareInserito = textFieldCognomeTitolare.getText();
            if (!CognomeTitolareInserito.isEmpty() && Character.isUpperCase(CognomeTitolareInserito.charAt(0))) {
                textFieldCognomeTitolare.setStyle("-fx-border-color: none;");
            } else {
                textFieldCognomeTitolare.setStyle("-fx-border-color: red;");
                isValid = false;
            }
            // Controlli sulla data
            String dataScadenzaString = textFieldDataScadenza.getText();
            try {
                DataScadenzaInserita = convertiData(dataScadenzaString);
                textFieldDataScadenza.setStyle("-fx-border-color: none;");
            } catch (ParseException e) {
                textFieldDataScadenza.setStyle("-fx-border-color: red;");
                isValid = false;
            }

            // Controlli sul CVV
            CVVInserito = textFieldCVV.getText();
            if (CVVInserito.length() == 3 && CVVInserito.matches("\\d+")) {
                textFieldCVV.setStyle("-fx-border-color: none;");
            } else {
                textFieldCVV.setStyle("-fx-border-color: red;");
                isValid = false;
            }



            if (isValid) {
                System.out.println("Tutti i campi sono validi.");
                // Puoi procedere con l'azione successiva
            } else {
                System.err.println("Alcuni campi contengono errori.");
            }

        } catch (Exception e) {
            System.out.println("Errore durante la verifica: " + e.getMessage());
        }
    }

    //il metodo "verificaTipo" controlla tramite uno switch il tipo che campi inseriti (possono essere String o Date)
    //Tramite il file CSS ".css", applico lo stile al nostro TextField nel codice Java quando si verifica un errore
    private void verificaTipo(Object valore, String tipoAtteso, TextField textField) throws Exception {
        switch (tipoAtteso) {
            case "String":
                if (!(valore instanceof String)) {
                    textField.setStyle("-fx-border-color: red;"); //aggiungo rosso
                    throw new Exception("Tipo errato: atteso String");
                } else {
                    textField.setStyle("-fx-border-color: none;"); //rimuovo il colore
                }
                break;
            case "Date":
                if (!(valore instanceof Date)) {
                    textFieldDataScadenza.setStyle("-fx-border-color: red;"); //aggiungo il rosso
                    throw new Exception("Tipo errato: atteso Date.");
                } else {
                    textFieldDataScadenza.setStyle("-fx-border-color: none;"); //rimuove il colore
                }
                break;
            default:
                throw new Exception("Tipo non supportato.");
        }
    }


    //metodo per convertire una stringa in una data
    private Date convertiData(String dataString) throws ParseException {
        System.out.println("Conversione della data: " + dataString);
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        return formatoData.parse(dataString);
    }
}