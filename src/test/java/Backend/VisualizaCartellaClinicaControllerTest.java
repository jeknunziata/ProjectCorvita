package Backend;

import Utils.CartellaClinica;
import Utils.CartellaClinicaDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class VisualizaCartellaClinicaControllerTest extends ApplicationTest {
    private VisualizzaCartellaClinicaController controller;
    private Text errorText;
    private TextField nome, cognome, cf, letto, telefono, cfMedico;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/VisualizzaCartellaClinica.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setProfessione("Medico");
        controller.caricaCartella(1,1);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testModificaCampi_withEmptyFields() {
        // Imposta la professione su "Medico" per testare la modifica
        controller.setProfessione("Medico");

        // Verifica che i TextField siano visibili
        nome = lookup("#inputNome").query();
        cognome = lookup("#inputCognome").query();
        cf = lookup("#inputCF").query();
        telefono = lookup("#inputTelefono").query();
        cfMedico = lookup("#inputCFMedico").query();
        letto = lookup("#inputLetto").query();
        Button confermaButton = lookup("#conferma").query();
        Button modificaCampi = lookup("#modificaCampi").query();
        // Fai clic sul bottone di conferma senza compilare nulla
        clickOn(modificaCampi);
        clickOn(confermaButton);

        // Verifica che venga visualizzato un messaggio di errore
        errorText = lookup("#errorText").query();
        assertTrue(errorText.isVisible(), "Il messaggio di errore dovrebbe essere visibile quando i campi sono vuoti.");
    }


    @Test
    void testModificaCampi_withValidFields() throws SQLException, IOException {

        // Imposta la professione su "Medico" per testare la modifica
        controller.setProfessione("Medico");

        // Verifica che i TextField siano visibili
        nome = lookup("#inputNome").query();
        cognome = lookup("#inputCognome").query();
        cf = lookup("#inputCF").query();
        telefono = lookup("#inputTelefono").query();
        cfMedico = lookup("#inputCFMedico").query();
        letto = lookup("#inputLetto").query();
        Button confermaButton = lookup("#conferma").query();
        Button modificaCampi = lookup("#modificaCampi").query();

        // Compila i campi con valori validi
        clickOn(modificaCampi);
        clickOn(nome).write("Mario");
        clickOn(cognome).write("Rossi");
        clickOn(cf).write("RSSMRA80A01H501Z");
        clickOn(telefono).write("3331234567");
        clickOn(cfMedico).write("MDC123");
        clickOn(letto).write("12");
        HomePageController homePageController = new HomePageController();
        homePageController.setChiave(1);
        errorText = lookup("#errorText").query();
        // Fai clic sul bottone di conferma
        clickOn(confermaButton);


        // Verifica che il messaggio di errore non sia visibile
        assertFalse(errorText.isVisible(), "Il messaggio di errore non dovrebbe essere visibile quando tutti i campi sono compilati.");
    }

}
