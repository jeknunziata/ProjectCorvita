package Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartellaClinicaDao {

    public List<CartellaClinica> getCartelle(int chiave) throws SQLException {
        List<CartellaClinica> cartellaClinicaList = new ArrayList<>();
        String query = "SELECT DISTINCT CartellaClinica.ID_CartellaClinica, CartellaClinica.CF_Paziente, CartellaClinica.Nome_paziente, " +
                "CartellaClinica.Cognome_paziente, CartellaClinica.Telefono, CartellaClinica.numero_letto, CartellaClinica.CF_Medico_Curante, " +
                "CartellaClinica.Data_creazione, CartellaClinica.Note " +
                "FROM Storico " +
                "JOIN CartellaClinica ON Storico.ID_CartellaClinica = CartellaClinica.ID_CartellaClinica " +
                "WHERE ChiaveLicenza = ?";

        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            // Imposta il valore del parametro nella query
            preparedStatement.setInt(1, chiave);

            // Esegui la query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Processa i risultati
                while (resultSet.next()) {
                    CartellaClinica cartella = new CartellaClinica();
                    cartella.setCF(resultSet.getString("CF_Paziente"));
                    cartella.setNome(resultSet.getString("Nome_paziente"));
                    cartella.setCognome(resultSet.getString("Cognome_paziente"));
                    cartella.setTelefone(resultSet.getString("Telefono"));
                    cartella.setLetto(resultSet.getInt("numero_letto"));
                    cartella.setCF_MedicoCurante(resultSet.getString("CF_Medico_Curante"));
                    cartella.setData_modifica(resultSet.getDate("Data_creazione"));
                    cartella.setNote(resultSet.getString("Note"));
                    cartella.setID(resultSet.getInt("ID_CartellaClinica"));
                    cartellaClinicaList.add(cartella);
                }
            }
        }
        return cartellaClinicaList;
    }


    public CartellaClinica setCartella(CartellaClinica cartella,int chiave) throws SQLException {
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


                }
        }
        String query3 = "INSERT INTO Storico (ID_CartellaClinica, ChiaveLicenza, data_modifica, note) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query3)) {
            preparedStatement.setInt(1, cartella.getID());
            preparedStatement.setInt(2, chiave);
            preparedStatement.setDate(3, new java.sql.Date(cartella.getData_modifica().getTime()));
            preparedStatement.setString(4,"Creazione");


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gestione delle eccezioni
            e.printStackTrace();
            throw e;
        }
        return cartella;


    }
    public CartellaClinica getCartella(int chiave) throws SQLException {
        CartellaClinica cartellaClinica = null;
        String query = "SELECT * " +
                "FROM CartellaClinica " +
                "WHERE ID_CartellaClinica = ?";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            // Imposta il valore del parametro nella query
            preparedStatement.setInt(1, chiave);

            // Esegui la query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Controlla se ci sono risultati
                if (resultSet.next()) {
                    cartellaClinica = new CartellaClinica();
                    cartellaClinica.setCF(resultSet.getString("CF_Paziente"));
                    cartellaClinica.setNome(resultSet.getString("Nome_paziente"));
                    cartellaClinica.setCognome(resultSet.getString("Cognome_paziente"));
                    cartellaClinica.setTelefone(resultSet.getString("Telefono"));
                    cartellaClinica.setLetto(resultSet.getInt("numero_letto"));
                    cartellaClinica.setCF_MedicoCurante(resultSet.getString("CF_Medico_Curante"));
                    cartellaClinica.setData_modifica(resultSet.getDate("Data_creazione"));
                    cartellaClinica.setNote(resultSet.getString("Note"));
                    cartellaClinica.setID(resultSet.getInt("ID_CartellaClinica"));
                }
            }
        }
        return cartellaClinica;
    }



    //metodo che mi stabilisce se l'id inserito nel pop-up è presente tra le cartelle cliniche o meno
    public static boolean findById(String idCheck) throws SQLException {
        String query = "SELECT * FROM CartellaClinica WHERE ID_CartellaClinica = ?";

        try(PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, idCheck);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("ID_CartellaClinica").equals(idCheck)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void aggiungiAllaStorico(int idCartella, int chiave) throws SQLException {
        String query = "INSERT INTO Storico(ChiaveLicenza,ID_CartellaClinica,data_modifica,note) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, chiave);
            preparedStatement.setInt(2, idCartella);
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime())); // Usa Timestamp
            preparedStatement.setString(4, "Condivisione");
            preparedStatement.executeUpdate();
        }
    }
    public void modificaCartella(CartellaClinica cartella, int chiaveLicenza) throws SQLException {
        // Query per aggiornare la cartella clinica
        String updateQuery = "UPDATE CartellaClinica " +
                "SET CF_Paziente = ?, Nome_paziente = ?, Cognome_paziente = ?, Telefono = ?, numero_letto = ?, CF_Medico_Curante = ?, Data_creazione = ?, Note = ? " +
                "WHERE ID_CartellaClinica = ?";

        try (PreparedStatement updateStmt = JDBC.getConnection().prepareStatement(updateQuery)) {
            updateStmt.setString(1, cartella.getCF());
            updateStmt.setString(2, cartella.getNome());
            updateStmt.setString(3, cartella.getCognome());
            updateStmt.setString(4, cartella.getTelefone());
            updateStmt.setInt(5, cartella.getLetto());
            updateStmt.setString(6, cartella.getCF_MedicoCurante());
            updateStmt.setDate(7, new java.sql.Date(cartella.getData_modifica().getTime()));
            updateStmt.setString(8, cartella.getNote());
            updateStmt.setInt(9, cartella.getID());

            // Esegui l'aggiornamento
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        // Query per inserire il record nello storico
        String insertStoricoQuery = "INSERT INTO Storico (ID_CartellaClinica, ChiaveLicenza, data_modifica, note) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement storicoStmt = JDBC.getConnection().prepareStatement(insertStoricoQuery)) {
            storicoStmt.setInt(1, cartella.getID());
            storicoStmt.setInt(2, chiaveLicenza);
            storicoStmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime())); // Data odierna
            storicoStmt.setString(4, "Modifica");

            // Esegui l'inserimento
            storicoStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //metodo di ricerca del CF del paziente in base cf inserito nella barra di ricerca del home page
    public List<CartellaClinica> cercaPerCF(String codiceFiscale) throws SQLException{
        List<CartellaClinica> cartellaCliniche = new ArrayList<>();
        String query = "SELECT * FROM CartellaClinica WHERE CF_Paziente = ?";

        try(PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, codiceFiscale);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CartellaClinica cartella = new CartellaClinica();

                    cartella.setID(resultSet.getInt("ID_CartellaClinica"));
                    cartella.setLetto(resultSet.getInt("numero_letto"));
                    cartella.setCF(resultSet.getString("CF_Paziente"));
                    cartella.setData_modifica(resultSet.getDate("Data_creazione"));
                    cartella.setNome(resultSet.getString("Nome_paziente"));
                    cartella.setCognome(resultSet.getString("Cognome_paziente"));
                    cartella.setCF_MedicoCurante(resultSet.getString("CF_Medico_Curante"));
                    cartella.setNote(resultSet.getString("Note"));
                    cartella.setTelefone(resultSet.getString("Telefono"));
                    cartellaCliniche.add(cartella);
                }
            }
        }
        return cartellaCliniche;
    }

}

