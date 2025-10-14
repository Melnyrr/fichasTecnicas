package starter.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import starter.dao.EquipoDAO;
import starter.model.Equipo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controlador completo para la vista de gestión de equipos con control de
 * columnas.
 */
public class EquiposController {

    // Tabla y columnas
    @FXML
    private TableView<Equipo> equiposTable;
    @FXML
    private TableColumn<Equipo, Integer> idColumn;
    @FXML
    private TableColumn<Equipo, String> tipoEquipoColumn;
    @FXML
    private TableColumn<Equipo, String> nombreColumn;
    @FXML
    private TableColumn<Equipo, String> marcaColumn;
    @FXML
    private TableColumn<Equipo, String> modeloColumn;
    @FXML
    private TableColumn<Equipo, String> serialColumn;
    @FXML
    private TableColumn<Equipo, String> codigoInternoColumn;
    @FXML
    private TableColumn<Equipo, String> interfazColumn;
    @FXML
    private TableColumn<Equipo, String> ubicacionColumn;
    @FXML
    private TableColumn<Equipo, String> proveedorColumn;
    @FXML
    private TableColumn<Equipo, LocalDate> fechaColumn;
    @FXML
    private TableColumn<Equipo, String> estadoColumn;
    @FXML
    private TableColumn<Equipo, String> referenciaPartesColumn;

    // Botones principales con iconos
    @FXML
    private Button agregarBtn;
    @FXML
    private Button editarBtn;
    @FXML
    private Button eliminarBtn;
    @FXML
    private Button verDetalleBtn;

    // Botones adicionales
    @FXML
    private Button refreshBtn;
    @FXML
    private Button exportBtn;
    @FXML
    private Button columnToggleBtn;

    // Labels de estado
    @FXML
    private Label totalEquiposLabel;
    @FXML
    private Label equiposActivosLabel;
    @FXML
    private Label equiposMantenimientoLabel;
    @FXML
    private Label columnaInfoLabel;

    // DAO y datos
    private EquipoDAO equipoDAO;
    private ObservableList<Equipo> equiposData;

    // Control de columnas
    private Map<String, TableColumn<Equipo, ?>> columnMap;
    private Map<String, Boolean> columnVisibility;
    private Popup columnMenuPopup;

    public EquiposController() {
        this.equipoDAO = new EquipoDAO();
        this.equiposData = FXCollections.observableArrayList();
        this.columnMap = new LinkedHashMap<>();
        this.columnVisibility = new HashMap<>();
    }

    @FXML
    private void initialize() {
        setupTableColumns();
        setupColumnControl();
        setupTable();
        setupButtons();
        loadEquipos();
        updateStatusLabels();
    }

    /**
     * Configura el control de visibilidad de columnas.
     */
    private void setupColumnControl() {
        // Mapear columnas con nombres descriptivos
        columnMap.put("ID", idColumn);
        columnMap.put("Tipo de Equipo", tipoEquipoColumn);
        columnMap.put("Nombre", nombreColumn);
        columnMap.put("Marca", marcaColumn);
        columnMap.put("Modelo", modeloColumn);
        columnMap.put("Número de Serie", serialColumn);
        columnMap.put("Código Interno", codigoInternoColumn);
        columnMap.put("Interfaces", interfazColumn);
        columnMap.put("Ubicación", ubicacionColumn);
        columnMap.put("Proveedor", proveedorColumn);
        columnMap.put("Fecha de Compra", fechaColumn);
        columnMap.put("Estado", estadoColumn);
        columnMap.put("Referencia/Manual", referenciaPartesColumn);

        // Definir columnas visibles inicialmente
        String[] initiallyVisibleColumns = {"Nombre", "Marca", "Modelo", "Proveedor", "Fecha de Compra", "Estado"};

        // Inicializar visibilidad
        columnMap.forEach((name, column) -> {
            boolean visible = java.util.Arrays.asList(initiallyVisibleColumns).contains(name);
            columnVisibility.put(name, visible);

            // Ocultar columnas que no están en la lista inicial
            if (!visible) {
                equiposTable.getColumns().remove(column);
            }
        });

        // Configurar evento del botón
        columnToggleBtn.setOnAction(e -> showColumnMenu());
    }

