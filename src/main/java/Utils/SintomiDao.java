package Utils;

import javafx.scene.control.CheckBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SintomiDao {


        public List<Sintomi> getSintomi() throws SQLException {

            List<Sintomi> sintomiList = new ArrayList<>();

                String query = "SELECT nome_sintomo FROM Sintomo";
                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Creazione delle checkbox
                while (resultSet.next()) {
                    Sintomi sintomi = new Sintomi();
                    String nomeElemento = resultSet.getString("nome_sintomo");
                    sintomi.setNome(nomeElemento);
                    sintomiList.add(sintomi);
                }
                return sintomiList;

        }

}
