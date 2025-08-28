package starter.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador para la vista de gestión de software.
 * Maneja la instalación, actualización y control de licencias de software.
 */
public class SoftwareController {

    @FXML
    private TableView<?> softwareTable;

    @FXML
    private TableColumn<?, ?> idSoftColumn;

    @FXML
    private TableColumn<?, ?> nombreSoftColumn;

    @FXML
    private TableColumn<?, ?> versionColumn;

    @FXML
    private TableColumn<?, ?> fabricanteColumn;

    @FXML
    private TableColumn<?, ?> equipoSoftColumn;

    @FXML
    private TableColumn<?, ?> fechaInstalacionColumn;

    @FXML
    private TableColumn<?, ?> licenciaColumn;

    @FXML
    private Button instalarBtn;

    @FXML
    private Button actualizarBtn;

    @FXML
    private Button desinstalarBtn;

    @FXML
    private Button licenciasBtn;

    @FXML
    private Label softwareInstaladoLabel;

    @FXML
    private Label licenciasPorVencerLabel;

    @FXML
    private Label actualizacionesDisponiblesLabel;

    /**
     * Inicializa el controlador después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        setupTable();
        setupButtons();
        updateStatusLabels();
        loadSoftware();
    }

    /**
     * Configura la tabla de software.
     */
    private void setupTable() {
        // Configurar selección de fila
        softwareTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldSelection, newSelection) -> {
                boolean hasSelection = newSelection != null;
                actualizarBtn.setDisable(!hasSelection);
                desinstalarBtn.setDisable(!hasSelection);
            }
        );

        // Inicialmente deshabilitar botones que requieren selección
        actualizarBtn.setDisable(true);
        desinstalarBtn.setDisable(true);
    }

    /**
     * Configura los event handlers de los botones.
     */
    private void setupButtons() {
        instalarBtn.setOnAction(e -> handleInstalarSoftware());
        actualizarBtn.setOnAction(e -> handleActualizarSoftware());
        desinstalarBtn.setOnAction(e -> handleDesinstalarSoftware());
        licenciasBtn.setOnAction(e -> handleGestionarLicencias());
    }

    /**
     * Actualiza las etiquetas de estado.
     */
    private void updateStatusLabels() {
        // TODO: Implementar lógica real cuando se conecte con la base de datos
        int softwareInstalado = 0;
        int licenciasPorVencer = 0;
        int actualizacionesDisponibles = 0;

        softwareInstaladoLabel.setText("Software instalado: " + softwareInstalado);
        licenciasPorVencerLabel.setText("Licencias por vencer: " + licenciasPorVencer);
        actualizacionesDisponiblesLabel.setText("Actualizaciones disponibles: " + actualizacionesDisponibles);
    }

    /**
     * Carga el software desde la base de datos.
     */
    private void loadSoftware() {
        // TODO: Implementar carga de datos desde la base de datos
        System.out.println("Cargando software desde la base de datos...");
    }

    /**
     * Maneja el evento de instalar nuevo software.
     */
    @FXML
    private void handleInstalarSoftware() {
        System.out.println("Instalar nuevo software");
        // TODO: Abrir ventana de diálogo para instalación de software
    }

    /**
     * Maneja el evento de actualizar software existente.
     */
    @FXML
    private void handleActualizarSoftware() {
        System.out.println("Actualizar software seleccionado");
        // TODO: Verificar actualizaciones disponibles y proceder
    }

    /**
     * Maneja el evento de desinstalar software.
     */
    @FXML
    private void handleDesinstalarSoftware() {
        System.out.println("Desinstalar software seleccionado");
        // TODO: Mostrar confirmación y desinstalar software
    }

    /**
     * Maneja el evento de gestionar licencias.
     */
    @FXML
    private void handleGestionarLicencias() {
        System.out.println("Gestionar licencias de software");
        // TODO: Abrir ventana de gestión de licencias
    }

    /**
     * Actualiza la vista después de cambios en los datos.
     */
    public void refresh() {
        loadSoftware();
        updateStatusLabels();
    }
}