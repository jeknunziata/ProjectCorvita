package Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    // Dati di connessione al database
    private static final String DB_URL = "jdbc:mysql://26.160.98.76:3306/CorVitaDatabase";
    private static final String DB_USER = "jk";
    private static final String DB_PASSWORD = "pippo";
    private static Connection connection;

    public static void connect() {
         connection = null;

        try {
            // Connessione al database
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connessione al database stabilita con successo!");

        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
        }
    }

    public static void disconnect() {
        // Chiudi la connessione
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connessione chiusa.");
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
            }
        }

    }

    }
