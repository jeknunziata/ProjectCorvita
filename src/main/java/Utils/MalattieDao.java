package Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MalattieDao {

    public List<Malattia> getMalattie(List<String> sintomiList) throws SQLException {
        List<Malattia> malattiaList = new ArrayList<>();

        if (sintomiList.isEmpty()) {
            return malattiaList; // Nessun sintomo selezionato
        }

        // Costruisci la query con la clausola IN
        String query = "SELECT DISTINCT nome_scientifico FROM composizione WHERE nome_sintomo IN (";
        String placeholders = String.join(",", "?".repeat(sintomiList.size()).split(""));
        query += placeholders + ")";

        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            // Imposta i parametri della query
            for (int i = 0; i < sintomiList.size(); i++) {
                preparedStatement.setString(i + 1, sintomiList.get(i));
            }

            // Esegui la query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Malattia malattia = new Malattia();
                    malattia.setNome(resultSet.getString("nome_scientifico"));
                    malattiaList.add(malattia);
                }
            }
        }

        return malattiaList;
    }
}
