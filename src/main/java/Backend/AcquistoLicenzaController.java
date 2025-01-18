package Backend;

import Utils.JDBC;
import Utils.Utente;
import Utils.UtenteDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private TextField textFieldMese;
    @FXML
    private TextField textFieldAnno;
    @FXML
    private TextField textFieldCVV;
    @FXML
    private Button compraButton;
    @FXML
    private RadioButton idRadioButtonMedico;
    @FXML
    private RadioButton idRadioButtonParamedico;

    private String CFInserito;
    private String NomeInserito;
    private String CognomeInserito;
    private String NumeroCartaInserita;
    private String NomeTitolareInserito;
    private String CognomeTitolareInserito;
    private Date DataScadenzaInserita;
    private String CVVInserito;
    private String professione;
    private int ChiaveLicenza;

    private ToggleGroup group;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        System.out.println("Inizializzazione del controller");

        group = new ToggleGroup();
        idRadioButtonMedico.setToggleGroup(group);
        idRadioButtonParamedico.setToggleGroup(group);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == idRadioButtonMedico) {
                professione = "Medico";
        } else if (newValue == idRadioButtonParamedico) {
                professione = "Paramedico";
        }});

        compraButton.setOnAction(event -> {
            System.out.println("Pulsante 'Compra' cliccato");
            verifica(event);
        });
    }

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

    public void verifica(ActionEvent event) {
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


            //Controlli sul nome del titolare:
            NomeTitolareInserito = textFieldNomeTitolare.getText();

            if (!NomeTitolareInserito.isEmpty() && Character.isUpperCase(NomeTitolareInserito.charAt(0))) {
                textFieldNomeTitolare.setStyle("-fx-border-color: none;");
            } else {
                textFieldNomeTitolare.setStyle("-fx-border-color: red;");
                isValid = false;
            }

            //Controlli sul cognome del titolare:
            CognomeTitolareInserito = textFieldCognomeTitolare.getText();
            if (!CognomeTitolareInserito.isEmpty() && Character.isUpperCase(CognomeTitolareInserito.charAt(0))) {
                textFieldCognomeTitolare.setStyle("-fx-border-color: none;");
            } else {
                textFieldCognomeTitolare.setStyle("-fx-border-color: red;");
                isValid = false;
            }
            // Controlli sulla data
            String mese = textFieldMese.getText();
            String anno = textFieldAnno.getText();
            if (mese.isEmpty() || anno.isEmpty()) {
                System.out.println("I campi Mese e Anno non possono essere vuoti.");
                textFieldMese.setStyle("-fx-border-color: red;");
                textFieldAnno.setStyle("-fx-border-color: red;");
                isValid = false;
            } else {
                try {
                    String meseAnno = combinaMeseAnno(mese, anno);
                    System.out.println("Mese e Anno: " + meseAnno);

                    if (isDataScaduta(meseAnno)) {
                        System.out.println("La data è scaduta.");
                        textFieldMese.setStyle("-fx-border-color: red;");
                        textFieldAnno.setStyle("-fx-border-color: red;");
                        isValid = false;
                    } else {
                        System.out.println("La data NON è scaduta");
                        textFieldMese.setStyle(null); // Rimuove il bordo
                        textFieldAnno.setStyle(null); // Rimuove il bordo
                    }

                } catch (ParseException e) {
                    System.out.println("Errore nel parsing della data: " + e.getMessage());
                    textFieldMese.setStyle("-fx-border-color: red;");
                    textFieldAnno.setStyle("-fx-border-color: red;");
                    isValid = false;
                }
            }

            // Controlli sul CVV
            CVVInserito = textFieldCVV.getText();
            if (CVVInserito.isEmpty()) {
                System.out.println("Il campo CVV non può essere vuoto.");
                textFieldCVV.setStyle("-fx-border-color: red;");
                isValid = false;
            } else if (CVVInserito.length() == 3 && CVVInserito.matches("\\d+")) {
                textFieldCVV.setStyle(null); // Rimuove il bordo
            } else {
                System.out.println("CVV non valido.");
                textFieldCVV.setStyle("-fx-border-color: red;");
                isValid = false;
            }


            try {
                if (isValid) {
                    System.out.println("Tutti i campi sono validi.");
                    // Puoi procedere con l'azione successiva

                    UtenteDAO utenteDAO = new UtenteDAO();
                    int chiaveLicenza = utenteDAO.inserisciUtente(CFInserito, NomeInserito, CognomeInserito, professione);

                    List<Utente> utenti = utenteDAO.recuperaUtenti(CFInserito);

                    // Puoi usare la chiave di licenza qui se necessario
                    System.out.println("Chiave di licenza generata: " + chiaveLicenza);

                    // Verifica che l'evento non sia nullo
                    if (event != null) {
                        Screen screen = Screen.getPrimary();
                        double screenWidth = screen.getVisualBounds().getWidth();
                        double screenHeight = screen.getVisualBounds().getHeight();
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/VisualizzaChiaveLicenza.fxml"));
                        root = loader.load();
                        // Recupera il controller della nuova scena
                        VisualizzaChiaveLicenzaController controller = loader.getController();
                        controller.acquisizioneChiave(chiaveLicenza);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root, screenWidth, screenHeight);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        stage.show();
                        System.out.println("Sono switchato");
                    } else {
                        System.err.println("L'evento è nullo, impossibile passare all'altra pagina.");
                    }

                } else {
                    System.err.println("Alcuni campi contengono errori.");
                }

            } catch (Exception e) {
                System.out.println("Errore durante la verifica: " + e.getMessage());
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
                break;/*
            case "Date":
                if (!(valore instanceof Date)) {
                    textFieldDataScadenza.setStyle("-fx-border-color: red;"); //aggiungo il rosso
                    throw new Exception("Tipo errato: atteso Date.");
                } else {
                    textFieldDataScadenza.setStyle("-fx-border-color: none;"); //rimuove il colore
                }
                break;
            default:
                throw new Exception("Tipo non supportato.");*/
        }
    }

