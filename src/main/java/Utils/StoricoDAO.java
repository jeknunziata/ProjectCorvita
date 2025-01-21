package Utils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StoricoDAO {

    public StoricoDAO() {}

    // Prende tutti i campi dello storico con i dettagli del medico
    public List<Storico> getStorico(int chiaveLicenza) throws SQLException {
        List<Storico> storici = new ArrayList<>();
        String query = "SELECT Utente.Nome AS Nome_Medico, " +
                "Utente.Cognome AS Cognome_Medico, " +
                "Storico.ID_CartellaClinica, " +
                "Storico.data_modifica, " +
                "Storico.note " +
                "FROM Storico " +
                "INNER JOIN CartellaClinica ON Storico.ID_CartellaClinica = CartellaClinica.ID_CartellaClinica " +
                "INNER JOIN Utente ON Storico.ChiaveLicenza = Utente.ChiaveLicenza " +
                "WHERE Storico.ChiaveLicenza = ?";

        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, chiaveLicenza);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

                while (resultSet.next()) {
                    Storico storico = new Storico(
                            resultSet.getString("Nome_Medico"),
                            resultSet.getString("Cognome_Medico"),
                            resultSet.getInt("ID_CartellaClinica"),
                            resultSet.getTimestamp("data_modifica"), // Usa getTimestamp per includere l'ora
                            resultSet.getString("note")
                    );

                    storici.add(storico);

                    // Aggiungi questa parte per stampare i risultati con la data formattata
                    String formattedDate = dateFormat.format(resultSet.getTimestamp("data_modifica"));
                    System.out.println("Nome Medico: " + storico.getNomeMedico() + ", Cognome Medico: " + storico.getCognomeMedico() + ", ID Cartella Clinica: " + storico.getIdCartellaClinica() + ", Data Modifica: " + formattedDate + ", Note: " + storico.getNote());
                }
            }
        }
        return storici;
    }
}
