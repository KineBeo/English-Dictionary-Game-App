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
    setAddEditFlashcardButtonBehavior(root);
    setSaveEditFlashcardButtonBehavior(root);
    setExitEditFlashcardButtonBehavior(root);
    setNextEditFlashcardButtonBehavior(root);
    setBackEditFlashcardButtonBehavior(root);
    setDeleteEditFlashcardButtonBehavior(root);
  }

  private void setAddEditFlashcardButtonBehavior(AnchorPane root) {
    Button addEditFlashcardButton = (Button) root.lookup("#addEditFlashcardButton");
    addEditFlashcardButton.setOnAction(
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

  private void setSaveEditFlashcardButtonBehavior(AnchorPane root) {
    Button saveEditFlashcardButton = (Button) root.lookup("#saveEditFlashcardButton");
    saveEditFlashcardButton.setOnAction(
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
    Alert alert = createExitAlert();

    alert.showAndWait();
    if (alert.getResult() == alert.getButtonTypes().get(0)) {
      databaseChanged = true;
      stage.close();
    } else {
      stage.close();
    }

  }

  private Alert createExitAlert() {
    Alert alert = new Alert(AlertType.CONFIRMATION);

    alert.setTitle("Exit Alert");
    alert.setHeaderText("Do you want to save changes?");
    alert.setContentText("Choose your option.");

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

  /**
   * Changes the operating flashcard to the flashcard at the given index.
   * Updates the flashcard counter.
   */
  private void changeOperatingFlashcard(int index) {
    saveCurrentFlashcard();
    loadFlashcard(index);
    updateFlashcardCounter();
  }

  private void setDeleteEditFlashcardButtonBehavior(AnchorPane root) {
    Button deleteEditFlashcardButton = (Button) root.lookup("#deleteEditFlashcardButton");
    deleteEditFlashcardButton.setOnAction(
        event -> {
          handleDeleteConfirmation();
        });
  }

  private void handleDeleteConfirmation() {
    Alert deleteConfirmationAlert = createDeleteAlert();
    deleteConfirmationAlert.showAndWait();
    ButtonType confirmationResult = deleteConfirmationAlert.getResult();
    ButtonType confirmDeleteButtonType = deleteConfirmationAlert.getButtonTypes().get(0);
    ButtonType cancelDeleteButtonType = deleteConfirmationAlert.getButtonTypes().get(1);

    if (confirmationResult == cancelDeleteButtonType) {
      return;
    } else if (confirmationResult == confirmDeleteButtonType) {
      int previousFlashcardIndex = unsavedFlashcardDatabase.getIndexOf(operatingFlashcard) - 1;
      deleteCurrentFlashcard();
      // Load the previous flashcard.
      if (unsavedFlashcardDatabase.size() == 0) {
        createAddFlashcardPage();
      } else if (previousFlashcardIndex < 0) {
        previousFlashcardIndex = unsavedFlashcardDatabase.size() - 1;
      }

      changeOperatingFlashcard(previousFlashcardIndex);
    }
  }


  private Alert createDeleteAlert() {
    Alert alert = new Alert(AlertType.CONFIRMATION);

    alert.setTitle("Delete Confirmation");
    alert.setHeaderText("Are you sure you want to delete this flashcard?");

    ButtonType okButtonType = alert.getButtonTypes().get(0);
    Button saveButton = (Button) alert.getDialogPane().lookupButton(okButtonType);
    saveButton.setText("Confirm");

    ButtonType cancelButtonType = alert.getButtonTypes().get(1);
    Button cancelButton = (Button) alert.getDialogPane().lookupButton(cancelButtonType);
    cancelButton.setText("Delete");

    return alert;
  }

  private void deleteCurrentFlashcard() {
    databaseChanged = true;
    unsavedFlashcardDatabase.remove(operatingFlashcard);
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
