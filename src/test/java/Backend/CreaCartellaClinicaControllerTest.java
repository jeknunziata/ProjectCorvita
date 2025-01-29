package Backend;

import Utils.CartellaClinica;
import Utils.CartellaClinicaDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CreaCartellaClinicaControllerTest extends ApplicationTest {

    private CreaCartellaClinicaController controller;
    private CartellaClinicaDao ccDao;
    private TextField nome, cognome, cf, letto, telefono, cfMedico;
    private Pane errorPane;
    private Button avantiButton;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/CreaCartellaClinica.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setChiave(1);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        // Inizializza i riferimenti ai nodi della scena
        nome = lookup("#nome").query();
        cognome = lookup("#cognome").query();
        cf = lookup("#cf").query();
        telefono = lookup("#telefono").query();
        cfMedico = lookup("#cfMedico").query();
        letto = lookup("#letto").query();
        errorPane = lookup("#errorPane").query();
        avantiButton = lookup("#avanti").query();

        // Usa il vero DAO (attenzione: potrebbe modificare il database!)

    }

    @Test
    void testSwitchSintomiEMalattie_withEmptyFields() {
        // Lasciamo i campi vuoti e premiamo il pulsante "Avanti"
        clickOn(avantiButton);

        // Verifica che l'errore venga mostrato
        assertTrue(errorPane.isVisible(), "L'errorPane dovrebbe essere visibile se i campi sono vuoti");
    }

    @Test
    void testSwitchSintomiEMalattie_withValidFields() throws SQLException {
        // Inseriamo dati validi nei campi
        clickOn(nome).write("Mario");
        clickOn(cognome).write("Rossi");
        clickOn(cf).write("RSSMRA80A01H501Z");
        clickOn(telefono).write("3331234567");
        clickOn(cfMedico).write("MDC123");
        clickOn(letto).write("12");

        // Clicchiamo su "Avanti"
        clickOn(avantiButton);

        // Recupera l'ultima cartella clinica creata dal database
        ccDao = new CartellaClinicaDao();
        CartellaClinica cartella = ccDao.getUltimaCartella();  // Metodo ipotetico per recuperare l'ultima cartella

        // Verifica che la cartella sia stata effettivamente creata
        assertNotNull(cartella, "La cartella clinica dovrebbe essere creata con campi validi");

        // Assicurati che l'errore non sia visibile
        assertFalse(errorPane.isVisible(), "L'errorPane non dovrebbe essere visibile con campi validi");
    }
}
