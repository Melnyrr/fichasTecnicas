package starter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import starter.database.DatabaseManager;
import starter.model.Software;

/**
 * Clase de Acceso a Datos (DAO) para la entidad Software.
 * Proporciona m\u00E9todos CRUD para interactuar con la tabla 'softwares'
 * en la base de datos SQLite.
 */
public class SoftwareDAO {

    /**
     * Inserta un nuevo registro de software en la base de datos.
     * @param software El objeto Software a insertar.
     * @return true si la inserci\u00F3n fue exitosa, false en caso contrario.
     */
    public boolean insert(Software software) {
        String sql = "INSERT INTO softwares(id_equipo, nombre, version, licencia) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, software.getIdEquipo());
            pstmt.setString(2, software.getNombre());
            pstmt.setString(3, software.getVersion());
            pstmt.setString(4, software.getLicencia());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar software: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los registros de software de la base de datos.
     * @return Una lista de objetos Software.
     */
    public List<Software> findAll() {
        List<Software> softwares = new ArrayList<>();
        String sql = "SELECT * FROM softwares";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Software software = new Software();
                software.setId(rs.getInt("id"));
                software.setIdEquipo(rs.getInt("id_equipo"));
                software.setNombre(rs.getString("nombre"));
                software.setVersion(rs.getString("version"));
                software.setLicencia(rs.getString("licencia"));
                softwares.add(software);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los softwares: " + e.getMessage());
        }
        return softwares;
    }
    
    /**
     * Obtiene una lista de registros de software por el ID de un equipo.
     * @param idEquipo El ID del equipo.
     * @return Una lista de objetos Software.
     */
    public List<Software> findByEquipoId(int idEquipo) {
        List<Software> softwares = new ArrayList<>();
        String sql = "SELECT * FROM softwares WHERE id_equipo = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idEquipo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Software software = new Software();
                    software.setId(rs.getInt("id"));
                    software.setIdEquipo(rs.getInt("id_equipo"));
                    software.setNombre(rs.getString("nombre"));
                    software.setVersion(rs.getString("version"));
                    software.setLicencia(rs.getString("licencia"));
                    softwares.add(software);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener softwares por ID de equipo: " + e.getMessage());
        }
        return softwares;
    }

    /**
     * Actualiza un registro de software existente en la base de datos.
     * @param software El objeto Software con los datos actualizados.
     * @return true si la actualizaci\u00F3n fue exitosa, false en caso contrario.
     */
    public boolean update(Software software) {
        String sql = "UPDATE softwares SET id_equipo = ?, nombre = ?, version = ?, licencia = ? WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, software.getIdEquipo());
            pstmt.setString(2, software.getNombre());
            pstmt.setString(3, software.getVersion());
            pstmt.setString(4, software.getLicencia());
            pstmt.setInt(5, software.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar software: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un registro de software de la base de datos por su ID.
     * @param id El ID del software a eliminar.
     * @return true si la eliminaci\u00F3n fue exitosa, false en caso contrario.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM softwares WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar software: " + e.getMessage());
            return false;
        }
    }
}
