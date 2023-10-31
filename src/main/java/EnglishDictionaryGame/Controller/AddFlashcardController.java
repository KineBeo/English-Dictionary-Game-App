package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddFlashcardController {
  FlashcardDatabase flashcardDatabase;
  Stage stage;

  public AddFlashcardController() {

  }

  public AddFlashcardController(FlashcardDatabase flashcardDatabase) {
    this.flashcardDatabase = flashcardDatabase;
  }

  public void addFlashcard(String frontText, String backText) {
    Flashcard newFlashcard = new Flashcard(frontText, backText);
    this.flashcardDatabase.addFlashcard(newFlashcard);
  }

  public void createWindow() {
    try {
      Parent root = createRoot();
      Scene scene = createScene(root);
      this.stage = createStage(scene);
      this.stage.showAndWait();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private AnchorPane createRoot() {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/AddFlashcardScreen.fxml"));
      return (AnchorPane) loader.load();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private Scene createScene(Parent root) {
    Scene scene = new Scene(root);
    return scene;
  }

  private Stage createStage(Scene scene) {
    Stage stage = new Stage();
    stage.setTitle("Edit Flashcards");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(new Main().getMainStage());
    return stage;
  }

}
