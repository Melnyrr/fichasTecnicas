package starter.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador para la vista de gestión de mantenimientos.
 * Maneja la programación, registro y seguimiento de mantenimientos.
 */
public class MantenimientosController {

    @FXML
    private TableView<?> mantenimientosTable;

    @FXML
    private TableColumn<?, ?> idMantColumn;

    @FXML
    private TableColumn<?, ?> equipoMantColumn;

    @FXML
    private TableColumn<?, ?> tipoMantColumn;

    @FXML
    private TableColumn<?, ?> fechaProgramadaColumn;

    @FXML
    private TableColumn<?, ?> fechaRealizadaColumn;

    @FXML
    private TableColumn<?, ?> estadoMantColumn;

    @FXML
    private TableColumn<?, ?> tecnicoColumn;

    @FXML
    private Button nuevoBtn;

    @FXML
    private Button programarBtn;

    @FXML
    private Button historialBtn;

    @FXML
    private Label mantenimientosPendientesLabel;

    @FXML
    private Label mantenimientosCompletadosLabel;

    @FXML
    private Label proximosMantenimientosLabel;

    /**
     * Inicializa el controlador después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        setupTable();
        setupButtons();
        updateStatusLabels();
        loadMantenimientos();
    }

    /**
     * Configura la tabla de mantenimientos.
     */
    private void setupTable() {
        // Configurar selección de fila para habilitar/deshabilitar acciones
        mantenimientosTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldSelection, newSelection) -> {
                // Aquí puedes agregar lógica para habilitar botones específicos
                // basándose en el estado del mantenimiento seleccionado
            }
        );
    }

    /**
     * Configura los event handlers de los botones.
     */
    private void setupButtons() {
        nuevoBtn.setOnAction(e -> handleNuevoMantenimiento());
        programarBtn.setOnAction(e -> handleProgramarMantenimiento());
        historialBtn.setOnAction(e -> handleVerHistorial());
    }

    /**
     * Actualiza las etiquetas de estado.
     */
    private void updateStatusLabels() {
        // TODO: Implementar lógica real cuando se conecte con la base de datos
        int mantenimientosPendientes = 0;
        int mantenimientosCompletados = 0;
        int proximosMantenimientos = 0;

        mantenimientosPendientesLabel.setText("Pendientes: " + mantenimientosPendientes);
        mantenimientosCompletadosLabel.setText("Completados este mes: " + mantenimientosCompletados);
        proximosMantenimientosLabel.setText("Próximos 7 días: " + proximosMantenimientos);
    }

    /**
     * Carga los mantenimientos desde la base de datos.
     */
    private void loadMantenimientos() {
        // TODO: Implementar carga de datos desde la base de datos
        System.out.println("Cargando mantenimientos desde la base de datos...");
    }

    /**
     * Maneja el evento de crear un nuevo mantenimiento.
     */
    @FXML
    private void handleNuevoMantenimiento() {
        System.out.println("Crear nuevo mantenimiento");
        // TODO: Abrir ventana de diálogo para nuevo mantenimiento
    }

    /**
     * Maneja el evento de programar un mantenimiento.
     */
    @FXML
    private void handleProgramarMantenimiento() {
        System.out.println("Programar mantenimiento");
        // TODO: Abrir ventana de programación de mantenimientos
    }

    /**
     * Maneja el evento de ver el historial de mantenimientos.
     */
    @FXML
    private void handleVerHistorial() {
        System.out.println("Ver historial de mantenimientos");
        // TODO: Abrir ventana de historial con filtros
    }

    /**
     * Actualiza la vista después de cambios en los datos.
     */
    public void refresh() {
        loadMantenimientos();
        updateStatusLabels();
    }
}