package starter.model;

public class Mantenimiento {
    private int id;
    private int idEquipo;
    private String fecha;
    private String detalle;
    private String tecnico;

    // Constructor vacío
    public Mantenimiento() {}

    // Constructor con todos los campos
    public Mantenimiento(int id, int idEquipo, String fecha, String detalle, String tecnico) {
        this.id = id;
        this.idEquipo = idEquipo;
        this.fecha = fecha;
        this.detalle = detalle;
        this.tecnico = tecnico;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }
}
