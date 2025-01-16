package Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    public int inserisciUtente(String CFInserito, String NomeInserito, String CognomeInserito, String professione) throws SQLException {
        String query = "INSERT INTO Utente(CF, Nome, Cognome, Professione) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, CFInserito);
            preparedStatement.setString(2, NomeInserito);
            preparedStatement.setString(3, CognomeInserito);
            preparedStatement.setString(4, professione);

            int result = preparedStatement.executeUpdate();
            System.out.println("Inserimento riuscito, righe affette: " + result);

            // Recupera la chiave generata automaticamente
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserimento fallito, nessuna chiave generata.");
                }
            }
        }
    }

    public List<Utente> recuperaUtenti(String CFInserito) throws SQLException {
        List<Utente> utenti = new ArrayList<>();
        String selectQuery = "SELECT * FROM Utente WHERE CF = ?";
        try (PreparedStatement selectStatement = JDBC.getConnection().prepareStatement(selectQuery)) {
            selectStatement.setString(1, CFInserito);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    Utente utente = new Utente(
                            resultSet.getInt("ChiaveLicenza"), // ChiaveLicenza come intero
                            resultSet.getString("CF"),
                            resultSet.getString("Nome"),
                            resultSet.getString("Cognome"),
                            resultSet.getString("Professione")
                    );
                    utenti.add(utente);
                }
            }
        }
        return utenti;
    }
}
