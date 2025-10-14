package starter;

import java.io.IOException;
import java.util.Objects;
import starter.database.DatabaseManager;
import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application {

    static final String ASSETS_DIR = "/assets/";
    static final String APP_ICON_PATH = Objects.requireNonNull(
            Launcher.class.getResource(ASSETS_DIR + "icons/app-icon.png")
    ).toExternalForm();

    @Override
    public void start(Stage stage) {
        // --- INICIALIZACIÓN DE LA BASE DE DATOS ---
        // Llamamos al método de inicialización aquí para asegurar que las tablas
        // existan antes de que se cargue la UI y la lógica de la app.
        DatabaseManager.initializeDatabase();
        
        // --- CARGA DE LA INTERFAZ DE USUARIO (UI) ---
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            var scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(
                    Objects.requireNonNull(
                            getClass().getResource(ASSETS_DIR + "index.css")
                    ).toExternalForm()
            );

            stage.setScene(scene);
            stage.setTitle("Fichas T\u00E9cnicas");
            stage.getIcons().add(new Image(APP_ICON_PATH));
            stage.setOnCloseRequest(t -> Platform.exit());
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setMaxWidth(1366);
            stage.setMaxHeight(900);

            Platform.runLater(() -> {
                stage.show();
                stage.requestFocus();
            });
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar el archivo FXML", e);
        }
    }
}

