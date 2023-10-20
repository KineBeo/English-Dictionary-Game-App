package EnglishDictionaryGame;

import java.util.Objects;

import EnglishDictionaryGame.Server.PronunciationService;
import EnglishDictionaryGame.Server.TranslationService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  public Stage mainStage;

  @Override
  public void start(Stage stage) {
    try {
      mainStage = stage;
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/Application.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      scene
          .getStylesheets()
          .add(
              Objects.requireNonNull(Main.class.getResource("css/stylesheet.css"))
                  .toExternalForm());
      scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
      mainStage.setTitle("Dictionary!");
      mainStage.setResizable(false);
      mainStage.setScene(scene);
      mainStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
      mainStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Stage getMainStage() {
    return this.mainStage;
  }

  public static void main(String[] args) {
    new Thread(() -> {
      String testText = "Hello darkness my old friend";
      PronunciationService.pronounce(testText, PronunciationService.LANGUAGE_ENGLISH);
      String translation = TranslationService.translate(testText, "en", "vi");
      System.out.println(translation);
    }).start();
    launch();
  }
}
