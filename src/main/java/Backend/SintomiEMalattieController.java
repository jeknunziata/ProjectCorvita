package Backend;

import Utils.Malattia;
import Utils.MalattieDao;
import Utils.Sintomo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SintomiEMalattieController {

    @FXML
    private GridPane sintomiGrid;// GridPane all'interno dello ScrollPane
    @FXML
    private StackPane malattieStack;
    @FXML
    private ScrollPane scrollContainer;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loadSintomiCheckboxes(List<Sintomo> sintomoList) {
        sintomiGrid.getChildren().clear(); // Pulisci il contenitore

        int columns = 3; // Numero di colonne
        int row = 0;
        int col = 0;

        for (Sintomo sintomo : sintomoList) {
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

    public List<String> getSelectedSintomi() {
        List<String> selectedSintomi = new ArrayList<>();

        // Itera sui figli del GridPane
        for (var node : sintomiGrid.getChildren()) {
            if (node instanceof CheckBox) { // Controlla se è una CheckBox
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) { // Verifica se è selezionata
                    selectedSintomi.add(checkBox.getText()); // Aggiungi il testo della checkbox
                }
            }
        }
        return selectedSintomi;
    }
    public void getMalattie() throws SQLException {
        MalattieDao malattieDao = new MalattieDao();
        List<Malattia> malattia;
        List<String> selectedSintomi = getSelectedSintomi(); // Ottieni i sintomi selezionati
        malattia = malattieDao.getMalattie(selectedSintomi);

        // Pulisci il contenitore principale
        malattieStack.getChildren().clear();

        // Crea un nuovo GridPane per le malattie
        GridPane malattieGrid = new GridPane();
        malattieGrid.setHgap(10); // Spaziatura orizzontale
        malattieGrid.setVgap(10); // Spaziatura verticale

        int columns = 3; // Numero di colonne
        int row = 0;
        int col = 0;

        // Aggiungi le malattie alla griglia
        for (Malattia m : malattia) {
            CheckBox checkBox = new CheckBox(m.getNome());

            // Posiziona la checkbox nella griglia
            malattieGrid.add(checkBox, col, row);

            col++;
            if (col == columns) {
                col = 0;
                row++;
            }
        }

        // Centra la griglia nel contenitore
        malattieGrid.setAlignment(javafx.geometry.Pos.CENTER);

        // Crea uno ScrollPane e imposta la griglia come contenuto
        ScrollPane scrollPane = new ScrollPane(malattieGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.getStyleClass().add("contenitoreList");
        scrollPane.setPadding(new Insets(5, 5, 5, 5));

        // Centra lo ScrollPane nello StackPane
        StackPane.setAlignment(scrollPane, javafx.geometry.Pos.CENTER);

        // Aggiungi lo ScrollPane allo StackPane
        malattieStack.getChildren().add(scrollPane);
    }
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
    public void switchCreaCartellaClinica(ActionEvent event) throws IOException {
        switchScene(event, "/Scene/CreaCartellaClinica.fxml");
    }



}
