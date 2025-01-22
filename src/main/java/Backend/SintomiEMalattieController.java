package Backend;

import Utils.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Button indietro;

    private int licenza;

    private CartellaClinica cartellaClinica;
    private String chiamante;

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
        if (chiamante.equals("Modifica")){
            indietro.setVisible(false);

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

        // Aggiungi le malattie alla griglia come testo
        for (Malattia m : malattia) {
            javafx.scene.text.Text malattiaText = new javafx.scene.text.Text(m.getNome());
            malattiaText.getStyleClass().add("malattiaName");

            // Posiziona il testo nella griglia
            malattieGrid.add(malattiaText, col, row);

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
    public void confermaScelte(ActionEvent event) throws IOException, SQLException {
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/HomePage.fxml"));
        root = loader.load();
        HomePageController controller = loader.getController();
        List<String> sintomi = getSelectedSintomi();
        SintomiDao sintomiDao = new SintomiDao();
        sintomiDao.salvaSintomi(cartellaClinica.getID(),sintomi,licenza,chiamante);
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

    public void setCartellaClinica(CartellaClinica cartellaClinica) {
        this.cartellaClinica = cartellaClinica;
    }

    public void setChiamante(String chiamante) {
        this.chiamante = chiamante;
    }

    public String getChiamante() {
        return chiamante;
    }

    public void setLicenza(int licenza) {
        this.licenza = licenza;
    }
}
