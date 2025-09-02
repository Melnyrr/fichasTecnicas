package starter.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controlador para la ventana principal de la aplicación.
 * Gestiona la navegación entre las diferentes vistas usando un sidebar lateral.
 */
public class MainController {

    @FXML
    private BorderPane rootPane;

    @FXML
    private VBox sidebar;

    @FXML
    private VBox contentArea;

    @FXML
    private Button equiposNavButton;

    @FXML
    private Button mantenimientosNavButton;

    @FXML
    private Button softwareNavButton;

    @FXML
    private Button themeSwitcherButton;

    private Button currentActiveButton;
    private boolean isDarkTheme = true; // PrimerDark por defecto

    /**
     * El método initialize se ejecuta automáticamente después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        // Configurar el sidebar
        setupSidebar();
        
        // Cargar la vista por defecto (Equipos)
        navigateToEquipos();
        currentActiveButton = equiposNavButton;
        
        // Configurar el theme switcher
        updateThemeSwitcherIcon();
    }

    /**
     * Configura los estilos y comportamiento del sidebar.
     */
    private void setupSidebar() {
        // Configurar ancho fijo del sidebar
        sidebar.setPrefWidth(250);
        sidebar.setMinWidth(250);
        sidebar.setMaxWidth(250);
    }

    /**
     * Navega a la vista de Equipos.
     */
    @FXML
    public void navigateToEquipos() {
        loadView("/fxml/EquiposView.fxml");
        setActiveButton(equiposNavButton);
        System.out.println("Navegando a: Equipos");
    }

    /**
     * Navega a la vista de Mantenimientos.
     */
    @FXML
    public void navigateToMantenimientos() {
        loadView("/fxml/MantenimientosView.fxml");
        setActiveButton(mantenimientosNavButton);
        System.out.println("Navegando a: Mantenimientos");
    }

    /**
     * Navega a la vista de Software.
     */
    @FXML
    public void navigateToSoftware() {
        loadView("/fxml/SoftwareView.fxml");
        setActiveButton(softwareNavButton);
        System.out.println("Navegando a: Software");
    }

    /**
     * Carga una vista FXML en el área de contenido.
     * @param fxmlPath La ruta del archivo FXML a cargar.
     */
    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            
            // Limpiar el contenido anterior
            contentArea.getChildren().clear();
            
            // Agregar la nueva vista
            contentArea.getChildren().add(view);
            
        } catch (IOException e) {
            System.err.println("Error al cargar la vista FXML: " + fxmlPath);
            e.printStackTrace();
            
            // Mostrar un mensaje de error en lugar de la vista
            showError("No se pudo cargar la vista: " + fxmlPath);
        }
    }

    /**
     * Establece qué botón de navegación está activo.
     * @param activeButton El botón que debe marcarse como activo.
     */
    private void setActiveButton(Button activeButton) {
        // Remover la clase activa de todos los botones
        equiposNavButton.getStyleClass().removeAll("nav-button-active");
        mantenimientosNavButton.getStyleClass().removeAll("nav-button-active");
        softwareNavButton.getStyleClass().removeAll("nav-button-active");
        
        // Agregar la clase activa al botón seleccionado
        if (!activeButton.getStyleClass().contains("nav-button-active")) {
            activeButton.getStyleClass().add("nav-button-active");
        }
        
        currentActiveButton = activeButton;
        
        // Forzar actualización visual
        activeButton.pseudoClassStateChanged(
            javafx.css.PseudoClass.getPseudoClass("selected"), true
        );
    }

    /**
     * Alterna entre tema claro y oscuro.
     */
    @FXML
    public void toggleTheme() {
        try {
            if (isDarkTheme) {
                // Cambiar a tema claro
                javafx.application.Application.setUserAgentStylesheet(
                    new atlantafx.base.theme.PrimerLight().getUserAgentStylesheet()
                );
                isDarkTheme = false;
            } else {
                // Cambiar a tema oscuro
                javafx.application.Application.setUserAgentStylesheet(
                    new atlantafx.base.theme.PrimerDark().getUserAgentStylesheet()
                );
                isDarkTheme = true;
            }
            updateThemeSwitcherIcon();
            System.out.println("Tema cambiado a: " + (isDarkTheme ? "Oscuro" : "Claro"));
        } catch (Exception e) {
            System.err.println("Error al cambiar el tema: " + e.getMessage());
        }
    }

    /**
     * Actualiza el ícono del botón de cambio de tema.
     */
    private void updateThemeSwitcherIcon() {
        if (themeSwitcherButton != null) {
            themeSwitcherButton.setText(isDarkTheme ? "🔆" : "🌙");
        }
    }

    /**
     * Muestra un mensaje de error en el área de contenido.
     * @param errorMessage El mensaje de error a mostrar.
     */
    private void showError(String errorMessage) {
        try {
            javafx.scene.control.Label errorLabel = new javafx.scene.control.Label(errorMessage);
            errorLabel.setStyle("-fx-text-fill: -color-danger-emphasis; -fx-font-size: 14px; -fx-padding: 20px; -fx-alignment: center;");
            
            contentArea.getChildren().clear();
            contentArea.getChildren().add(errorLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recarga la vista actualmente seleccionada.
     */
    public void reloadCurrentView() {
        if (currentActiveButton == equiposNavButton) {
            navigateToEquipos();
        } else if (currentActiveButton == mantenimientosNavButton) {
            navigateToMantenimientos();
        } else if (currentActiveButton == softwareNavButton) {
            navigateToSoftware();
        }
    }

    /**
     * Recargar una vista específica por nombre.
     * @param viewName Nombre de la vista ("equipos", "mantenimientos", "software")
     */
    public void reloadView(String viewName) {
        switch (viewName.toLowerCase()) {
            case "equipos":
                navigateToEquipos();
                break;
            case "mantenimientos":
                navigateToMantenimientos();
                break;
            case "software":
                navigateToSoftware();
                break;
            default:
                System.err.println("Vista desconocida: " + viewName);
        }
    }

    /**
     * Obtiene el botón de navegación actualmente seleccionado.
     * @return El botón activo actual.
     */
    public Button getCurrentActiveButton() {
        return currentActiveButton;
    }

    /**
     * Obtiene el área de contenido para manipulación externa si es necesario.
     * @return El VBox del área de contenido.
     */
    public VBox getContentArea() {
        return contentArea;
    }
}