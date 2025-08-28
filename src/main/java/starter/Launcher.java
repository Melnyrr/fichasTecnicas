package starter;

import java.io.IOException;
import java.util.Objects;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import starter.database.DatabaseManager;

public class Launcher extends Application {

    static final String ASSETS_DIR = "/assets/";
    static final String APP_ICON_PATH = Objects.requireNonNull(
            Launcher.class.getResource(ASSETS_DIR + "icons/app-icon.png")
    ).toExternalForm();

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            var scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(
                    Objects.requireNonNull(
                            getClass().getResource(ASSETS_DIR + "index.css")
                    ).toExternalForm()
            );

            stage.setScene(scene);
            stage.setTitle("Fichas T\u00E9cnicas");
            stage.getIcons().add(new Image(APP_ICON_PATH));
            stage.setOnCloseRequest(t -> Platform.exit());
            stage.setMaxWidth(1280);
            stage.setMaxHeight(900);

            Platform.runLater(() -> {
                stage.show();
                stage.requestFocus();
            });
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar el archivo FXML", e);
        }
    }

    public static void main(String[] args) {
        // Llama al m\u00E9todo de inicializaci\u00F3n de la base de datos
        DatabaseManager.initializeDatabase();
        launch(args);
    }
}
