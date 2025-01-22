package Backend;

import Utils.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaCartellaClinicaController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int idCartellaClinica;// Variabile per salvare l'ID della cartella
    private static int licenza;
    private CartellaClinica cartellaClinica;

    @FXML
    private Text nome;
    @FXML
    private Text cognome;
    @FXML
    private Text telefono;
    @FXML
    private Text cf;
    @FXML
    private TextArea sintomi;
    @FXML
    private Text data;
    @FXML
    private Text letto;
    @FXML
    private Text cfMedico;
    @FXML
    private TextArea note;
    @FXML
    private TextArea malattie;
    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCognome;
    @FXML
    private TextField inputTelefono;
    @FXML
    private TextField inputLetto;
    @FXML
    private TextField inputCF;
    @FXML
    private TextField inputCFMedico;
    @FXML
    private Button conferma;
    @FXML
    private Button modificaSintomi;


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
    public void switchHomePage(ActionEvent event) throws IOException, SQLException {
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
    public void caricaCartella(int ID,int licenza) throws SQLException {
        // DAO per ottenere i dati della cartella e dei sintomi
        CartellaClinicaDao cartellaClinicaDao = new CartellaClinicaDao();
        SintomiDao sintomiDao = new SintomiDao();
        MalattieDao malattieDao = new MalattieDao();
        this.licenza = licenza;
        // Recupera la cartella clinica dal database
        cartellaClinica = cartellaClinicaDao.getCartella(ID);

        // Salva l'ID della cartella nella variabile di istanza
        if (cartellaClinica != null) {
            idCartellaClinica = cartellaClinica.getID();
        } else {
            System.out.println("Nessuna cartella trovata con ID: " + ID);
            return; // Termina se non c'è una cartella
        }

        // Imposta i valori sui campi Text
        nome.setText(cartellaClinica.getNome());
        cognome.setText(cartellaClinica.getCognome());
        telefono.setText(cartellaClinica.getTelefone());
        cf.setText(cartellaClinica.getCF());
        data.setText(cartellaClinica.getData_modifica().toString());
        letto.setText(String.valueOf(cartellaClinica.getLetto()));
        note.setText(cartellaClinica.getNote());
        cfMedico.setText(cartellaClinica.getCF_MedicoCurante());

        // Recupera sintomi e malattie e aggiorna i rispettivi campi
        List<Sintomo> listaSintomi = sintomiDao.getSintomiAssociati(ID);
        List<String> sintomiStrinhe = new ArrayList<>();
        for (Sintomo sintomo : listaSintomi) {
            sintomiStrinhe.add(sintomo.getNome());
        }
        List<Malattia> listaMalattie = malattieDao.getMalattie(sintomiStrinhe);

        if (listaSintomi != null && !listaSintomi.isEmpty()) {
            StringBuilder sintomiText = new StringBuilder();
            for (Sintomo sintomo : listaSintomi) {
                sintomiText.append(sintomo.getNome()).append(", ");
            }
            if (sintomiText.length() > 0) {
                sintomiText.setLength(sintomiText.length() - 2);
            }
            sintomi.setText(sintomiText.toString());
        } else {
            sintomi.setText("Nessun sintomo associato.");
        }

        if (listaMalattie != null && !listaMalattie.isEmpty()) {
            StringBuilder malattieText = new StringBuilder();
            for (Malattia malattia : listaMalattie) {
                malattieText.append(malattia.getNome()).append(", ");
            }
            if (malattieText.length() > 0) {
                malattieText.setLength(malattieText.length() - 2);
            }
            malattie.setText(malattieText.toString());
        } else {
            malattie.setText("Nessuna malattia associata.");
        }
    }

    public void modificaCampi(ActionEvent event) throws IOException, SQLException {
        nome.setVisible(false);
        cognome.setVisible(false);
        telefono.setVisible(false);
        cf.setVisible(false);
        letto.setVisible(false);
        cfMedico.setVisible(false);
        note.setEditable(true);
        inputCF.setVisible(true);
        inputCognome.setVisible(true);
        inputTelefono.setVisible(true);
        inputLetto.setVisible(true);
        inputNome.setVisible(true);
        inputCFMedico.setVisible(true);
        conferma.setVisible(true);
        modificaSintomi.setVisible(false);
    }
    public void conferma(ActionEvent event) throws IOException, SQLException {
        if (inputNome.getText().isEmpty() || inputCognome.getText().isEmpty() ||
                inputTelefono.getText().isEmpty() || inputCF.getText().isEmpty() ||
                inputLetto.getText().isEmpty() || inputCFMedico.getText().isEmpty()) {
            System.out.println("Tutti i campi devono essere compilati.");
            return;
        }

        // Crea l'oggetto CartellaClinica con i nuovi valori
        CartellaClinica cartella = new CartellaClinica();
        cartella.setNome(inputNome.getText());
        cartella.setCognome(inputCognome.getText());
        cartella.setTelefone(inputTelefono.getText());
        cartella.setCF(inputCF.getText());
        cartella.setLetto(Integer.parseInt(inputLetto.getText()));
        cartella.setNote(note.getText());
        cartella.setCF_MedicoCurante(inputCFMedico.getText());
        cartella.setData_modifica(new java.util.Date());
        cartella.setID(idCartellaClinica);

        // Modifica la cartella nel database
        CartellaClinicaDao dao = new CartellaClinicaDao();
        dao.modificaCartella(cartella, licenza);

        System.out.println("Modifica confermata e salvata nel database.");
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/HomePage.fxml"));
        root = loader.load();
        HomePageController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        Platform.runLater(() -> {
            try {
                controller.caricaCartelle();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }
    public void modificaSintomi(ActionEvent event) throws IOException, SQLException {
        SintomiDao sintomiDao = new SintomiDao();
        List<Sintomo> sintomoList =sintomiDao.getSintomi();
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/SintomieMalattie.fxml"));
        root = loader.load();
        // Recupera il controller della nuova scena
        SintomiEMalattieController controller = loader.getController();
        controller.setChiamante("Modifica");
        controller.setLicenza(licenza);
        controller.loadSintomiCheckboxes(sintomoList);
        System.out.println(cartellaClinica.getID());
        controller.setCartellaClinica(cartellaClinica);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

}
