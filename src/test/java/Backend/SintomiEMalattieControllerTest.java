package Backend;

import Utils.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SintomiEMalattieControllerTest extends ApplicationTest {
    private SintomiEMalattieController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/SintomiEMalattie.fxml"));
        Parent root = loader.load();
        HomePageController homePageController = new HomePageController();
        homePageController.setChiave(1);
        controller = loader.getController();
        controller.setLicenza(1);
        SintomiDao dao = new SintomiDao();
        CartellaClinica c = new CartellaClinica();
        c.setID(1);
        controller.setCartellaClinica(c);
        controller.setChiamante("Modifica");
        List<Sintomo> sintomi = dao.getSintomi();
        controller.loadSintomiCheckboxes(sintomi);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
    }

    @Test
    void testConfermaScelte() throws IOException, SQLException {
        // Simula la selezione dei sintomi
        clickOn("Acidità");  // Simula la selezione del sintomo 1
        clickOn("Alitosi");  // Simula la selezione del sintomo 2
        Button malattieButton = lookup("#malattieButton").query();
        clickOn(malattieButton);
        // Esegui l'azione di conferma
        Button confermaButton = lookup("#conferma").query();
        clickOn(confermaButton);


        // Verifica che i dati siano stati effettivamente salvati (controlla il database)
        SintomiDao sintomiDao = new SintomiDao();
        List<Sintomo> selectedSintomi = sintomiDao.getSintomiAssociati(1);// Usa il metodo appropriato per recuperare i sintomi
        String s1 = selectedSintomi.getFirst().getNome();
        String s2 = selectedSintomi.get(1).getNome();
        assertTrue(s1.contains("Acidità"), "Il sintomo 1 non è stato salvato correttamente.");
        assertTrue(s2.contains("Alitosi"), "Il sintomo 2 non è stato salvato correttamente.");
        MalattieDao malattieDao = new MalattieDao();
        List<Malattia> malattie = malattieDao.getMalattie(List.of("Acidità", "Alitosi"));  // Ottieni le malattie per i sintomi selezionati

        assertFalse(malattie.isEmpty(), "Dovrebbero essere state caricate delle malattie.");

    }


}
