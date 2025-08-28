package starter.dao;

import starter.database.DatabaseManager;
import starter.model.Mantenimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Acceso a Datos (DAO) para la entidad Mantenimiento.
 * Proporciona métodos CRUD para interactuar con la tabla 'mantenimientos'
 * en la base de datos SQLite.
 */
public class MantenimientoDAO {

    /**
     * Inserta un nuevo mantenimiento en la base de datos.
     * @param mantenimiento El objeto Mantenimiento a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insert(Mantenimiento mantenimiento) {
        String sql = "INSERT INTO mantenimientos(id_equipo, fecha, detalle, tecnico) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, mantenimiento.getIdEquipo());
            pstmt.setString(2, mantenimiento.getFecha());
            pstmt.setString(3, mantenimiento.getDetalle());
            pstmt.setString(4, mantenimiento.getTecnico());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los mantenimientos de la base de datos.
     * @return Una lista de objetos Mantenimiento.
     */
    public List<Mantenimiento> findAll() {
        List<Mantenimiento> mantenimientos = new ArrayList<>();
        String sql = "SELECT * FROM mantenimientos";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Mantenimiento mantenimiento = new Mantenimiento();
                mantenimiento.setId(rs.getInt("id"));
                mantenimiento.setIdEquipo(rs.getInt("id_equipo"));
                mantenimiento.setFecha(rs.getString("fecha"));
                mantenimiento.setDetalle(rs.getString("detalle"));
                mantenimiento.setTecnico(rs.getString("tecnico"));
                mantenimientos.add(mantenimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los mantenimientos: " + e.getMessage());
        }
        return mantenimientos;
    }
    
    /**
     * Obtiene una lista de mantenimientos por el ID de un equipo.
     * @param idEquipo El ID del equipo.
     * @return Una lista de objetos Mantenimiento.
     */
    public List<Mantenimiento> findByEquipoId(int idEquipo) {
        List<Mantenimiento> mantenimientos = new ArrayList<>();
        String sql = "SELECT * FROM mantenimientos WHERE id_equipo = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idEquipo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Mantenimiento mantenimiento = new Mantenimiento();
                    mantenimiento.setId(rs.getInt("id"));
                    mantenimiento.setIdEquipo(rs.getInt("id_equipo"));
                    mantenimiento.setFecha(rs.getString("fecha"));
                    mantenimiento.setDetalle(rs.getString("detalle"));
                    mantenimiento.setTecnico(rs.getString("tecnico"));
                    mantenimientos.add(mantenimiento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mantenimientos por ID de equipo: " + e.getMessage());
        }
        return mantenimientos;
    }

    /**
     * Actualiza un mantenimiento existente en la base de datos.
     * @param mantenimiento El objeto Mantenimiento con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean update(Mantenimiento mantenimiento) {
        String sql = "UPDATE mantenimientos SET id_equipo = ?, fecha = ?, detalle = ?, tecnico = ? WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, mantenimiento.getIdEquipo());
            pstmt.setString(2, mantenimiento.getFecha());
            pstmt.setString(3, mantenimiento.getDetalle());
            pstmt.setString(4, mantenimiento.getTecnico());
            pstmt.setInt(5, mantenimiento.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un mantenimiento de la base de datos por su ID.
     * @param id El ID del mantenimiento a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM mantenimientos WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar mantenimiento: " + e.getMessage());
            return false;
        }
    }
}