    /**
     * Muestra el menú desplegable para controlar columnas.
     */
    private void showColumnMenu() {
        if (columnMenuPopup != null && columnMenuPopup.isShowing()) {
            columnMenuPopup.hide();
            return;
        }

        // Crear el popup
        columnMenuPopup = new Popup();
        columnMenuPopup.setAutoHide(true);
        columnMenuPopup.setHideOnEscape(true);

        // Crear contenido del menú
        VBox menuContent = createColumnMenu();

        columnMenuPopup.getContent().add(menuContent);

        // Mostrar el popup debajo del botón
        var bounds = columnToggleBtn.localToScreen(columnToggleBtn.getBoundsInLocal());
        columnMenuPopup.show(
                columnToggleBtn.getScene().getWindow(),
                bounds.getMinX(),
                bounds.getMaxY() + 5
        );
    }

    /**
     * Crea el contenido del menú de columnas.
     */
    private VBox createColumnMenu() {
        VBox menu = new VBox(4); // Spacing reducido
        menu.setPadding(new Insets(8)); // Padding reducido
        menu.getStyleClass().addAll("column-menu", "elevation-2");
        menu.setMinWidth(200);

        // Título del menú
        Label title = new Label("Mostrar/Ocultar Columnas");
        title.getStyleClass().add("column-menu-title");
        menu.getChildren().add(title);

        // Separador
        Separator separator = new Separator();
        separator.getStyleClass().add("column-menu-separator");
        menu.getChildren().add(separator);

        // Crear checkboxes para cada columna con área clickeable completa
        columnMap.forEach((columnName, column) -> {
            CheckBox checkBox = new CheckBox(columnName);
            checkBox.setSelected(columnVisibility.get(columnName));
            checkBox.getStyleClass().add("column-menu-checkbox");

            // IMPORTANTE: Hacer que el checkbox ocupe toda la anchura
            checkBox.setMaxWidth(Double.MAX_VALUE);
            checkBox.setPrefWidth(200);

            // Deshabilitar columnas esenciales
            if ("ID".equals(columnName) || "Nombre".equals(columnName) || "Tipo de Equipo".equals(columnName)) {
                checkBox.setDisable(true);
                checkBox.setSelected(true);
            }

            checkBox.setOnAction(e -> {
                boolean visible = checkBox.isSelected();
                columnVisibility.put(columnName, visible);
                toggleColumnVisibility(column, visible);
                updateColumnInfo();
            });

            menu.getChildren().add(checkBox);
        });

        // Separador para botones de acción
        Separator actionSeparator = new Separator();
        actionSeparator.getStyleClass().add("column-menu-separator");
        menu.getChildren().add(actionSeparator);

        // Botón mostrar todas - más compacto
        Button showAllBtn = new Button("Mostrar Todas");
        showAllBtn.getStyleClass().addAll("column-menu-button", "success");
        showAllBtn.setMaxWidth(Double.MAX_VALUE);
        showAllBtn.setOnAction(e -> {
            showAllColumns();
            columnMenuPopup.hide();
        });

        // Botón mostrar solo básicas - más compacto
        Button showBasicBtn = new Button("Solo Básicas");
        showBasicBtn.getStyleClass().addAll("column-menu-button", "secondary");
        showBasicBtn.setMaxWidth(Double.MAX_VALUE);
        showBasicBtn.setOnAction(e -> {
            showBasicColumns();
            columnMenuPopup.hide();
        });

        menu.getChildren().addAll(showAllBtn, showBasicBtn);

        return menu;
    }

    /**
     * Alterna la visibilidad de una columna.
     */
    private void toggleColumnVisibility(TableColumn<Equipo, ?> column, boolean visible) {
        if (visible) {
            if (!equiposTable.getColumns().contains(column)) {
                // Encontrar la posición correcta para insertar la columna
                insertColumnInCorrectPosition(column);
            }
        } else {
            equiposTable.getColumns().remove(column);
        }
    }

    /**
     * Inserta una columna en su posición correcta según el orden original.
     */
    private void insertColumnInCorrectPosition(TableColumn<Equipo, ?> columnToInsert) {
        var allColumns = columnMap.values().toArray(new TableColumn[0]);
        var visibleColumns = equiposTable.getColumns();

        // Encontrar el índice correcto
        int targetIndex = 0;
        for (TableColumn<Equipo, ?> col : allColumns) {
            if (col == columnToInsert) {
                break;
            }
            if (visibleColumns.contains(col)) {
                targetIndex++;
            }
        }

        // Insertar en la posición correcta
        if (targetIndex >= visibleColumns.size()) {
            visibleColumns.add(columnToInsert);
        } else {
            visibleColumns.add(targetIndex, columnToInsert);
        }
    }

