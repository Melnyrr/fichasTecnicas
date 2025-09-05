package starter.model;

import java.time.LocalDate;

/**
 * Modelo que representa un equipo en el sistema de fichas técnicas.
 * Esta clase contiene todos los atributos que corresponden a la tabla 'equipos' en la base de datos.
 */
public class Equipo {
    
    private int id;
    private String imagen;
    private String tipoEquipo;
    private String nombre;
    private String marca;
    private String modelo;
    private String serie;
    private String codigoInterno;
    private String interfaz;
    private String ubicacion;
    private String proveedor;
    private LocalDate fechaCompra;
    private String estado;
    private String referenciaPartes;
    
    // Constructor vacío
    public Equipo() {}
    
    // Constructor completo
    public Equipo(int id, String imagen, String tipoEquipo, String nombre, String marca, 
                  String modelo, String serie, String codigoInterno, String interfaz, 
                  String ubicacion, String proveedor, LocalDate fechaCompra, 
                  String estado, String referenciaPartes) {
        this.id = id;
        this.imagen = imagen;
        this.tipoEquipo = tipoEquipo;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.codigoInterno = codigoInterno;
        this.interfaz = interfaz;
        this.ubicacion = ubicacion;
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.referenciaPartes = referenciaPartes;
    }
    
    // Constructor sin ID (para nuevos equipos)
    public Equipo(String imagen, String tipoEquipo, String nombre, String marca, 
                  String modelo, String serie, String codigoInterno, String interfaz, 
                  String ubicacion, String proveedor, LocalDate fechaCompra, 
                  String estado, String referenciaPartes) {
        this.imagen = imagen;
        this.tipoEquipo = tipoEquipo;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.codigoInterno = codigoInterno;
        this.interfaz = interfaz;
        this.ubicacion = ubicacion;
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.referenciaPartes = referenciaPartes;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    
    public String getTipoEquipo() { return tipoEquipo; }
    public void setTipoEquipo(String tipoEquipo) { this.tipoEquipo = tipoEquipo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    
    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }
    
    public String getInterfaz() { return interfaz; }
    public void setInterfaz(String interfaz) { this.interfaz = interfaz; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    
    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getReferenciaPartes() { return referenciaPartes; }
    public void setReferenciaPartes(String referenciaPartes) { this.referenciaPartes = referenciaPartes; }
    
    @Override
    public String toString() {
        return "Equipo{" +
               "id=" + id +
               ", tipoEquipo='" + tipoEquipo + '\'' +
               ", nombre='" + nombre + '\'' +
               ", marca='" + marca + '\'' +
               ", modelo='" + modelo + '\'' +
               ", serie='" + serie + '\'' +
               ", estado='" + estado + '\'' +
               ", fechaCompra=" + fechaCompra +
               '}';
    }
    
    /**
     * Método de utilidad para obtener un resumen del equipo.
     */
    public String getResumen() {
        return String.format("%s %s - %s", 
            marca != null ? marca : "Sin marca",
            modelo != null ? modelo : "Sin modelo", 
            nombre != null ? nombre : "Sin nombre");
    }
    
    /**
     * Valida que el equipo tenga los campos obligatorios.
     */
    public boolean isValid() {
        return tipoEquipo != null && !tipoEquipo.trim().isEmpty() &&
               nombre != null && !nombre.trim().isEmpty();
    }
}