package starter.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import starter.dao.EquipoDAO;
import starter.model.Equipo;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

/**
 * Controlador para el formulario de agregar/editar equipos.
 */
public class EquipoFormController {
    // Add new field after other @FXML declarations
    @FXML private ScrollPane formScrollPane;

    // Header
    @FXML private Button backButton;
    @FXML private Label formTitleLabel;

    // Imagen
    @FXML private ImageView imagePreview;
    @FXML private Label noImageLabel;
    @FXML private TextField imagenField;
    @FXML private Button selectImageButton;
    @FXML private Button clearImageButton;

    // Información Básica
    @FXML private ComboBox<String> tipoEquipoCombo;
    @FXML private ComboBox<String> estadoCombo;
    @FXML private TextField nombreField;

    // Especificaciones Técnicas
    @FXML private TextField marcaField;
    @FXML private TextField modeloField;
    @FXML private TextField serieField;
    @FXML private TextField codigoInternoField;
    @FXML private TextField interfazField;

    // Compra y Ubicación
    @FXML private TextField proveedorField;
    @FXML private DatePicker fechaCompraPicker;
    @FXML private TextField ubicacionField;

    // Información Adicional
    @FXML private TextArea referenciaPartesArea;

    // Botones de acción
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    // Variables de control
    private EquipoDAO equipoDAO;
    private Equipo equipoActual;
    private boolean modoEdicion = false;
    private Consumer<Equipo> onSaveCallback;
    private Runnable onCancelCallback;

    public EquipoFormController() {
        this.equipoDAO = new EquipoDAO();
    }

    @FXML
    private void initialize() {
        setupComboBoxes();
        setupImageField();
        setupDatePicker();
        setupValidations();
        setupFieldStyles();
    }

    /**
     * Configura estilos adicionales para los campos del formulario.
     */
    private void setupFieldStyles() {
        // Agregar clase form-input a TextArea
        referenciaPartesArea.getStyleClass().add("text-area");
        
        // Agregar clase date-picker al DatePicker
        fechaCompraPicker.getStyleClass().add("date-picker");
    }

    /**
     * Configura los ComboBoxes con valores predefinidos.
     */
    private void setupComboBoxes() {
        // Tipos de equipo
        tipoEquipoCombo.setItems(FXCollections.observableArrayList(
            "Computadora de Escritorio",
            "Laptop",
            "Servidor",
            "Impresora",
            "Scanner",
            "Monitor",
            "Switch",
            "Router",
            "Access Point",
            "Firewall",
            "NAS",
            "UPS",
            "Proyector",
            "Tablet",
            "Teléfono IP",
            "Otro"
        ));
        tipoEquipoCombo.getStyleClass().add("combo-box");

        // Estados
        estadoCombo.setItems(FXCollections.observableArrayList(
            "Activo",
            "En Mantenimiento",
            "Inactivo",
            "En Reparación",
            "Descontinuado"
        ));
        estadoCombo.setValue("Activo"); // Valor por defecto
        estadoCombo.getStyleClass().add("combo-box");
    }

    /**
     * Configura el campo de imagen para cargar automáticamente la vista previa.
     */
    private void setupImageField() {
        imagenField.textProperty().addListener((obs, oldVal, newVal) -> {
            loadImagePreview(newVal);
        });
    }

