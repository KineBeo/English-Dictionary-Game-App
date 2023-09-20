module dictionaryapplication.englishdictionarygameapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens dictionaryapplication.englishdictionarygameapp to javafx.fxml;
    exports dictionaryapplication.englishdictionarygameapp;
}