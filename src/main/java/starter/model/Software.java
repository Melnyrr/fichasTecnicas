package starter.model;

public class Software {
    private int id;
    private int idEquipo;
    private String nombre;
    private String version;
    private String licencia;

    // Constructor vacío
    public Software() {}

    // Constructor con todos los campos
    public Software(int id, int idEquipo, String nombre, String version, String licencia) {
        this.id = id;
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.version = version;
        this.licencia = licencia;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getLicencia() { return licencia; }
    public void setLicencia(String licencia) { this.licencia = licencia; }
}
