package starter.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de gestión de la base de datos SQLite.
 * Se encarga de la conexión e inicialización de la base de datos,
 * asegurando que el archivo 'inventario.db' y sus tablas existan.
 */
public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:inventario.db";

    /**
     * Establece y retorna una conexión a la base de datos SQLite.
     * @return Un objeto Connection.
     * @throws SQLException si ocurre un error al conectar.
     */
    public static Connection getConnection() throws SQLException {
        // La conexión se cierra automáticamente al salir del bloque try-with-resources.
        return DriverManager.getConnection(URL);
    }

    /**
     * Inicializa la base de datos creando las tablas 'equipos',
     * 'mantenimientos' y 'softwares' si aún no existen.
     */
    public static void initializeDatabase() {
        String createEquipos = """
            CREATE TABLE IF NOT EXISTS equipos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                tipo_equipo TEXT NOT NULL,
                nombre TEXT,
                marca TEXT,
                modelo TEXT,
                serie TEXT,
                codigo_interno TEXT,
                interfaz TEXT,
                ubicacion TEXT,
                proveedor TEXT,
                fecha_compra TEXT,
                estado TEXT,
                referencia_partes TEXT
            );
        """;

        String createMantenimientos = """
            CREATE TABLE IF NOT EXISTS mantenimientos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_equipo INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                detalle TEXT,
                tecnico TEXT,
                FOREIGN KEY (id_equipo) REFERENCES equipos(id) ON DELETE CASCADE
            );
        """;

        String createSoftwares = """
            CREATE TABLE IF NOT EXISTS softwares (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_equipo INTEGER NOT NULL,
                nombre TEXT,
                version TEXT,
                licencia TEXT,
                FOREIGN KEY (id_equipo) REFERENCES equipos(id) ON DELETE CASCADE
            );
        """;

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createEquipos);
            stmt.execute(createMantenimientos);
            stmt.execute(createSoftwares);
            System.out.println("✅ Base de datos inicializada correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error inicializando la base de datos: " + e.getMessage());
        }
    }

    /**
     * Método main para probar la inicialización de la base de datos.
     * Al ejecutar esta clase, se creará o verificará el archivo 'inventario.db'.
     */
    public static void main(String[] args) {
        initializeDatabase();
    }
}
