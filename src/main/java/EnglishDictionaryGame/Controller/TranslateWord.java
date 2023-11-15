package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.TranslationService;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TranslateWord extends WordOperation {

  @FXML private AnchorPane anchorPane;

  @FXML private ComboBox<String> sourceComboBox;

  @FXML private TextArea sourceLanguage;

  @FXML private ComboBox<String> translationComboBox;

  @FXML private TextArea translationLanguage;

  private final List<String> languages =
      Arrays.asList("English", "Vietnamese", "Spanish", "French");

  private final Executor executor = Executors.newSingleThreadExecutor();

  @FXML
  private void initialize() {
    sourceComboBox.getItems().addAll(languages);
    translationComboBox.getItems().addAll(languages);

    sourceComboBox.setValue("English");
    translationComboBox.setValue("Vietnamese");

    sourceLanguage
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Task<String> translationTask = createTranslationTask(newValue);
              translationTask.setOnSucceeded(
                  event -> translationLanguage.setText(translationTask.getValue()));
              new Thread(translationTask).start();
            });
  }

  public void saveWord() {}

  public void translateWord() {
    Task<String> translationTask = createTranslationTask(sourceLanguage.getText());
    translationTask.setOnSucceeded(
        event -> translationLanguage.setText(translationTask.getValue()));
    new Thread(translationTask).start();
  }

  private Task<String> createTranslationTask(String sourceText) {
    return new Task<>() {
      @Override
      protected String call() throws Exception {
        String sourceLang = sourceComboBox.getValue();
        String targetLang = translationComboBox.getValue();
        switch (sourceLang) {
          case "English" -> sourceLang = "en";
          case "Vietnamese" -> sourceLang = "vi";
          case "French" -> sourceLang = "fr";
          case "Spanish" -> sourceLang = "es";
        }

        switch (targetLang) {
          case "English" -> targetLang = "en";
          case "Vietnamese" -> targetLang = "vi";
          case "French" -> targetLang = "fr";
          case "Spanish" -> targetLang = "es";
        }
        return TranslationService.translate(sourceText, sourceLang, targetLang);
      }
    };
  }

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
