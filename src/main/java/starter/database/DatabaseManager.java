package starter.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para gestionar la conexión a la base de datos de SQL Server
 * e inicializar las tablas necesarias.
 */
public class DatabaseManager {

    // Configuración de la base de datos
    private static final String serverName = "localhost";
    private static final int portNumber = 1433;
    private static final String databaseName = "fichasTecnicas";
    private static final String user = "sa";
    private static final String password = "YourStrongPassword123!";

    // URL para conectar a master (para crear la base de datos)
    private static final String MASTER_URL = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=master;encrypt=false;trustServerCertificate=true;integratedSecurity=false;";
    
    // URL para conectar a la base de datos específica   
    private static final String DB_URL = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + databaseName + ";encrypt=false;trustServerCertificate=true;integratedSecurity=false;";

    /**
     * Establece y retorna una conexión a la base de datos específica.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(DB_URL, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver de SQL Server JDBC.");
            throw new SQLException("Driver not found.", e);
        }
    }
    
    /**
     * Establece conexión a la base de datos master.
     */
    private static Connection getMasterConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(MASTER_URL, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver de SQL Server JDBC.");
            throw new SQLException("Driver not found.", e);
        }
    }
    
    /**
     * Crea la base de datos si no existe.
     */
    private static void createDatabaseIfNotExists() {
        String createDatabase = "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'" + databaseName + "') " +
                                "CREATE DATABASE [" + databaseName + "];";
        
        try (Connection conn = getMasterConnection(); Statement stmt = conn.createStatement()) {
            System.out.println("✅ Conectado a master. Verificando/creando base de datos...");
            stmt.execute(createDatabase);
            System.out.println("✅ Base de datos '" + databaseName + "' verificada/creada.");
        } catch (SQLException e) {
            System.err.println("❌ Error creando la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Inicializa la base de datos creando las tablas necesarias.
     */
    public static void initializeDatabase() {
        // Primero crear la base de datos si no existe
        createDatabaseIfNotExists();
        
        // Esperar un poco para asegurar que la base de datos esté lista
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Scripts SQL para crear las tablas
        String createEquipos = """
            IF OBJECT_ID('equipos', 'U') IS NULL
            CREATE TABLE equipos (
                id INT IDENTITY(1,1) PRIMARY KEY,
                imagen NVARCHAR(MAX) NULL,
                tipo_equipo NVARCHAR(255) NOT NULL,
                nombre NVARCHAR(255),
                marca NVARCHAR(255),
                modelo NVARCHAR(255),
                serie NVARCHAR(255),
                codigo_interno NVARCHAR(255),
                interfaz NVARCHAR(255),
                ubicacion NVARCHAR(255),
                proveedor NVARCHAR(255),
                fecha_compra DATE,
                estado NVARCHAR(255),
                referencia_partes NVARCHAR(255)
            );
        """;

        String createMantenimientos = """
            IF OBJECT_ID('mantenimientos', 'U') IS NULL
            CREATE TABLE mantenimientos (
                id INT IDENTITY(1,1) PRIMARY KEY,
                id_equipo INT NOT NULL,
                fecha DATETIME NOT NULL,
                detalle NVARCHAR(MAX),
                tecnico NVARCHAR(255),
                FOREIGN KEY (id_equipo) REFERENCES equipos(id) ON DELETE CASCADE
            );
        """;

        String createSoftwares = """
            IF OBJECT_ID('softwares', 'U') IS NULL
            CREATE TABLE softwares (
                id INT IDENTITY(1,1) PRIMARY KEY,
                id_equipo INT NOT NULL,
                nombre NVARCHAR(255),
                version NVARCHAR(255),
                licencia NVARCHAR(255),
                FOREIGN KEY (id_equipo) REFERENCES equipos(id) ON DELETE CASCADE
            );
        """;

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            System.out.println("✅ Conexión exitosa a " + databaseName + ". Inicializando tablas...");
            stmt.execute(createEquipos);
            stmt.execute(createMantenimientos);
            stmt.execute(createSoftwares);
            System.out.println("✅ Base de datos inicializada correctamente. Tablas creadas o ya existentes.");
        } catch (SQLException e) {
            System.err.println("❌ Error inicializando las tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Método de prueba para verificar la conexión y la inicialización.
     */
    public static void main(String[] args) {
        initializeDatabase();
    }
}