    /**
     * Muestra todas las columnas.
     */
    private void showAllColumns() {
        columnMap.forEach((name, column) -> {
            columnVisibility.put(name, true);
            if (!equiposTable.getColumns().contains(column)) {
                insertColumnInCorrectPosition(column);
            }
        });
        updateColumnInfo();
    }

    /**
     * Muestra solo las columnas básicas.
     */
    private void showBasicColumns() {
        String[] basicColumns = {"Nombre", "Marca", "Modelo", "Proveedor", "Fecha de Compra", "Estado"};

        // Ocultar todas primero
        equiposTable.getColumns().clear();

        // Mostrar solo las básicas en el orden correcto
        for (String columnName : basicColumns) {
            TableColumn<Equipo, ?> column = columnMap.get(columnName);
            if (column != null) {
                equiposTable.getColumns().add(column);
                columnVisibility.put(columnName, true);
            }
        }

        // Marcar el resto como ocultas
        columnMap.forEach((name, column) -> {
            if (!java.util.Arrays.asList(basicColumns).contains(name)) {
                columnVisibility.put(name, false);
            }
        });

        updateColumnInfo();
    }

    /**
     * Actualiza la información sobre columnas visibles.
     */
    private void updateColumnInfo() {
        long visibleCount = columnVisibility.values().stream().mapToLong(v -> v ? 1 : 0).sum();
        long totalCount = columnMap.size();

        columnaInfoLabel.setText(String.format("Columnas visibles: %d de %d - Usa scroll horizontal para navegar",
                visibleCount, totalCount));
    }

    /**
     * Configura todas las columnas de la tabla vinculándolas con las
     * propiedades del modelo.
     */
    private void setupTableColumns() {
        // Columnas principales
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoEquipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoEquipo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        // Información de identificación
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serie"));
        codigoInternoColumn.setCellValueFactory(new PropertyValueFactory<>("codigoInterno"));

        // Información técnica
        interfazColumn.setCellValueFactory(new PropertyValueFactory<>("interfaz"));

        // Información de ubicación y gestión
        ubicacionColumn.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        proveedorColumn.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        referenciaPartesColumn.setCellValueFactory(new PropertyValueFactory<>("referenciaPartes"));

        // Formateo personalizado
        setupCustomCellFormatting();
    }

