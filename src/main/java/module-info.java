module starter {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;
    
    // Ikonli requirements for icons
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;

    opens starter to javafx.fxml;
    opens starter.ui to javafx.fxml;
    opens starter.model to javafx.base;
    
    exports starter;
}