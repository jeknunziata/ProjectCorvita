package Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SintomiDao {


        public List<Sintomo> getSintomi() throws SQLException {

            List<Sintomo> sintomoList = new ArrayList<>();

                String query = "SELECT nome_sintomo FROM Sintomo";
                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creazione delle checkbox
                while (resultSet.next()) {
                    Sintomo sintomo = new Sintomo();
                    String nomeElemento = resultSet.getString("nome_sintomo");
                    sintomo.setNome(nomeElemento);
                    sintomoList.add(sintomo);
                }
                return sintomoList;

        }
    public void salvaSintomi(int idCartellaClinica, List<String> nuoviSintomi, int licenza, String chiamante) throws SQLException {
        // Recupera i sintomi attualmente associati alla cartella
        String querySelect = "SELECT nome_sintomo FROM Inclusione WHERE ID_CartellaClinica = ?";
        List<String> sintomiEsistenti = new ArrayList<>();

        try (PreparedStatement selectStmt = JDBC.getConnection().prepareStatement(querySelect)) {
            selectStmt.setInt(1, idCartellaClinica);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                sintomiEsistenti.add(resultSet.getString("nome_sintomo"));
            }
        }

        // Determina i sintomi da rimuovere
        List<String> sintomiDaRimuovere = new ArrayList<>(sintomiEsistenti);
        sintomiDaRimuovere.removeAll(nuoviSintomi);

        // Determina i sintomi da aggiungere
        List<String> sintomiDaAggiungere = new ArrayList<>(nuoviSintomi);
        sintomiDaAggiungere.removeAll(sintomiEsistenti);

        // Rimuovi i sintomi non più presenti
        if (!sintomiDaRimuovere.isEmpty()) {
            String queryDelete = "DELETE FROM Inclusione WHERE ID_CartellaClinica = ? AND nome_sintomo = ?";
            try (PreparedStatement deleteStmt = JDBC.getConnection().prepareStatement(queryDelete)) {
                for (String sintomo : sintomiDaRimuovere) {
                    deleteStmt.setInt(1, idCartellaClinica);
                    deleteStmt.setString(2, sintomo);
                    deleteStmt.addBatch(); // Aggiungi alla batch
                }
                deleteStmt.executeBatch(); // Esegui la batch
            }
        }

        // Aggiungi i nuovi sintomi
        if (!sintomiDaAggiungere.isEmpty()) {
            String queryInsert = "INSERT INTO Inclusione (ID_CartellaClinica, nome_sintomo) VALUES (?, ?)";
            try (PreparedStatement insertStmt = JDBC.getConnection().prepareStatement(queryInsert)) {
                for (String sintomo : sintomiDaAggiungere) {
                    insertStmt.setInt(1, idCartellaClinica);
                    insertStmt.setString(2, sintomo);
                    insertStmt.addBatch(); // Aggiungi alla batch
                }
                insertStmt.executeBatch(); // Esegui la batch
            }
        }

        // Aggiorna lo storico se il chiamante è "Modifica"
        if ("Modifica".equals(chiamante)) {
            String queryStorico = "INSERT INTO Storico (ID_CartellaClinica, ChiaveLicenza, data_modifica, note) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
            try (PreparedStatement storicoStmt = JDBC.getConnection().prepareStatement(queryStorico)) {
                storicoStmt.setInt(1, idCartellaClinica); // Imposta l'ID della cartella clinica
                storicoStmt.setInt(2, licenza); // Imposta la chiave licenza
                storicoStmt.setString(3, "Modifica"); // Imposta la nota
                storicoStmt.executeUpdate(); // Esegui l'inserimento
            }
        }
    }


    public List<Sintomo> getSintomiAssociati(int id) throws SQLException {

        List<Sintomo> sintomoList = new ArrayList<>();

        String query = "SELECT nome_sintomo FROM Inclusione WHERE ID_CartellaClinica = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Sintomo sintomo = new Sintomo();
            String nomeElemento = resultSet.getString("nome_sintomo");
            sintomo.setNome(nomeElemento);
            sintomoList.add(sintomo);
        }
        return sintomoList;

    }



}
