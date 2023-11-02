package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditFlashcardController {

  /**
   * All of this controller's operations are done on this database. The main database will be setted
   * equal to this database if the user chooses to save changes, therefore saving data.
   */
  private FlashcardDatabase unsavedFlashcardDatabase;
  private Stage stage;
  private Flashcard operatingFlashcard;
  private boolean databaseChanged;

  public EditFlashcardController() {
    this.stage = createAddFlashcardStage();
    this.unsavedFlashcardDatabase = new FlashcardDatabase();
    databaseChanged = false;
  }

  public EditFlashcardController(FlashcardDatabase savedFlashcardDatabase) {
    this.stage = createAddFlashcardStage();
    this.unsavedFlashcardDatabase = new FlashcardDatabase(savedFlashcardDatabase);
    databaseChanged = false;
  }

  public void addFlashcard(String frontText, String backText) {
    Flashcard newFlashcard = new Flashcard(frontText, backText);
    this.unsavedFlashcardDatabase.addFlashcard(newFlashcard);
  }

  public void createWindow() {
    loadFlashcard(0);
    this.stage.show();
  }

  public boolean changedFlashcardDatabase() {
    return databaseChanged;
  }

  /**
   * Updates the saved flashcard database with the unsaved flashcard database.
   */
  public FlashcardDatabase getUnsavedFlashcardDatabase() {
    return this.unsavedFlashcardDatabase;
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
    Flashcard flashcard = unsavedFlashcardDatabase.getFlashcard(flashcardIndex);
    if (flashcard == null) {
      return;
    }

    loadFlashcard(flashcard);
  }

  private void setAllElementsBehaviors(AnchorPane root) {
    setAddFlashcardButtonBehavior(root);
    setSaveFlashcardButtonBehavior(root);
    setExitEditFlashcardButtonBehavior(root);
    setNextEditFlashcardButtonBehavior(root);
    setBackEditFlashcardButtonBehavior(root);
  }

  private void setAddFlashcardButtonBehavior(AnchorPane root) {
    Button addFlashcardButton = (Button) root.lookup("#addFlashcardButton");
    addFlashcardButton.setOnAction(
        event -> {
          createAddFlashcardPage();
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
    unsavedFlashcardDatabase.addFlashcard(newFlashcard);

    updateFlashcardCounter();
  }

  private void setSaveFlashcardButtonBehavior(AnchorPane root) {
    Button saveFlashcardButton = (Button) root.lookup("#saveFlashcardButton");
    saveFlashcardButton.setOnAction(
        event -> {
          saveCurrentFlashcard();
        });
  }

  private void saveCurrentFlashcard() {
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");

    String frontText = wordTargetEditor.getText();
    String backText = wordDefinitionEditor.getText();

    operatingFlashcard.setFrontText(frontText);
    operatingFlashcard.setBackText(backText);
  }

  private void setExitEditFlashcardButtonBehavior(AnchorPane root) {
    Button exitEditFlashcardButton = (Button) root.lookup("#exitEditFlashcardsButton");
    exitEditFlashcardButton.setOnAction(
        event -> {
          System.out.println("Exit edit flashcards button clicked");
          exitEditFlashcards();
        });
  }

  private void exitEditFlashcards() {
    Alert alert = createSaveAlert();
    alert.setTitle("Save Changes");
    alert.setHeaderText("Do you want to save changes?");
    alert.setContentText("Choose your option.");

    alert.showAndWait();
    if (alert.getResult() == alert.getButtonTypes().get(0)) {
      databaseChanged = true;
      stage.close();
    } else {
      stage.close();
    }
  }

  private Alert createSaveAlert() {
    Alert alert = new Alert(AlertType.CONFIRMATION);

    ButtonType okButtonType = alert.getButtonTypes().get(0);
    Button saveButton = (Button) alert.getDialogPane().lookupButton(okButtonType);
    saveButton.setText("Save and Continue");

    ButtonType cancelButtonType = alert.getButtonTypes().get(1);
    Button cancelButton = (Button) alert.getDialogPane().lookupButton(cancelButtonType);
    cancelButton.setText("Continue without saving");

    return alert;
  }

  private void setNextEditFlashcardButtonBehavior(AnchorPane root) {
    Button nextEditFlashcardButton = (Button) root.lookup("#nextEditFlashcardButton");
    nextEditFlashcardButton.setOnAction(
        event -> {
          int nextFlashcardIndex = unsavedFlashcardDatabase.getIndexOf(operatingFlashcard) + 1;
          if (nextFlashcardIndex >= unsavedFlashcardDatabase.size()) {
            nextFlashcardIndex = 0;
          }
          changeOperatingFlashcard(nextFlashcardIndex);
        });
  }

  private void setBackEditFlashcardButtonBehavior(AnchorPane root) {
    Button backEditFlashcardsButton = (Button) root.lookup("#backEditFlashcardButton");
    backEditFlashcardsButton.setOnAction(
        event -> {
          int previousFlashcardIndex = unsavedFlashcardDatabase.getIndexOf(operatingFlashcard) - 1;
          if (previousFlashcardIndex < 0) {
            previousFlashcardIndex = unsavedFlashcardDatabase.size() - 1;
          }
          changeOperatingFlashcard(previousFlashcardIndex);
        });
  }
  private void changeOperatingFlashcard(int index) {
    saveCurrentFlashcard();
    loadFlashcard(index);
    updateFlashcardCounter();
  }

  private void updateFlashcardCounter() {
    Label flashcardCounter = (Label) stage.getScene().getRoot().lookup("#flashcardCounter");
    int currentFlashcardCount = unsavedFlashcardDatabase.getIndexOf(operatingFlashcard) + 1;
    flashcardCounter.setText(currentFlashcardCount + " / " + unsavedFlashcardDatabase.size());
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
