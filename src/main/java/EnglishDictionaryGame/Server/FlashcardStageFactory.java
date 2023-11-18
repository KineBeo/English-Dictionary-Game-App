package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Exceptions.Utils;
import EnglishDictionaryGame.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FlashcardStageFactory {

  private static final String FLASHCARD_SCREEN_FXML_PATH = "fxml/FlashcardScreen.fxml";

  public static Stage createFlashcardStage() {
    StackPane root = createRoot();
    Scene scene = createScene(root);
    return createStage(scene);
  }

  public static Stage createEditFlashcardStage() {
    Stage stage = new Stage();
    AnchorPane root = createEditFlashcardRoot();
    Scene scene = new Scene(root);
    stage.setTitle("Edit Flashcards");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(new Main().getMainStage());
    return stage;
  }

  private static StackPane createRoot() {
    StackPane root = new StackPane(); // Initialize root in case of an exception

    // Loading the buttons in the FXML file.
    FXMLLoader loader = new FXMLLoader(Main.class.getResource(FLASHCARD_SCREEN_FXML_PATH));
    try {
      root = loader.load();
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }

    return root;
  }

  private static Scene createScene(StackPane root) {
    Scene flashcardScene = new Scene(root);
    flashcardScene.setCamera(new PerspectiveCamera());
    return flashcardScene;
  }

  private static Stage createStage(Scene scene) {
    Stage flashcardStage = new Stage();
    flashcardStage.setScene(scene);
    flashcardStage.setTitle("Flashcard");
    return flashcardStage;
  }

  private static AnchorPane createEditFlashcardRoot() {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/EditFlashcardScreen.fxml"));
      return loader.load();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private static Scene createEditFlashcardScene(AnchorPane root) {
    return new Scene(root);
  }
}
