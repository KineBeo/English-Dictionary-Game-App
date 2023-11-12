package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDatabase;
import EnglishDictionaryGame.Server.FlashcardStageFactory;
import java.util.HashMap;
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

  private FlashcardDatabase newFlashcardDatabase;

  /**
   * All of this controller's operations are done on this database.
   */
  private FlashcardDatabase editingFlashcardDatabase;
  HashMap<Flashcard, Boolean> flashcardSaveMap;

  private Stage stage;
  private Flashcard operatingFlashcard;

  public EditFlashcardController() {
    this.editingFlashcardDatabase = new FlashcardDatabase();
    this.stage = FlashcardStageFactory.createEditFlashcardStage();
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    setAllElementsBehaviors(root);
  }

  public EditFlashcardController(FlashcardDatabase savedFlashcardDatabase) {
    this.newFlashcardDatabase = new FlashcardDatabase(savedFlashcardDatabase);
    this.editingFlashcardDatabase = new FlashcardDatabase(savedFlashcardDatabase);

    // Add all flashcards to the flashcardSaveMap HashMap.
    this.flashcardSaveMap = new HashMap<>();
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      flashcardSaveMap.put(editingFlashcardDatabase.getFlashcard(i), true);
    }

    this.stage = FlashcardStageFactory.createEditFlashcardStage();
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    setAllElementsBehaviors(root);
  }

  public void addFlashcard(String frontText, String backText) {
    Flashcard newFlashcard = new Flashcard(frontText, backText);
    this.editingFlashcardDatabase.addFlashcard(newFlashcard);
    this.newFlashcardDatabase.addFlashcard(new Flashcard("Unsaved new Flashcard", "Unsaved new Flashcard"));
    flashcardSaveMap.put(newFlashcard, false);
  }

  public void createWindow() {
    loadFlashcard(0);
    this.stage.showAndWait();
  }

  /**
   * Updates the saved flashcard database with the unsaved flashcard database.
   */
  public FlashcardDatabase getNewFlashcardDatabase() {
    return this.newFlashcardDatabase;
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
    Flashcard flashcard = editingFlashcardDatabase.getFlashcard(flashcardIndex);
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
    setSaveAllEditFlashcardButtonBehavior(root);
  }

  private void setAddEditFlashcardButtonBehavior(AnchorPane root) {
    Button addEditFlashcardButton = (Button) root.lookup("#addEditFlashcardButton");
    addEditFlashcardButton.setOnAction(
        event -> {
          createAddFlashcardPage();
          // Test
          printDatabase();
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
    editingFlashcardDatabase.addFlashcard(newFlashcard);

    updateFlashcardCounter();
  }

  private void setSaveEditFlashcardButtonBehavior(AnchorPane root) {
    Button saveEditFlashcardButton = (Button) root.lookup("#saveEditFlashcardButton");
    saveEditFlashcardButton.setOnAction(
        event -> {
          saveCurrentFlashcard();
          // Test
          printDatabase();
        });
  }

  private void saveCurrentFlashcard() {
    // Get the new contents of the current flashcard.
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");

    String frontText = wordTargetEditor.getText();
    String backText = wordDefinitionEditor.getText();

    // Update the flashcard in the editing flashcard database.
    operatingFlashcard.setFrontText(frontText);
    operatingFlashcard.setBackText(backText);

    // Update the flashcard in the new flashcard database.
    int updatedFlashcardIndex = editingFlashcardDatabase.getIndexOf(operatingFlashcard);
    Flashcard updatedFlashcard = newFlashcardDatabase.getFlashcard(updatedFlashcardIndex);
    updatedFlashcard.setFrontText(frontText);
    updatedFlashcard.setBackText(backText);

    // Update flashcard save state.
    flashcardSaveMap.put(operatingFlashcard, true);
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

    ButtonType confirmationChoice = alert.getResult();
    ButtonType confirmSaveAndExit = alert.getButtonTypes().get(0);
    ButtonType confirmExitWithoutSaving = alert.getButtonTypes().get(1);
    if (confirmationChoice == confirmSaveAndExit) {
      saveAllFlashcards();
      stage.close();
    } else if (confirmationChoice == confirmExitWithoutSaving) {
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
    setFlashcardNavigationButtonBehavior(root, nextEditFlashcardButton, 1);
  }

  private void setBackEditFlashcardButtonBehavior(AnchorPane root) {
    Button backEditFlashcardsButton = (Button) root.lookup("#backEditFlashcardButton");
    setFlashcardNavigationButtonBehavior(root, backEditFlashcardsButton, -1);
  }

  private void setFlashcardNavigationButtonBehavior(AnchorPane root,
      Button flashcardNavigationButton, int direction) {
    flashcardNavigationButton.setOnAction(event -> {
      boolean currentFlashcardSaved = flashcardSaveMap.get(operatingFlashcard);
      if (!currentFlashcardSaved) {
        // Check if there are changes to the current flashcard
        TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
        TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");
        String currentFlashcardFrontText = wordTargetEditor.getText();
        String currentFlashcardBackText = wordDefinitionEditor.getText();
        String originalFlashcardFrontText = newFlashcardDatabase
            .getFlashcard(newFlashcardDatabase.getIndexOf(operatingFlashcard)).getFrontText();
        String originalFlashcardBackText = newFlashcardDatabase
            .getFlashcard(newFlashcardDatabase.getIndexOf(operatingFlashcard)).getBackText();

        if (!currentFlashcardFrontText.equals(originalFlashcardFrontText) || !currentFlashcardBackText.equals(originalFlashcardBackText)) {
          // Store the unsaved changes to the editing database.
          saveCurrentFlashcard();
        }

      }

      int currentIndex = editingFlashcardDatabase.getIndexOf(operatingFlashcard);
      int newIndex = currentIndex + direction;
      if (newIndex > editingFlashcardDatabase.size() - 1) {
        newIndex = 0;
      } else if (newIndex < 0) {
        newIndex = editingFlashcardDatabase.size() - 1;
      }

      changeOperatingFlashcard(newIndex);
    });
  }

  /**
   * Changes the operating flashcard to the flashcard at the given index. Updates the flashcard
   * counter.
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
    // Test.
    printDatabase();
  }

  private void handleDeleteConfirmation() {
    Alert deleteConfirmationAlert = createDeleteAlert();
    deleteConfirmationAlert.showAndWait();
    ButtonType confirmationChoice = deleteConfirmationAlert.getResult();
    ButtonType confirmDelete = deleteConfirmationAlert.getButtonTypes().get(0);
    ButtonType cancelDelete = deleteConfirmationAlert.getButtonTypes().get(1);

    if (confirmationChoice == cancelDelete) {
      return;
    } else if (confirmationChoice == confirmDelete) {
      int previousFlashcardIndex = editingFlashcardDatabase.getIndexOf(operatingFlashcard) - 1;
      deleteCurrentFlashcard();
      // Load the previous flashcard.
      if (editingFlashcardDatabase.size() == 0) {
        createAddFlashcardPage();
      } else if (previousFlashcardIndex < 0) {
        previousFlashcardIndex = editingFlashcardDatabase.size() - 1;
      }

      changeOperatingFlashcard(previousFlashcardIndex);
    }
  }

  private void setSaveAllEditFlashcardButtonBehavior(AnchorPane root) {
    Button saveAllEditFlashcardsButton = (Button) root.lookup("#saveAllEditFlashcardsButton");
    saveAllEditFlashcardsButton.setOnAction(
        event -> {
          saveAllFlashcards();
        });
  }

  /**
   * Sets the newFlashcardDatabase to be exactly the same as the editingFlashcardDatabase.
   */
  private void saveAllFlashcards() {
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      Flashcard editingFlashcard = editingFlashcardDatabase.getFlashcard(i);
      Flashcard newFlashcard = newFlashcardDatabase.getFlashcard(i);
      newFlashcard.setFrontText(editingFlashcard.getFrontText());
      newFlashcard.setBackText(editingFlashcard.getBackText());
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
    int currentIndex = editingFlashcardDatabase.getIndexOf(operatingFlashcard);
    newFlashcardDatabase.remove(currentIndex);
    editingFlashcardDatabase.remove(currentIndex);
  }

  private void updateFlashcardCounter() {
    Label flashcardCounter = (Label) stage.getScene().getRoot().lookup("#flashcardCounter");
    int currentFlashcardCount = editingFlashcardDatabase.getIndexOf(operatingFlashcard) + 1;
    flashcardCounter.setText(currentFlashcardCount + " / " + editingFlashcardDatabase.size());
  }

  private void printDatabase() {
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      Flashcard printFlashcard = editingFlashcardDatabase.getFlashcard(i);
      System.out.print(printFlashcard.getFrontText() + "|" + printFlashcard.getBackText());
      System.out.println();
    }
  }
}
