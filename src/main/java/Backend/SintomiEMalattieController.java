package Backend;

import Utils.Sintomi;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import java.util.List;

public class SintomiEMalattieController {

    @FXML
    private ScrollPane scrollContainer; // ScrollPane nel file FXML
    @FXML
    private GridPane sintomiGrid; // GridPane all'interno dello ScrollPane

    public void loadSintomiCheckboxes(List<Sintomi> sintomiList) {
        sintomiGrid.getChildren().clear(); // Pulisci il contenitore

        int columns = 3; // Numero di colonne
        int row = 0;
        int col = 0;

        for (Sintomi sintomo : sintomiList) {
            CheckBox checkBox = new CheckBox(sintomo.getNome());

            // Posiziona la checkbox nella griglia
            sintomiGrid.add(checkBox, col, row);

            col++;
            if (col == columns) {
                col = 0;
                row++;
            }
        }
    }
}
