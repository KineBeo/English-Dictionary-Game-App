package EnglishDictionaryGame;
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
      mainStage.setTitle("Dictionary!");
      mainStage.setResizable(false);
      mainStage.setScene(scene);
      mainStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Stage getMainStage() {
    return this.mainStage;
  }

  public static void main(String[] args) {
//    PronunciationService.pronounce("hello is it me you're looking for", PronunciationService.LANGUAGE_ENGLISH);
//    PronunciationService.pronounce("Đã fix được bug dcu", PronunciationService.LANGUAGE_VIETNAMESE);
    String testText = "Hello darkness my old friend";
    PronunciationService.pronounce(testText, PronunciationService.LANGUAGE_ENGLISH);
    String translation = TranslationService.translate(testText, "en", "vi");
    System.out.println(translation);
    launch();
  }
}