// Metodo per combinare mese e anno in una data
private String combinaMeseAnno(String mese, String anno) throws ParseException {
        //Controllo se il mese è nel range 1-12
        int meseInt = Integer.parseInt(mese);
        if (meseInt < 1 || meseInt > 12) {
            throw new ParseException("Mese non valido: " + mese, 0);
        }

        //Controllo se l'anno è nel range tra 00-99:
        int annoInt = Integer.parseInt(anno);
        if (annoInt < 0 || annoInt > 99) {
            throw new ParseException("Anno non valido: " + anno, 0);
        }

        String dataString = mese + "/" + anno; System.out.println("Combinazione della data: " + dataString);
        SimpleDateFormat formatoMeseAnno = new SimpleDateFormat("MM/yy");
        Date data = formatoMeseAnno.parse(dataString);
        return formatoMeseAnno.format(data);
    }

    //Metodo per verificare se la data è scaduta
    private boolean isDataScaduta (String meseAnno) throws  ParseException{
        SimpleDateFormat formatoMeseAnno = new SimpleDateFormat("MM/yy");
        Date data = formatoMeseAnno.parse(meseAnno);
        Date dataAttuale = new Date();

        return data.before(dataAttuale);
    }
/*
    //funzione che mi disattiva i campi
    private void disableTextFields(boolean disable) {
        textFieldCF.setDisable(disable);
        textFieldNome.setDisable(disable);
        textFieldCognome.setDisable(disable);
        textFieldNumeroCarta.setDisable(disable);
        textFieldNomeTitolare.setDisable(disable);
        textFieldCognomeTitolare.setDisable(disable);
        textFieldMese.setDisable(disable);
        textFieldAnno.setDisable(disable);
        textFieldCVV.setDisable(disable);
        compraButton.setDisable(disable);
    }
*/

}