package Backend;

import Utils.Utente;
import Utils.UtenteDAO;
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
import javafx.scene.text.Text;
import java.io.IOException;
import java.sql.SQLException;

public class AreaUtenteController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Text nome;
    @FXML
    private Text cognome;
    @FXML
    private Text cf;
    @FXML
    private Text professione;
    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCognome;
    @FXML
    private TextField inputCF;
    @FXML
    private Button ConfirmAlterButton;
    @FXML
    private Text errorMessage;


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
    public void switchHomePage(ActionEvent event) throws IOException {
        switchScene(event, "/Scene/HomePage.fxml");
    }
    public void modifica(ActionEvent event) throws IOException, SQLException {
        ConfirmAlterButton.setVisible(true);
        nome.setVisible(false);
        cognome.setVisible(false);
        cf.setVisible(false);
        inputNome.setVisible(true);
        inputCognome.setVisible(true);
        inputCF.setVisible(true);
        inputNome.clear();
        inputCognome.clear();
        inputCF.clear();

    }
    public void conferma(ActionEvent event) throws IOException, SQLException {
        if (inputNome.getText().isEmpty()  || inputCognome.getText().isEmpty()  || inputCF.getText().isEmpty() ) {
            System.out.println(inputNome.getText() + " " + inputCognome.getText() + " " + inputCF.getText());
            errorMessage.setVisible(true);
        }
        else {
            System.out.println(inputNome.getText() + " " + inputCognome.getText() + " " + inputCF.getText());
            Utente utente = new Utente();
            UtenteDAO utenteDAO = new UtenteDAO();
            utente.setCF(inputCF.getText());
            utente.setNome(inputNome.getText());
            utente.setCognome(inputCognome.getText());
            utente.setProfessione(professione.getText());

            FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/HomePage.fxml"));
            root = loader.load();
            // Recupera il controller della nuova scena
            HomePageController controller = loader.getController();
            if (inputCF.getText().length() == 16){
                System.out.println("sono dentro");
                utente.setChiaveLicenza(controller.getChiave());
                utenteDAO.modificaUtente(utente);
                switchScene(event, "/Scene/HomePage.fxml");
            }
            else {
                System.out.println("sono fuori");

            }

        }

    }
    public void setUtente(int chiave) throws SQLException {
        Utente utente;
        UtenteDAO utenteDAO = new UtenteDAO();
        utente= utenteDAO.getUtente(chiave);
        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        cf.setText(utente.getCF());
        professione.setText(utente.getProfessione());

    }
}
