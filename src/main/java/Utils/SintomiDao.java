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
    public void salvaSintomi(int idCartellaClinica, List<String> sintomi) throws SQLException {
        String query = "INSERT INTO Inclusione (ID_CartellaClinica, nome_sintomo) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            for (String sintomo : sintomi) {
                preparedStatement.setInt(1, idCartellaClinica); // Imposta l'ID della cartella clinica
                preparedStatement.setString(2, sintomo); // Imposta il nome del sintomo
                preparedStatement.addBatch(); // Aggiungi alla batch
            }
            preparedStatement.executeBatch(); // Esegui la batch
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rilancia l'eccezione
        }
    }


}
