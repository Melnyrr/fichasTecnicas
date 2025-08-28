package starter.model;

public class Equipo {
    private int id;
    private String tipoEquipo;
    private String nombre;
    private String marca;
    private String modelo;
    private String serie;
    private String codigoInterno;
    private String interfaz;
    private String ubicacion;
    private String proveedor;
    private String fechaCompra;
    private String estado;
    private String referenciaPartes;

    // Constructor vacío
    public Equipo() {}

    // Constructor con todos los campos
    public Equipo(int id, String tipoEquipo, String nombre, String marca, String modelo, String serie,
                  String codigoInterno, String interfaz, String ubicacion, String proveedor,
                  String fechaCompra, String estado, String referenciaPartes) {
        this.id = id;
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

    public String getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(String fechaCompra) { this.fechaCompra = fechaCompra; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getReferenciaPartes() { return referenciaPartes; }
    public void setReferenciaPartes(String referenciaPartes) { this.referenciaPartes = referenciaPartes; }
}
