package Backend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextMatchers;
import static org.testfx.api.FxAssert.verifyThat;

public class AreaUtenteControllerTest extends ApplicationTest {

    private TextField nome, cognome, cf;
    private Button modifica, conferma;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/AreaUtente.fxml"));
        Parent mainNode = loader.load();
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
    public void testModificaDatiUtente() {

        // Simula il click sul pulsante per modificare i dati
        clickOn(modifica);

        // Inserisci nuovi dati nei campi di testo
        clickOn(nome).write("Carlo");
        clickOn(cognome).write("Magno");
        clickOn(cf).write("CarloCF1234567890");

        clickOn(conferma);

        // Verifica che i dati siano stati aggiornati correttamente
        verifyThat("#nome", TextMatchers.hasText("Carlo"));
        verifyThat("#cognome", TextMatchers.hasText("Magno"));
        verifyThat("#cf", TextMatchers.hasText("CarloCF1234567890"));
    }
}