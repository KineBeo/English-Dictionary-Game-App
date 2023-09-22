module dictionaryapplication.englishdictionarygameapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens EnglishDictionaryGame to javafx.fxml;
    exports EnglishDictionaryGame;
    exports EnglishDictionaryGame.Controller;
    opens EnglishDictionaryGame.Controller to javafx.fxml;
}