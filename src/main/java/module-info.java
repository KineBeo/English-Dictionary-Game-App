module dictionaryapplication.englishdictionarygameapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
  requires org.apache.commons.lang3;
    requires java.sql;
    requires javafx.web;
  requires jlayer;
    requires java.desktop;

    opens EnglishDictionaryGame to javafx.fxml;
    exports EnglishDictionaryGame;

    opens EnglishDictionaryGame.Controller to javafx.fxml; // Export package to JavaFX
    exports EnglishDictionaryGame.Controller; // Export main module package
}