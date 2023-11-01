package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditFlashcardController {
  FlashcardDatabase flashcardDatabase;
  Stage stage;
  Flashcard operatingFlashcard;

  public EditFlashcardController() {
    this.stage = createAddFlashcardStage();
    this.flashcardDatabase = new FlashcardDatabase();
  }

  public EditFlashcardController(FlashcardDatabase flashcardDatabase) {
    this.stage = createAddFlashcardStage();
    this.flashcardDatabase = flashcardDatabase;
  }

  public void addFlashcard(String frontText, String backText) {
    Flashcard newFlashcard = new Flashcard(frontText, backText);
    this.flashcardDatabase.addFlashcard(newFlashcard);
  }

  public void createWindow() {
    this.stage.show();
    loadFlashcard(0);
  }

  private Stage createAddFlashcardStage() {
    AnchorPane root = createRoot();
    Scene scene = createScene(root);
    setAllElementsBehaviors(root);
    return createStage(scene);
  }

  private void loadFlashcard(Flashcard flashcard) {
    operatingFlashcard = flashcard;
    AnchorPane root = (AnchorPane) this.stage.getScene().getRoot();

    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");

    wordTargetEditor.setText(operatingFlashcard.getFrontText());
    wordDefinitionEditor.setText(operatingFlashcard.getBackText());

    updateFlashcardCounter();
  }

  private void loadFlashcard(int flashcardIndex) {
    Flashcard flashcard = flashcardDatabase.getFlashcard(flashcardIndex);
    if (flashcard == null) {
      return;
    }

    loadFlashcard(flashcard);
  }

  private void setAllElementsBehaviors(AnchorPane root) {
    setAddFlashcardButtonBehavior(root);
    setSaveFlashcardButtonBehavior(root);
  }

  private void setAddFlashcardButtonBehavior(AnchorPane root) {
    Button addFlashcardButton = (Button) root.lookup("#addFlashcardButton");
    addFlashcardButton.setOnAction(
        event -> {
          createAddFlashcardPage();
        });
  }

  private void setSaveFlashcardButtonBehavior(AnchorPane root) {
    Button saveFlashcardButton = (Button) root.lookup("#saveFlashcardButton");
    saveFlashcardButton.setOnAction(
        event -> {
          saveFlashcard();
        });
  }

  private void createAddFlashcardPage() {
    // Clears the word target and word definition fields.
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");
    wordTargetEditor.setText("");
    wordDefinitionEditor.setText("");

    Flashcard newFlashcard = new Flashcard("", "");
    loadFlashcard(newFlashcard);
    flashcardDatabase.addFlashcard(newFlashcard);

    updateFlashcardCounter();
  }

  private void saveFlashcard() {
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");

    String frontText = wordTargetEditor.getText();
    String backText = wordDefinitionEditor.getText();

    operatingFlashcard.setFrontText(frontText);
    operatingFlashcard.setBackText(backText);
  }
  private void updateFlashcardCounter() {
    Label flashcardCounter = (Label) stage.getScene().getRoot().lookup("#flashcardCounter");
    int currentFlashcardCount = flashcardDatabase.getIndexOf(operatingFlashcard) + 1;
    flashcardCounter.setText(currentFlashcardCount + " / " + flashcardDatabase.size());
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

  private Scene createScene(AnchorPane root) {
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
