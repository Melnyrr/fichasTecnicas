package starter.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador para la vista de gestión de equipos.
 * Maneja la interacción con la tabla de equipos y los botones de acción.
 */
public class EquiposController {

    @FXML
    private TableView<?> equiposTable;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> nombreColumn;

    @FXML
    private TableColumn<?, ?> marcaColumn;

    @FXML
    private TableColumn<?, ?> modeloColumn;

    @FXML
    private TableColumn<?, ?> serialColumn;

    @FXML
    private TableColumn<?, ?> estadoColumn;

    @FXML
    private TableColumn<?, ?> fechaColumn;

    @FXML
    private Button agregarBtn;

    @FXML
    private Button editarBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private Label totalEquiposLabel;

    @FXML
    private Label equiposActivosLabel;

    @FXML
    private Label equiposMantenimientoLabel;

    /**
     * Inicializa el controlador después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        setupTable();
        setupButtons();
        updateStatusLabels();
        loadEquipos();
    }

    /**
     * Configura la tabla de equipos.
     */
    private void setupTable() {
        // Configurar selección de fila
        equiposTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldSelection, newSelection) -> {
                boolean hasSelection = newSelection != null;
                editarBtn.setDisable(!hasSelection);
                eliminarBtn.setDisable(!hasSelection);
            }
        );

        // Inicialmente deshabilitar botones de edición y eliminación
        editarBtn.setDisable(true);
        eliminarBtn.setDisable(true);
    }

    /**
     * Configura los event handlers de los botones.
     */
    private void setupButtons() {
        agregarBtn.setOnAction(e -> handleAgregarEquipo());
        editarBtn.setOnAction(e -> handleEditarEquipo());
        eliminarBtn.setOnAction(e -> handleEliminarEquipo());
    }

    /**
     * Actualiza las etiquetas de estado.
     */
    private void updateStatusLabels() {
        // TODO: Implementar lógica real cuando se conecte con la base de datos
        int totalEquipos = 0;  // Obtener de la base de datos
        int equiposActivos = 0;
        int equiposEnMantenimiento = 0;

        totalEquiposLabel.setText("Total de equipos: " + totalEquipos);
        equiposActivosLabel.setText("Activos: " + equiposActivos);
        equiposMantenimientoLabel.setText("En mantenimiento: " + equiposEnMantenimiento);
    }

    /**
     * Carga los equipos desde la base de datos.
     */
    private void loadEquipos() {
        // TODO: Implementar carga de datos desde la base de datos
        System.out.println("Cargando equipos desde la base de datos...");
    }

    /**
     * Maneja el evento de agregar un nuevo equipo.
     */
    @FXML
    private void handleAgregarEquipo() {
        System.out.println("Agregar nuevo equipo");
        // TODO: Abrir ventana de diálogo para agregar equipo
    }

    /**
     * Maneja el evento de editar un equipo existente.
     */
    @FXML
    private void handleEditarEquipo() {
        System.out.println("Editar equipo seleccionado");
        // TODO: Abrir ventana de diálogo para editar equipo
    }

    /**
     * Maneja el evento de eliminar un equipo.
     */
    @FXML
    private void handleEliminarEquipo() {
        System.out.println("Eliminar equipo seleccionado");
        // TODO: Mostrar confirmación y eliminar equipo
    }

    /**
     * Actualiza la vista después de cambios en los datos.
     */
    public void refresh() {
        loadEquipos();
        updateStatusLabels();
    }
}