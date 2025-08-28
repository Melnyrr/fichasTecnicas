package starter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import starter.database.DatabaseManager;
import starter.model.Equipo;

/**
 * Clase de Acceso a Datos (DAO) para la entidad Equipo.
 * Proporciona m\u00E9todos CRUD (Crear, Leer, Actualizar, Borrar)
 * para interactuar con la tabla 'equipos' en la base de datos SQLite.
 */
public class EquipoDAO {

    /**
     * Inserta un nuevo equipo en la base de datos.
     * @param equipo El objeto Equipo a insertar.
     * @return true si la inserci\u00F3n fue exitosa, false en caso contrario.
     */
    public boolean insert(Equipo equipo) {
        // La sentencia SQL para la inserci\u00F3n.
        String sql = "INSERT INTO equipos(tipo_equipo, nombre, marca, modelo, serie, codigo_interno, " +
                     "interfaz, ubicacion, proveedor, fecha_compra, estado, referencia_partes) " +
                     "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Se establecen los par\u00E1metros del PreparedStatement con los valores del objeto Equipo.
            pstmt.setString(1, equipo.getTipoEquipo());
            pstmt.setString(2, equipo.getNombre());
            pstmt.setString(3, equipo.getMarca());
            pstmt.setString(4, equipo.getModelo());
            pstmt.setString(5, equipo.getSerie());
            pstmt.setString(6, equipo.getCodigoInterno());
            pstmt.setString(7, equipo.getInterfaz());
            pstmt.setString(8, equipo.getUbicacion());
            pstmt.setString(9, equipo.getProveedor());
            pstmt.setString(10, equipo.getFechaCompra());
            pstmt.setString(11, equipo.getEstado());
            pstmt.setString(12, equipo.getReferenciaPartes());
            
            // Se ejecuta la sentencia de actualizaci\u00F3n.
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar equipo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los equipos de la base de datos.
     * @return Una lista de objetos Equipo.
     */
    public List<Equipo> findAll() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM equipos";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Mapea cada fila del ResultSet a un objeto Equipo.
                Equipo equipo = new Equipo();
                equipo.setId(rs.getInt("id"));
                equipo.setTipoEquipo(rs.getString("tipo_equipo"));
                equipo.setNombre(rs.getString("nombre"));
                equipo.setMarca(rs.getString("marca"));
                equipo.setModelo(rs.getString("modelo"));
                equipo.setSerie(rs.getString("serie"));
                equipo.setCodigoInterno(rs.getString("codigo_interno"));
                equipo.setInterfaz(rs.getString("interfaz"));
                equipo.setUbicacion(rs.getString("ubicacion"));
                equipo.setProveedor(rs.getString("proveedor"));
                equipo.setFechaCompra(rs.getString("fecha_compra"));
                equipo.setEstado(rs.getString("estado"));
                equipo.setReferenciaPartes(rs.getString("referencia_partes"));
                equipos.add(equipo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los equipos: " + e.getMessage());
        }
        return equipos;
    }

    /**
     * Obtiene un equipo por su ID.
     * @param id El ID del equipo.
     * @return El objeto Equipo si se encuentra, o null si no existe.
     */
    public Equipo findById(int id) {
        String sql = "SELECT * FROM equipos WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Mapea la fila a un objeto Equipo si se encuentra un resultado.
                    Equipo equipo = new Equipo();
                    equipo.setId(rs.getInt("id"));
                    equipo.setTipoEquipo(rs.getString("tipo_equipo"));
                    equipo.setNombre(rs.getString("nombre"));
                    equipo.setMarca(rs.getString("marca"));
                    equipo.setModelo(rs.getString("modelo"));
                    equipo.setSerie(rs.getString("serie"));
                    equipo.setCodigoInterno(rs.getString("codigo_interno"));
                    equipo.setInterfaz(rs.getString("interfaz"));
                    equipo.setUbicacion(rs.getString("ubicacion"));
                    equipo.setProveedor(rs.getString("proveedor"));
                    equipo.setFechaCompra(rs.getString("fecha_compra"));
                    equipo.setEstado(rs.getString("estado"));
                    equipo.setReferenciaPartes(rs.getString("referencia_partes"));
                    return equipo;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener equipo por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza un equipo existente en la base de datos.
     * @param equipo El objeto Equipo con los datos actualizados.
     * @return true si la actualizaci\u00F3n fue exitosa, false en caso contrario.
     */
    public boolean update(Equipo equipo) {
        String sql = "UPDATE equipos SET tipo_equipo = ?, nombre = ?, marca = ?, modelo = ?, serie = ?, " +
                     "codigo_interno = ?, interfaz = ?, ubicacion = ?, proveedor = ?, fecha_compra = ?, " +
                     "estado = ?, referencia_partes = ? WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, equipo.getTipoEquipo());
            pstmt.setString(2, equipo.getNombre());
            pstmt.setString(3, equipo.getMarca());
            pstmt.setString(4, equipo.getModelo());
            pstmt.setString(5, equipo.getSerie());
            pstmt.setString(6, equipo.getCodigoInterno());
            pstmt.setString(7, equipo.getInterfaz());
            pstmt.setString(8, equipo.getUbicacion());
            pstmt.setString(9, equipo.getProveedor());
            pstmt.setString(10, equipo.getFechaCompra());
            pstmt.setString(11, equipo.getEstado());
            pstmt.setString(12, equipo.getReferenciaPartes());
            pstmt.setInt(13, equipo.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar equipo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un equipo de la base de datos por su ID.
     * @param id El ID del equipo a eliminar.
     * @return true si la eliminaci\u00F3n fue exitosa, false en caso contrario.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM equipos WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar equipo: " + e.getMessage());
            return false;
        }
    }
}
