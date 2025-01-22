package Backend;

import Utils.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CreaCartellaClinicaController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static int chiave;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField cf;
    @FXML
    private TextField letto;
    @FXML
    private TextField telefono;
    @FXML
    private TextField cfMedico;
    @FXML
    private TextArea note;
    @FXML
    private Pane errorPane;




    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        //essendo java buggato ci prendiamo la grandezza dello schermo per mantenere la grandezza massima della finestra
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        root = FXMLLoader.load(getClass().getResource(fxmlPath));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.show();
    }


    //Metodi necessari allo switch tra le pagine iniziali
    public void switchSintomiEMalattie(ActionEvent event) throws IOException, SQLException {
        CartellaClinica cc = new CartellaClinica();
        CartellaClinicaDao ccDao = new CartellaClinicaDao();
        SintomiDao sintomiDao = new SintomiDao();
        List<Sintomo> sintomoList =sintomiDao.getSintomi();
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/SintomieMalattie.fxml"));
        root = loader.load();
        // Recupera il controller della nuova scena
        SintomiEMalattieController controller = loader.getController();

        // Passa la lista dei sintomi al controller per caricare le checkbox
        controller.setChiamante("Crea");
        controller.loadSintomiCheckboxes(sintomoList);
        if (nome.getText().isEmpty() || cognome.getText().isEmpty() || telefono.getText().isEmpty() || cfMedico.getText().isEmpty() || cf.getText().isEmpty() || letto.getText().isEmpty()) {
            errorPane.setVisible(true);
        }
        else{
            cc.setNome(nome.getText());
            cc.setCognome(cognome.getText());
            cc.setLetto( Integer.parseInt(letto.getText()));
            cc.setNote(note.getText());
            cc.setCF(cf.getText());
            cc.setData_modifica(new Timestamp(new Date().getTime()));
            cc.setCF_MedicoCurante(cfMedico.getText());
            cc.setTelefone(telefono.getText());
            CartellaClinica cc2;
            System.out.println(chiave);
            cc2=ccDao.setCartella(cc, chiave);
            controller.setCartellaClinica(cc2);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, screenWidth, screenHeight);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }



    }
    public void switchHomePage(ActionEvent event) throws IOException, SQLException {
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Scene/HomePage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, screenWidth, screenHeight);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        HomePageController controller = loader.getController();
        Platform.runLater(() -> {
            try {
                controller.caricaCartelle();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void setChiave(int chiave) {
        this.chiave = chiave;
    }
}
