package starter.ui;

import java.io.IOException;

import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * Controlador para la ventana principal de la aplicación.
 * Gestiona la navegación entre las diferentes vistas usando un TabPane con estilo floating.
 */
public class MainController {

    @FXML
    private BorderPane rootPane;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab equiposTab;

    @FXML
    private Tab mantenimientosTab;

    @FXML
    private Tab softwareTab;

    /**
     * El método initialize se ejecuta automáticamente después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        // Configurar el estilo classic para el TabPane
        mainTabPane.getStyleClass().add(Styles.TABS_CLASSIC);
        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Cargar el contenido inicial de cada tab
        loadTabContent(equiposTab, "/fxml/EquiposView.fxml");
        loadTabContent(mantenimientosTab, "/fxml/MantenimientosView.fxml");
        loadTabContent(softwareTab, "/fxml/SoftwareView.fxml");
        
        // Seleccionar la primera tab por defecto
        mainTabPane.getSelectionModel().select(equiposTab);
        
        // Listener para cambios de tab (opcional, para lógica adicional)
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                System.out.println("Cambiando a tab: " + newTab.getText());
                // Aquí puedes agregar lógica adicional cuando se cambie de tab
            }
        });
    }

    /**
     * Carga un archivo FXML en una tab específica.
     * @param tab La tab donde cargar el contenido.
     * @param fxmlPath La ruta del archivo FXML a cargar.
     */
    private void loadTabContent(Tab tab, String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            tab.setContent(view);
        } catch (IOException e) {
            System.err.println("Error al cargar la vista FXML: " + fxmlPath);
            e.printStackTrace();
            
            // Mostrar un mensaje de error en lugar de la vista
            showErrorInTab(tab, "No se pudo cargar la vista: " + fxmlPath);
        }
    }

    /**
     * Muestra un mensaje de error en una tab cuando no se puede cargar su contenido.
     */
    private void showErrorInTab(Tab tab, String errorMessage) {
        try {
            javafx.scene.control.Label errorLabel = new javafx.scene.control.Label(errorMessage);
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-padding: 20px;");
            tab.setContent(errorLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Métodos públicos para navegación programática (opcional)
    
    public void navigateToEquipos() {
        mainTabPane.getSelectionModel().select(equiposTab);
    }

    public void navigateToMantenimientos() {
        mainTabPane.getSelectionModel().select(mantenimientosTab);
    }

    public void navigateToSoftware() {
        mainTabPane.getSelectionModel().select(softwareTab);
    }

    /**
     * Recargar el contenido de una tab específica.
     * @param tabName Nombre de la tab ("equipos", "mantenimientos", "software")
     */
    public void reloadTab(String tabName) {
        switch (tabName.toLowerCase()) {
            case "equipos":
                loadTabContent(equiposTab, "/fxml/EquiposView.fxml");
                break;
            case "mantenimientos":
                loadTabContent(mantenimientosTab, "/fxml/MantenimientosView.fxml");
                break;
            case "software":
                loadTabContent(softwareTab, "/fxml/SoftwareView.fxml");
                break;
            default:
                System.err.println("Tab desconocida: " + tabName);
        }
    }
}