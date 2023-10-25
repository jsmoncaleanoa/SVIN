package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para la conexión a la base de datos MySQL.
 */
public class ConexionMySQL {

    // Librería de MySQL
    private String driver = "com.mysql.cj.jdbc.Driver";

    // Nombre de la base de datos
    private String database = "cybermoto.mwb";

    // Host
    private String hostname = "localhost";

    // Puerto
    private String port = "3306";

    // Ruta de la base de datos (desactivamos el uso de SSL con "?useSSL=false")
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";

    // Nombre de usuario
    private String username = "root";

    // Clave de usuario
    private String password = "";

    /**
     * Establece una conexión con la base de datos MySQL.
     *
     * @return La conexión establecida.
     */
    public Connection conectarMySQL() {
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
