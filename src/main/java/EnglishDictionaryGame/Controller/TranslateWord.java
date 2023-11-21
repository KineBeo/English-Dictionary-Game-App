package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.PronunciationService;
import EnglishDictionaryGame.Server.TranslationService;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TranslateWord {

  @FXML private AnchorPane anchorPane;

  @FXML private ComboBox<String> sourceComboBox;

  @FXML private TextArea sourceLanguage;

  @FXML private ComboBox<String> translationComboBox;

  @FXML private TextArea translationLanguage;

  @FXML private Button sourceLanguageSpeaker;

  @FXML private Button swapLanguage;
  private final Timeline timeline = new Timeline();

  private final List<String> languages =
      Arrays.asList("English", "Vietnamese", "Spanish", "French", "Japanese");

  private final Executor executor = Executors.newSingleThreadExecutor();

  @FXML
  private void initialize() {
    sourceComboBox.getItems().addAll(languages);
    translationComboBox.getItems().addAll(languages);

    sourceComboBox.setValue("English");
    translationComboBox.setValue("Vietnamese");

    // Make the translation result uneditable.
    translationLanguage.setEditable(false);

    //    sourceLanguage
    //        .textProperty()
    //        .addListener(
    //            (observable, oldValue, newValue) -> {
    //              Task<String> translationTask = createTranslationTask(newValue);
    //              translationTask.setOnSucceeded(
    //                  event -> translationLanguage.setText(translationTask.getValue()));
    //              new Thread(translationTask).start();
    //            });

    sourceLanguage
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Reset the timeline on each key press
              timeline.stop();
              timeline.getKeyFrames().clear();

              // Add a keyframe with a 1.5 seconds delay
              timeline
                  .getKeyFrames()
                  .add(
                      new KeyFrame(
                          Duration.seconds(0.2),
                          event -> {
                            // Execute translation when the timeline completes
                            Task<String> translationTask = createTranslationTask(newValue);
                            translationTask.setOnSucceeded(
                                e -> translationLanguage.setText(translationTask.getValue()));
                            new Thread(translationTask).start();
                          }));

              // Play the timeline
              timeline.playFromStart();
            });

    swapLanguage.setOnAction(event -> swapLanguage());
    sourceLanguageSpeaker.setOnAction(
        event -> {
          if (sourceLanguage.getText().isEmpty() || sourceLanguage.getText().isBlank()) {
            sourceLanguage.setText("Please enter a word to pronounce!");
          }
          pronounceWord(sourceLanguage.getText(), sourceComboBox.getValue());
        });
  }

  private void pronounceWord(String text, String language) {
    switch (language) {
      case "English" -> language = "en";
      case "Vietnamese" -> language = "vi";
      case "French" -> language = "fr";
      case "Spanish" -> language = "es";
      case "Japanese" -> language = "ja";
    }
    String finalLanguage = language;
    executor.execute(() -> PronunciationService.pronounce(text, finalLanguage));
  }

  public void translateWord() {
    Task<String> translationTask = createTranslationTask(sourceLanguage.getText());
    translationTask.setOnSucceeded(
        event -> translationLanguage.setText(translationTask.getValue()));
    new Thread(translationTask).start();
  }

  private void swapLanguage() {
    String tempSourceLang = sourceComboBox.getValue();
    String tempTargetLang = translationComboBox.getValue();
    String tempSourceText = sourceLanguage.getText();
    String tempTargetText = translationLanguage.getText();

    sourceLanguage.setText(tempTargetText);
    translationLanguage.setText(tempSourceText);
    sourceComboBox.setValue(tempTargetLang);
    translationComboBox.setValue(tempSourceLang);
  }

  private Task<String> createTranslationTask(String sourceText) {
    return new Task<>() {
      @Override
      protected String call() {
        String sourceLang = sourceComboBox.getValue();
        String targetLang = translationComboBox.getValue();
        switch (sourceLang) {
          case "English" -> sourceLang = "en";
          case "Vietnamese" -> sourceLang = "vi";
          case "French" -> sourceLang = "fr";
          case "Spanish" -> sourceLang = "es";
          case "Japanese" -> sourceLang = "ja";
        }

        switch (targetLang) {
          case "English" -> targetLang = "en";
          case "Vietnamese" -> targetLang = "vi";
          case "French" -> targetLang = "fr";
          case "Spanish" -> targetLang = "es";
          case "Japanese" -> targetLang = "ja";
        }
        return TranslationService.translate(sourceText, sourceLang, targetLang);
      }
    };
  }
}
