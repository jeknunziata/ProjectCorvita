package Backend;

import Utils.Utente;
import Utils.UtenteDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AreaUtenteControllerTest extends ApplicationTest {

    private AreaUtenteController controller;

    private TextField nome, cognome, cf;
    private Button modifica, conferma;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/AreaUtente.fxml"));
        Parent mainNode = loader.load();

        HomePageController homePageController = new HomePageController();
        homePageController.setChiave(1);

        controller = loader.getController();
        controller.setUtente(1);

        stage.setScene(new Scene(mainNode));
        stage.show();
    }

    @BeforeEach
    void setUp() {

        // Inizializza i riferimenti ai nodi della scena
        nome = lookup("#inputNome").query();
        cognome = lookup("#inputCognome").query();
        cf = lookup("#inputCF").query();
        modifica = lookup("#AlterButton").query();
        conferma = lookup("#ConfirmAlterButton").query();
    }

    @Test
    public void testModificaDatiUtente() throws SQLException {

        Utente utente;
        UtenteDAO utenteDAO = new UtenteDAO();

        // Simula il click sul pulsante per modificare i dati
        clickOn(modifica);

        // Inserisci nuovi dati nei campi di testo
        clickOn(nome).write("Carlo");
        clickOn(cognome).write("Magno");
        clickOn(cf).write("CarloCF123456789");

        clickOn(conferma);

        utente = utenteDAO.getUtente(1);

        // Verifica che i dati siano stati aggiornati correttamente
        assertEquals("Carlo", utente.getNome());
        assertEquals("Magno", utente.getCognome());
        assertEquals("CarloCF123456789", utente.getCF());
    }
}