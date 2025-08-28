module starter {

    // --- Módulos Requeridos ---
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base; // Este ya lo tenías, está correcto
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    // Kordamp - Estos ya los tenías, están correctos
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.materialdesign2;
    
    // --- Exportar y Abrir Paquetes ---
    // Exporta el paquete principal para que la aplicación pueda ser lanzada
    exports starter;

    // ¡ESTAS SON LAS LÍNEAS QUE FALTABAN!
    // Abren tus paquetes al módulo de FXML para que pueda acceder a tus controladores.
    opens starter to javafx.fxml;
    opens starter.ui to javafx.fxml;
}