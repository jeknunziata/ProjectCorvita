package Backend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoricoControllerTest extends ApplicationTest {

    private StoricoController controller;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/StoricoPage.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testShowHistoric() throws Exception {
        // Simula il click sul pulsante di visualizzazione
        clickOn("#idMostraTutto");

        ListView<String> listView = lookup("#listView").queryAs(ListView.class);

        // Verifica i risultati
        assertEquals(2, listView.getItems().size());
        assertTrue(listView.getItems().contains("Mario Rossi ha modificato la cartella clinica con ID 1 nella data 01/01/2023 10:10:10.000 con nota: Nota 1"));
        assertTrue(listView.getItems().contains("Luigi Verdi ha modificato la cartella clinica con ID 2 nella data 02/01/2023 11:11:11.000 con nota: Nota 2"));
    }
}