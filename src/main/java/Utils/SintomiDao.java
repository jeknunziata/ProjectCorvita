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

}
