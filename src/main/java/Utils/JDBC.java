package Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static final String DB_URL = "jdbc:mysql://26.160.98.76:3306/CorVitaDatabase";
    private static final String DB_USER = "jk";
    private static final String DB_PASSWORD = "pippo";

    // Unica istanza della connessione
    private static Connection connection;

    private JDBC() {
        // Costruttore privato per evitare istanze multiple
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Connessione al database stabilita con successo!");
            } catch (SQLException e) {
                System.err.println("Errore di connessione al database: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Reset per poter riaprire in futuro
                System.out.println("Connessione chiusa.");
            } catch (SQLException e) {
                System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
            }
        }
    }
}
