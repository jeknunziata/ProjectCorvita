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
    public Utente getUtente(int chiave) throws SQLException {
        String selectQuery = "SELECT * FROM Utente WHERE ChiaveLicenza = ?";
        try (PreparedStatement selectStatement = JDBC.getConnection().prepareStatement(selectQuery)) {
            selectStatement.setInt(1, chiave);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                resultSet.next();
                    Utente utente = new Utente(
                            resultSet.getInt("ChiaveLicenza"), // ChiaveLicenza come intero
                            resultSet.getString("CF"),
                            resultSet.getString("Nome"),
                            resultSet.getString("Cognome"),
                            resultSet.getString("Professione")
                    );
                    return utente;

            }
        }

    }
    public boolean checkChiaveLicenza(int chiave) throws SQLException {
        String query = "SELECT ChiaveLicenza FROM Utente WHERE ChiaveLicenza = ?";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, chiave);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Restituisce true se esiste almeno un risultato
            }
        }
    }
    public void modificaUtente(Utente utente) throws SQLException {
        String query = "UPDATE Utente SET Nome = ?, Cognome = ?, CF = ? WHERE ChiaveLicenza = ?";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getCF());
            preparedStatement.setInt(4, utente.getChiaveLicenza());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Utente aggiornato con successo.");
            } else {
                System.out.println("Nessun utente trovato con il codice fiscale specificato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Propaga l'eccezione al chiamante
        }
    }


}