    /**
     * Configura el DatePicker con formato local.
     */
    private void setupDatePicker() {
        fechaCompraPicker.setConverter(new javafx.util.StringConverter<LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });
    }

    /**
     * Configura validaciones en tiempo real para campos obligatorios.
     */
    private void setupValidations() {
        // Agregar listeners para validar en tiempo real si lo deseas
        // Por ahora, la validación se hará al intentar guardar
    }

    /**
     * Carga una imagen en el preview desde una ruta.
     */
    private void loadImagePreview(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            imagePreview.setImage(null);
            imagePreview.setVisible(false);
            noImageLabel.setVisible(true);
            return;
        }

        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                imagePreview.setImage(image);
                imagePreview.setVisible(true);
                noImageLabel.setVisible(false);
            } else {
                // Intentar cargar desde recursos si la ruta es relativa
                Image image = new Image(imagePath);
                imagePreview.setImage(image);
                imagePreview.setVisible(true);
                noImageLabel.setVisible(false);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
            imagePreview.setImage(null);
            imagePreview.setVisible(false);
            noImageLabel.setVisible(true);
        }
    }

    /**
     * Maneja la selección de imagen desde el sistema de archivos.
     */
    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Equipo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"),
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(selectImageButton.getScene().getWindow());
        if (selectedFile != null) {
            imagenField.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Limpia la imagen seleccionada.
     */
    @FXML
    private void handleClearImage() {
        imagenField.clear();
        imagePreview.setImage(null);
        imagePreview.setVisible(false);
        noImageLabel.setVisible(true);
    }

    /**
     * Maneja el botón de volver/regresar.
     */
    @FXML
    private void handleBack() {
        handleCancel();
    }

    /**
     * Maneja el botón de cancelar.
     */
    @FXML
    private void handleCancel() {
        // Preguntar confirmación si hay cambios sin guardar
        if (hasUnsavedChanges()) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmar Cancelación");
            confirmAlert.setHeaderText("¿Desea cancelar sin guardar?");
            confirmAlert.setContentText("Los cambios realizados se perderán.");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (onCancelCallback != null) {
                        onCancelCallback.run();
                    }
                }
            });
        } else {
            if (onCancelCallback != null) {
                onCancelCallback.run();
            }
        }
    }

    /**
     * Maneja el botón de guardar.
     */
    @FXML
    private void handleSave() {
        if (!validateForm()) {
            return;
        }

        try {
            Equipo equipo = modoEdicion ? equipoActual : new Equipo();

            // Asignar valores del formulario al equipo
            equipo.setImagen(imagenField.getText().trim());
            equipo.setTipoEquipo(tipoEquipoCombo.getValue());
            equipo.setNombre(nombreField.getText().trim());
            equipo.setMarca(marcaField.getText().trim());
            equipo.setModelo(modeloField.getText().trim());
            equipo.setSerie(serieField.getText().trim());
            equipo.setCodigoInterno(codigoInternoField.getText().trim());
            equipo.setInterfaz(interfazField.getText().trim());
            equipo.setUbicacion(ubicacionField.getText().trim());
            equipo.setProveedor(proveedorField.getText().trim());
            equipo.setFechaCompra(fechaCompraPicker.getValue());
            equipo.setEstado(estadoCombo.getValue());
            equipo.setReferenciaPartes(referenciaPartesArea.getText().trim());

            boolean success;
            if (modoEdicion) {
                success = equipoDAO.updateEquipo(equipo);
            } else {
                success = equipoDAO.insertEquipo(equipo);
            }

            if (success) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Operación Exitosa");
                successAlert.setHeaderText(modoEdicion ? "Equipo actualizado" : "Equipo agregado");
                successAlert.setContentText("La operación se completó correctamente.");
                successAlert.showAndWait();

                if (onSaveCallback != null) {
                    onSaveCallback.accept(equipo);
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("No se pudo guardar el equipo");
                errorAlert.setContentText("Ocurrió un error al guardar en la base de datos.");
                errorAlert.showAndWait();
            }

        } catch (Exception e) {
            System.err.println("Error al guardar equipo: " + e.getMessage());
            e.printStackTrace();

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Inesperado");
            errorAlert.setHeaderText("Error al procesar el equipo");
            errorAlert.setContentText("Detalles: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    /**
     * Valida el formulario antes de guardar.
     */
    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        // Validar campos obligatorios
        if (tipoEquipoCombo.getValue() == null || tipoEquipoCombo.getValue().trim().isEmpty()) {
            errors.append("• El tipo de equipo es obligatorio\n");
        }

        if (nombreField.getText() == null || nombreField.getText().trim().isEmpty()) {
            errors.append("• El nombre del equipo es obligatorio\n");
        }

        if (estadoCombo.getValue() == null || estadoCombo.getValue().trim().isEmpty()) {
            errors.append("• El estado es obligatorio\n");
        }

        // Mostrar errores si existen
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validación de Formulario");
            alert.setHeaderText("Por favor complete los campos obligatorios:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }

    /**
     * Verifica si hay cambios sin guardar.
     */
    private boolean hasUnsavedChanges() {
        // Verificar si algún campo tiene datos
        return !nombreField.getText().trim().isEmpty() ||
               !marcaField.getText().trim().isEmpty() ||
               !modeloField.getText().trim().isEmpty() ||
               tipoEquipoCombo.getValue() != null;
    }

    /**
     * Configura el formulario para modo edición.
     */
    public void setEquipoParaEditar(Equipo equipo) {
        if (equipo == null) {
            return;
        }

        this.equipoActual = equipo;
        this.modoEdicion = true;

        // Cambiar título
        formTitleLabel.setText("Editar Equipo");
        saveButton.setText("Actualizar Equipo");

        // Cargar datos del equipo en el formulario
        imagenField.setText(nvl(equipo.getImagen()));
        tipoEquipoCombo.setValue(nvl(equipo.getTipoEquipo()));
        nombreField.setText(nvl(equipo.getNombre()));
        marcaField.setText(nvl(equipo.getMarca()));
        modeloField.setText(nvl(equipo.getModelo()));
        serieField.setText(nvl(equipo.getSerie()));
        codigoInternoField.setText(nvl(equipo.getCodigoInterno()));
        interfazField.setText(nvl(equipo.getInterfaz()));
        ubicacionField.setText(nvl(equipo.getUbicacion()));
        proveedorField.setText(nvl(equipo.getProveedor()));
        fechaCompraPicker.setValue(equipo.getFechaCompra());
        estadoCombo.setValue(nvl(equipo.getEstado()));
        referenciaPartesArea.setText(nvl(equipo.getReferenciaPartes()));

        // Cargar imagen si existe
        if (equipo.getImagen() != null && !equipo.getImagen().trim().isEmpty()) {
            loadImagePreview(equipo.getImagen());
        }
        resetScrollPosition(); // Add this line
    }

    /**
     * Configura el formulario para modo agregar (nuevo equipo).
     */
    public void setModoAgregar() {
        this.equipoActual = null;
        this.modoEdicion = false;
        formTitleLabel.setText("Agregar Nuevo Equipo");
        saveButton.setText("Guardar Equipo");
        limpiarFormulario();
        resetScrollPosition(); // Add this line
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarFormulario() {
        imagenField.clear();
        tipoEquipoCombo.setValue(null);
        nombreField.clear();
        marcaField.clear();
        modeloField.clear();
        serieField.clear();
        codigoInternoField.clear();
        interfazField.clear();
        ubicacionField.clear();
        proveedorField.clear();
        fechaCompraPicker.setValue(null);
        estadoCombo.setValue("Activo");
        referenciaPartesArea.clear();
        
        imagePreview.setImage(null);
        imagePreview.setVisible(false);
        noImageLabel.setVisible(true);
    }
    

    /**
     * Establece el callback que se ejecuta al guardar exitosamente.
     */
    public void setOnSaveCallback(Consumer<Equipo> callback) {
        this.onSaveCallback = callback;
    }

    /**
     * Establece el callback que se ejecuta al cancelar.
     */
    public void setOnCancelCallback(Runnable callback) {
        this.onCancelCallback = callback;
    }

    /**
     * Maneja valores nulos.
     */
    private String nvl(String value) {
        return value != null ? value : "";
    }

    // Add new method
    private void resetScrollPosition() {
        if (formScrollPane != null) {
            javafx.application.Platform.runLater(() -> {
                formScrollPane.setVvalue(0);
                formScrollPane.setHvalue(0);
            });
        }
    }
}