package starter.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import starter.database.DatabaseManager;
import starter.model.Equipo;

/**
 * Data Access Object para la entidad Equipo.
 * Maneja todas las operaciones de base de datos relacionadas con equipos.
 */
public class EquipoDAO {
    
    /**
     * Obtiene todos los equipos de la base de datos.
     * @return Lista de todos los equipos
     */
    public List<Equipo> getAllEquipos() {
        List<Equipo> equipos = new ArrayList<>();
        String query = """
            SELECT id, imagen, tipo_equipo, nombre, marca, modelo, serie, 
                   codigo_interno, interfaz, ubicacion, proveedor, fecha_compra, 
                   estado, referencia_partes 
            FROM equipos 
            ORDER BY id ASC
        """;
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Equipo equipo = new Equipo();
                equipo.setId(rs.getInt("id"));
                equipo.setImagen(rs.getString("imagen"));
                equipo.setTipoEquipo(rs.getString("tipo_equipo"));
                equipo.setNombre(rs.getString("nombre"));
                equipo.setMarca(rs.getString("marca"));
                equipo.setModelo(rs.getString("modelo"));
                equipo.setSerie(rs.getString("serie"));
                equipo.setCodigoInterno(rs.getString("codigo_interno"));
                equipo.setInterfaz(rs.getString("interfaz"));
                equipo.setUbicacion(rs.getString("ubicacion"));
                equipo.setProveedor(rs.getString("proveedor"));
                
                // Manejar fecha que puede ser null
                Date fechaCompra = rs.getDate("fecha_compra");
                if (fechaCompra != null) {
                    equipo.setFechaCompra(fechaCompra.toLocalDate());
                }
                
                equipo.setEstado(rs.getString("estado"));
                equipo.setReferenciaPartes(rs.getString("referencia_partes"));
                
                equipos.add(equipo);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener equipos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return equipos;
    }
    
    /**
     * Obtiene un equipo por su ID.
     * @param id ID del equipo a buscar
     * @return El equipo encontrado o null si no existe
     */
    public Equipo getEquipoById(int id) {
        String query = """
            SELECT id, imagen, tipo_equipo, nombre, marca, modelo, serie, 
                   codigo_interno, interfaz, ubicacion, proveedor, fecha_compra, 
                   estado, referencia_partes 
            FROM equipos 
            WHERE id = ?
        """;
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Equipo equipo = new Equipo();
                    equipo.setId(rs.getInt("id"));
                    equipo.setImagen(rs.getString("imagen"));
                    equipo.setTipoEquipo(rs.getString("tipo_equipo"));
                    equipo.setNombre(rs.getString("nombre"));
                    equipo.setMarca(rs.getString("marca"));
                    equipo.setModelo(rs.getString("modelo"));
                    equipo.setSerie(rs.getString("serie"));
                    equipo.setCodigoInterno(rs.getString("codigo_interno"));
                    equipo.setInterfaz(rs.getString("interfaz"));
                    equipo.setUbicacion(rs.getString("ubicacion"));
                    equipo.setProveedor(rs.getString("proveedor"));
                    
                    // Manejar fecha que puede ser null
                    Date fechaCompra = rs.getDate("fecha_compra");
                    if (fechaCompra != null) {
                        equipo.setFechaCompra(fechaCompra.toLocalDate());
                    }
                    
                    equipo.setEstado(rs.getString("estado"));
                    equipo.setReferenciaPartes(rs.getString("referencia_partes"));
                    
                    return equipo;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener equipo por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Inserta un nuevo equipo en la base de datos.
     * @param equipo El equipo a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertEquipo(Equipo equipo) {
        String query = """
            INSERT INTO equipos (imagen, tipo_equipo, nombre, marca, modelo, serie, 
                               codigo_interno, interfaz, ubicacion, proveedor, fecha_compra, 
                               estado, referencia_partes)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, equipo.getImagen());
            pstmt.setString(2, equipo.getTipoEquipo());
            pstmt.setString(3, equipo.getNombre());
            pstmt.setString(4, equipo.getMarca());
            pstmt.setString(5, equipo.getModelo());
            pstmt.setString(6, equipo.getSerie());
            pstmt.setString(7, equipo.getCodigoInterno());
            pstmt.setString(8, equipo.getInterfaz());
            pstmt.setString(9, equipo.getUbicacion());
            pstmt.setString(10, equipo.getProveedor());
            
            // Manejar fecha que puede ser null
            if (equipo.getFechaCompra() != null) {
                pstmt.setDate(11, Date.valueOf(equipo.getFechaCompra()));
            } else {
                pstmt.setNull(11, Types.DATE);
            }
            
            pstmt.setString(12, equipo.getEstado());
            pstmt.setString(13, equipo.getReferenciaPartes());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar equipo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza un equipo existente en la base de datos.
     * @param equipo El equipo con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean updateEquipo(Equipo equipo) {
        String query = """
            UPDATE equipos 
            SET imagen = ?, tipo_equipo = ?, nombre = ?, marca = ?, modelo = ?, 
                serie = ?, codigo_interno = ?, interfaz = ?, ubicacion = ?, 
                proveedor = ?, fecha_compra = ?, estado = ?, referencia_partes = ?
            WHERE id = ?
        """;
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, equipo.getImagen());
            pstmt.setString(2, equipo.getTipoEquipo());
            pstmt.setString(3, equipo.getNombre());
            pstmt.setString(4, equipo.getMarca());
            pstmt.setString(5, equipo.getModelo());
            pstmt.setString(6, equipo.getSerie());
            pstmt.setString(7, equipo.getCodigoInterno());
            pstmt.setString(8, equipo.getInterfaz());
            pstmt.setString(9, equipo.getUbicacion());
            pstmt.setString(10, equipo.getProveedor());
            
            // Manejar fecha que puede ser null
            if (equipo.getFechaCompra() != null) {
                pstmt.setDate(11, Date.valueOf(equipo.getFechaCompra()));
            } else {
                pstmt.setNull(11, Types.DATE);
            }
            
            pstmt.setString(12, equipo.getEstado());
            pstmt.setString(13, equipo.getReferenciaPartes());
            pstmt.setInt(14, equipo.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar equipo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Elimina un equipo de la base de datos.
     * @param id ID del equipo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean deleteEquipo(int id) {
        String query = "DELETE FROM equipos WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar equipo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene la cantidad total de equipos.
     * @return Número total de equipos
     */
    public int getTotalEquipos() {
        String query = "SELECT COUNT(*) FROM equipos";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener total de equipos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Obtiene la cantidad de equipos por estado.
     * @param estado Estado a contar
     * @return Número de equipos con el estado especificado
     */
    public int getEquiposByEstado(String estado) {
        String query = "SELECT COUNT(*) FROM equipos WHERE estado = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, estado);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener equipos por estado: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
}