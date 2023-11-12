package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDataManager;
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

  private Stage stage;
  private Flashcard operatingFlashcard;

  public EditFlashcardController() {
    this.stage = FlashcardStageFactory.createEditFlashcardStage();
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    setAllElementsBehaviors(root);
  }

  public void createWindow() {
    loadFlashcard(0);
    this.stage.showAndWait();
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
    Flashcard flashcard = FlashcardDataManager.getEditingFlashcard(flashcardIndex);
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
        });
  }

  private void createAddFlashcardPage() {
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");
    wordTargetEditor.setText("");
    wordDefinitionEditor.setText("");

    FlashcardDataManager.addEmptyFlashcard();
    loadFlashcard(FlashcardDataManager.getSize() - 1);
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
    // Get the new contents of the current flashcard.
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");
    String frontText = wordTargetEditor.getText();
    String backText = wordDefinitionEditor.getText();

    // Hard save the new contents to the main database.
    FlashcardDataManager.hardSave(FlashcardDataManager.getIndexOf(operatingFlashcard), frontText,
        backText);
  }

  private void setExitEditFlashcardButtonBehavior(AnchorPane root) {
    Button exitEditFlashcardButton = (Button) root.lookup("#exitEditFlashcardsButton");
    exitEditFlashcardButton.setOnAction(
        event -> {
          System.out.println("Exit edit flashcards button clicked");
          FlashcardDataManager.temporarySave(FlashcardDataManager.getIndexOf(operatingFlashcard),
              operatingFlashcard.getFrontText(), operatingFlashcard.getBackText());
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
    } else if (confirmationChoice == confirmExitWithoutSaving) {
      FlashcardDataManager.updateDatabase();
    }

    stage.close();
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
      // Get the current flashcard's contents.
      TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
      TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");
      String currentFlashcardFrontText = wordTargetEditor.getText();
      String currentFlashcardBackText = wordDefinitionEditor.getText();

      // Temporary save the current flashcard's contents.
      int currentIndex = FlashcardDataManager.getIndexOf(operatingFlashcard);
      FlashcardDataManager.temporarySave(currentIndex, currentFlashcardFrontText,
          currentFlashcardBackText);

      // Change to the next OR previous flashcard.
      int newIndex = currentIndex + direction;
      if (newIndex > FlashcardDataManager.getSize() - 1) {
        newIndex = 0;
      } else if (newIndex < 0) {
        newIndex = FlashcardDataManager.getSize() - 1;
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
  }

  private void setSaveAllEditFlashcardButtonBehavior(AnchorPane root) {
    Button saveAllEditFlashcardsButton = (Button) root.lookup("#saveAllEditFlashcardsButton");
    saveAllEditFlashcardsButton.setOnAction(
        event -> {
          saveAllFlashcards();
        });
  }

  private void handleDeleteConfirmation() {
    Alert deleteConfirmationAlert = createDeleteAlert();
    deleteConfirmationAlert.showAndWait();
    ButtonType confirmationChoice = deleteConfirmationAlert.getResult();
    ButtonType confirmDelete = deleteConfirmationAlert.getButtonTypes().get(0);
    ButtonType cancelDelete = deleteConfirmationAlert.getButtonTypes().get(1);

    int previousFlashcardIndex = FlashcardDataManager.getIndexOf(operatingFlashcard) - 1;
    if (confirmationChoice == cancelDelete) {
      return;
    } else if (confirmationChoice == confirmDelete) {
      deleteCurrentFlashcard();
      // Load the previous flashcard.
      if (FlashcardDataManager.getSize() == 0) {
        createAddFlashcardPage();
      } else if (previousFlashcardIndex < 0) {
        previousFlashcardIndex = FlashcardDataManager.getSize() - 1;
      }

      changeOperatingFlashcard(previousFlashcardIndex);
    }
  }

  private void saveAllFlashcards() {
    String frontText = "";
    String backText = "";
    for (int i = 0; i < FlashcardDataManager.getSize(); i++) {
      frontText = FlashcardDataManager.getEditingFlashcard(i).getFrontText();
      backText = FlashcardDataManager.getEditingFlashcard(i).getBackText();
      FlashcardDataManager.hardSave(i, frontText, backText);
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
    int currentIndex = FlashcardDataManager.getIndexOf(operatingFlashcard);
    FlashcardDataManager.removeFlashcard(currentIndex);
  }

  private void updateFlashcardCounter() {
    Label flashcardCounter = (Label) stage.getScene().getRoot().lookup("#flashcardCounter");
    int currentFlashcardCount = FlashcardDataManager.getIndexOf(operatingFlashcard) + 1;
    flashcardCounter.setText(currentFlashcardCount + " / " + FlashcardDataManager.getSize());
  }
}
