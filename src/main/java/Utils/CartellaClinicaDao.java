package Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartellaClinicaDao {

    public List<CartellaClinica> getCartelle() throws SQLException {
        List<CartellaClinica> cartellaClinica = new ArrayList<>();
        String query = "SELECT * FROM CartellaClinica";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        // Creazione delle checkbox
        while (resultSet.next()) {
            CartellaClinica cartella = new CartellaClinica();
            cartella.setCF(resultSet.getString("CF"));
            cartella.setNome(resultSet.getString("Nome"));
            cartella.setCognome(resultSet.getString("Cognome"));
            cartella.setTelefone(resultSet.getString("Telefone"));
            cartella.setLetto(resultSet.getInt("Letto"));
            cartella.setCF_MedicoCurante(resultSet.getString("CF_MedicoCurante"));
            cartella.setData_modifica(resultSet.getDate("Data_Modifica"));
            cartella.setNote(resultSet.getString("Note"));
            cartellaClinica.add(cartella);

        }
        return cartellaClinica;

    }
    public CartellaClinica setCartella(CartellaClinica cartella) throws SQLException {
        String query = "INSERT INTO CartellaClinica (CF_Paziente, Nome_paziente, Cognome_paziente, Telefono, numero_letto, CF_Medico_Curante, Data_creazione, Note) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, cartella.getCF());
            preparedStatement.setString(2, cartella.getNome());
            preparedStatement.setString(3, cartella.getCognome());
            preparedStatement.setString(4, cartella.getTelefone());
            preparedStatement.setInt(5, cartella.getLetto());
            preparedStatement.setString(6, cartella.getCF_MedicoCurante());
            preparedStatement.setDate(7, new java.sql.Date(cartella.getData_modifica().getTime()));
            preparedStatement.setString(8, cartella.getNote());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gestione delle eccezioni
            e.printStackTrace();
            throw e;
        }
        String query2 = "select ID_CartellaClinica from CartellaClinica where CF_Paziente = ? and Nome_paziente = ? and Cognome_paziente = ? and Telefono = ? and numero_letto = ? and CF_Medico_Curante = ? and Data_creazione = ? and Note = ?";
        try (PreparedStatement preparedStatement2 = JDBC.getConnection().prepareStatement(query2)) {
            preparedStatement2.setString(1, cartella.getCF());
            preparedStatement2.setString(2, cartella.getNome());
            preparedStatement2.setString(3, cartella.getCognome());
            preparedStatement2.setString(4, cartella.getTelefone());
            preparedStatement2.setInt(5, cartella.getLetto());
            preparedStatement2.setString(6, cartella.getCF_MedicoCurante());
            preparedStatement2.setDate(7, new java.sql.Date(cartella.getData_modifica().getTime()));
            preparedStatement2.setString(8, cartella.getNote());


                try (ResultSet resultSet = preparedStatement2.executeQuery();) {
                    resultSet.next();
                    cartella.setID(resultSet.getInt("ID_CartellaClinica"));
                    return cartella;

                }
        }


    }
}