    /**
     * Configura formato personalizado para ciertas columnas.
     */
    private void setupCustomCellFormatting() {
        // Columna ID - centrar
        idColumn.setCellFactory(column -> {
            return new TableCell<Equipo, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
        });

        // Columna de fecha - formatear y centrar
        fechaColumn.setCellFactory(column -> {
            return new TableCell<Equipo, LocalDate>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("--");
                        setStyle("-fx-alignment: CENTER; -fx-text-fill: #999999;");
                    } else {
                        setText(formatter.format(item));
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
        });

        // Columna de estado - aplicar estilos según el valor
        estadoColumn.setCellFactory(column -> {
            return new TableCell<Equipo, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setStyle("-fx-alignment: CENTER;");

                        getStyleClass().removeAll("equipment-status-active", "equipment-status-maintenance", "equipment-status-inactive");
                        switch (item.toLowerCase()) {
                            case "activo":
                                getStyleClass().add("equipment-status-active");
                                break;
                            case "en mantenimiento":
                                getStyleClass().add("equipment-status-maintenance");
                                break;
                            case "inactivo":
                                getStyleClass().add("equipment-status-inactive");
                                break;
                        }
                    }
                }
            };
        });

        // Columna de tipo de equipo - aplicar estilo
        tipoEquipoColumn.setCellFactory(column -> {
            return new TableCell<Equipo, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                        setStyle("-fx-font-weight: bold; -fx-alignment: CENTER;");
                    }
                }
            };
        });

        // Columna de interfaces - ajustar texto largo
        interfazColumn.setCellFactory(column -> {
            return new TableCell<Equipo, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setTooltip(null);
                    } else {
                        if (item.length() > 15) {
                            setText(item.substring(0, 12) + "...");
                            setTooltip(new Tooltip(item));
                        } else {
                            setText(item);
                            setTooltip(null);
                        }
                    }
                }
            };
        });

        // Similar para referencia de partes
        referenciaPartesColumn.setCellFactory(column -> {
            return new TableCell<Equipo, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setTooltip(null);
                    } else {
                        if (item.length() > 20) {
                            setText(item.substring(0, 17) + "...");
                            setTooltip(new Tooltip(item));
                        } else {
                            setText(item);
                            setTooltip(null);
                        }
                    }
                }
            };
        });
    }

    /**
     * Configura la tabla de equipos.
     */
    private void setupTable() {
        equiposTable.setItems(equiposData);

        equiposTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    boolean hasSelection = newSelection != null;
                    editarBtn.setDisable(!hasSelection);
                    eliminarBtn.setDisable(!hasSelection);
                    verDetalleBtn.setDisable(!hasSelection);
                }
        );

        editarBtn.setDisable(true);
        eliminarBtn.setDisable(true);
        verDetalleBtn.setDisable(true);

        equiposTable.setPlaceholder(new Label("No hay equipos registrados"));

        // Cambiar a CONSTRAINED_RESIZE_POLICY para distribuir columnas automáticamente
        equiposTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateColumnInfo();
    }

    /**
     * Configura los event handlers de todos los botones.
     */
    private void setupButtons() {
        // Botones principales
        agregarBtn.setOnAction(e -> handleAgregarEquipo());
        editarBtn.setOnAction(e -> handleEditarEquipo());
        eliminarBtn.setOnAction(e -> handleEliminarEquipo());
        verDetalleBtn.setOnAction(e -> handleVerDetalle());

        // Botones adicionales
        refreshBtn.setOnAction(e -> handleRefresh());
        exportBtn.setOnAction(e -> handleExport());
    }

    /**
     * Carga los equipos desde la base de datos.
     */
    private void loadEquipos() {
        try {
            equiposData.clear();
            var equipos = equipoDAO.getAllEquipos();
            equiposData.addAll(equipos);

            System.out.println("Cargados " + equipos.size() + " equipos desde la base de datos");

        } catch (Exception e) {
            System.err.println("Error cargando equipos: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Conexión");
            alert.setHeaderText("No se pudieron cargar los equipos");
            alert.setContentText("Verifique la conexión a la base de datos.\nError: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Actualiza las etiquetas de estado.
     */
    private void updateStatusLabels() {
        try {
            int totalEquipos = equipoDAO.getTotalEquipos();
            int equiposActivos = equipoDAO.getEquiposByEstado("Activo");
            int equiposEnMantenimiento = equipoDAO.getEquiposByEstado("En Mantenimiento");

            totalEquiposLabel.setText("Total de equipos: " + totalEquipos);
            equiposActivosLabel.setText("Activos: " + equiposActivos);
            equiposMantenimientoLabel.setText("En mantenimiento: " + equiposEnMantenimiento);

        } catch (Exception e) {
            System.err.println("Error actualizando etiquetas de estado: " + e.getMessage());
            totalEquiposLabel.setText("Total de equipos: --");
            equiposActivosLabel.setText("Activos: --");
            equiposMantenimientoLabel.setText("En mantenimiento: --");
        }
    }

    // ====================================================================
    // MANEJADORES DE EVENTOS DE BOTONES
    // ====================================================================
    @FXML
    private void handleAgregarEquipo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Agregar Equipo");
        alert.setHeaderText("Nueva funcionalidad");
        alert.setContentText("El formulario para agregar equipos será implementado próximamente.");
        alert.showAndWait();
    }

    @FXML
    private void handleEditarEquipo() {
        Equipo equipoSeleccionado = equiposTable.getSelectionModel().getSelectedItem();

        if (equipoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Editar Equipo");
            alert.setHeaderText("Información del equipo seleccionado:");
            alert.setContentText(buildEquipoDetailText(equipoSeleccionado));
            alert.getDialogPane().setPrefWidth(500);
            alert.getDialogPane().setPrefHeight(400);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEliminarEquipo() {
        Equipo equipoSeleccionado = equiposTable.getSelectionModel().getSelectedItem();

        if (equipoSeleccionado != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmar Eliminación");
            confirmAlert.setHeaderText("¿Está seguro de eliminar este equipo?");
            confirmAlert.setContentText("Equipo: " + equipoSeleccionado.getNombre()
                    + " (" + equipoSeleccionado.getMarca() + " "
                    + equipoSeleccionado.getModelo() + ")");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (equipoDAO.deleteEquipo(equipoSeleccionado.getId())) {
                        equiposData.remove(equipoSeleccionado);
                        updateStatusLabels();

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Eliminación Exitosa");
                        successAlert.setHeaderText("Equipo eliminado");
                        successAlert.setContentText("El equipo ha sido eliminado correctamente.");
                        successAlert.showAndWait();

                        System.out.println("Equipo eliminado correctamente");
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error de Eliminación");
                        errorAlert.setHeaderText("No se pudo eliminar el equipo");
                        errorAlert.setContentText("Ha ocurrido un error al intentar eliminar el equipo.");
                        errorAlert.showAndWait();
                    }
                }
            });
        }
    }

    @FXML
    private void handleVerDetalle() {
        Equipo equipoSeleccionado = equiposTable.getSelectionModel().getSelectedItem();

        if (equipoSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detalle del Equipo");
            alert.setHeaderText("Información completa:");
            alert.setContentText(buildEquipoDetailText(equipoSeleccionado));
            alert.getDialogPane().setPrefWidth(500);
            alert.getDialogPane().setPrefHeight(400);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRefresh() {
        System.out.println("Actualizando lista de equipos...");
        loadEquipos();
        updateStatusLabels();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Actualización");
        info.setHeaderText("Lista actualizada");
        info.setContentText("La lista de equipos ha sido actualizada correctamente.");
        info.showAndWait();
    }

    @FXML
    private void handleExport() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exportar Datos");
        alert.setHeaderText("Funcionalidad de exportación");
        alert.setContentText("La exportación de datos será implementada próximamente.\n\n"
                + "Formatos disponibles próximamente:\n"
                + "• Excel (.xlsx)\n"
                + "• PDF\n"
                + "• CSV");
        alert.showAndWait();
    }

    // ====================================================================
    // MÉTODOS AUXILIARES
    // ====================================================================
    /**
     * Construye un texto detallado con toda la información del equipo.
     */
    private String buildEquipoDetailText(Equipo equipo) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(equipo.getId()).append("\n");
        sb.append("Tipo: ").append(nvl(equipo.getTipoEquipo())).append("\n");
        sb.append("Nombre: ").append(nvl(equipo.getNombre())).append("\n");
        sb.append("Marca: ").append(nvl(equipo.getMarca())).append("\n");
        sb.append("Modelo: ").append(nvl(equipo.getModelo())).append("\n");
        sb.append("Serie: ").append(nvl(equipo.getSerie())).append("\n");
        sb.append("Código Interno: ").append(nvl(equipo.getCodigoInterno())).append("\n");
        sb.append("Interfaces: ").append(nvl(equipo.getInterfaz())).append("\n");
        sb.append("Ubicación: ").append(nvl(equipo.getUbicacion())).append("\n");
        sb.append("Proveedor: ").append(nvl(equipo.getProveedor())).append("\n");
        sb.append("Fecha de Compra: ").append(equipo.getFechaCompra() != null
                ? equipo.getFechaCompra().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "No especificada").append("\n");
        sb.append("Estado: ").append(nvl(equipo.getEstado())).append("\n");
        sb.append("Referencia/Manual: ").append(nvl(equipo.getReferenciaPartes()));

        return sb.toString();
    }

    /**
     * Maneja valores nulos para mostrar texto más amigable.
     */
    private String nvl(String value) {
        return value != null && !value.trim().isEmpty() ? value : "No especificado";
    }

    /**
     * Actualiza la vista después de cambios en los datos.
     */
    public void refresh() {
        loadEquipos();
        updateStatusLabels();
    }

    /**
     * Obtiene el equipo seleccionado en la tabla.
     */
    public Equipo getSelectedEquipo() {
        return equiposTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Obtiene el estado actual de visibilidad de columnas.
     */
    public Map<String, Boolean> getColumnVisibility() {
        return new HashMap<>(columnVisibility);
    }

    /**
     * Restaura el estado de visibilidad de columnas.
     */
    public void setColumnVisibility(Map<String, Boolean> visibility) {
        visibility.forEach((columnName, visible) -> {
            if (columnMap.containsKey(columnName)) {
                columnVisibility.put(columnName, visible);
                toggleColumnVisibility(columnMap.get(columnName), visible);
            }
        });
        updateColumnInfo();
    }
}
