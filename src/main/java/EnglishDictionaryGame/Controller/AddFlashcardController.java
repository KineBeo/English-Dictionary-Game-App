package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
      FXMLLoader loader =
          new FXMLLoader(Main.class.getResource("fxml/AddFlashcardScreen.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      this.stage = new Stage();
      this.stage.setTitle("Edit Flashcards");
      this.stage.setScene(scene);
      this.stage.setResizable(false);
      this.stage.initModality(Modality.APPLICATION_MODAL);
      this.stage.initOwner(new Main().getMainStage());
      this.stage.showAndWait();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
