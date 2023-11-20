package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDataManager;
import EnglishDictionaryGame.Server.FlashcardStageFactory;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    setFlashcardNumberInputFieldBehavior(root);
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
    changeOperatingFlashcard(FlashcardDataManager.getSize() - 1);
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
          exitEditFlashcards();
        });
  }

  private void exitEditFlashcards() {
    // Check if there are any changes on the current flashcard that have not been saved.
    String frontTextData = operatingFlashcard.getFrontText();
    String backTextData = operatingFlashcard.getBackText();
    String wordTargetEditorContent = getWordTargetEditorContent();
    String wordDefinitionEditorContent = getWordDefinitionEditorContent();

    boolean frontTextChanged = !frontTextData.equals(wordTargetEditorContent);
    boolean backTextChanged = !backTextData.equals(wordDefinitionEditorContent);

    boolean flashcardChanged = frontTextChanged || backTextChanged;
    boolean flashcardSaved = FlashcardDataManager.isSaved(operatingFlashcard);

    if (flashcardChanged && flashcardSaved) {
      FlashcardDataManager.temporarySave(FlashcardDataManager.getIndexOf(operatingFlashcard),
          wordTargetEditorContent, wordDefinitionEditorContent);
      // Update "Unsaved" status for the current flashcard.
      updateFlashcardCounter();

      System.out.println(FlashcardDataManager.isSaved(operatingFlashcard)); // test.
    }

    // Launch confirmation alert if there are unsaved flashcards.
    if (!FlashcardDataManager.isAllSaved()) {
      Alert alert = createExitAlert();

      alert.showAndWait();

      ButtonType confirmationChoice = alert.getResult();
      ButtonType confirmSaveAndExit = alert.getButtonTypes().get(0);
      ButtonType confirmExitWithoutSaving = alert.getButtonTypes().get(1);

      if (confirmationChoice == confirmSaveAndExit) {
        FlashcardDataManager.saveAll();
      } else if (confirmationChoice == confirmExitWithoutSaving) {
        System.out.println("Exit without saving");
      }
    }

    // Update the database and file.
    FlashcardDataManager.updateDatabase();
    FlashcardDataManager.updateFile();
    stage.close();
  }

  private Alert createExitAlert() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    ArrayList<Integer> unsavedFlashcardsNumber = FlashcardDataManager.getUnsavedFlashcardsNumber();
    alert.setTitle("Exit Alert");
    String bracketlessNumbers = unsavedFlashcardsNumber.toString()
        .substring(1, unsavedFlashcardsNumber.toString().length() - 1);
    alert.setHeaderText("Do you want to save changes?\n" +
        "Unsaved flashcards' Numbers:\n" + bracketlessNumbers);

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
      // Change to the next OR previous flashcard.
      int currentIndex = FlashcardDataManager.getIndexOf(operatingFlashcard);
      int newIndex = currentIndex + direction;
      navigateToFlashcard(root, newIndex);
    });
  }

  private void navigateToFlashcard(AnchorPane root, int index) {
    int currentIndex = FlashcardDataManager.getIndexOf(operatingFlashcard);
    if (index == currentIndex) {
      return;
    }

    // Get the current flashcard's contents.
    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");
    String currentFlashcardFrontText = wordTargetEditor.getText();
    String currentFlashcardBackText = wordDefinitionEditor.getText();

    // Temporary save the current flashcard's contents.
    boolean frontTextChanged = !currentFlashcardFrontText
        .equals(operatingFlashcard.getFrontText());
    boolean backTextChanged = !currentFlashcardBackText
        .equals(operatingFlashcard.getBackText());
    boolean flashcardChanged = frontTextChanged || backTextChanged;
    if (flashcardChanged) {
      FlashcardDataManager.temporarySave(currentIndex, currentFlashcardFrontText,
          currentFlashcardBackText);
    }

    if (index > FlashcardDataManager.getSize() - 1) {
      index = 0;
    } else if (index < 0) {
      index = FlashcardDataManager.getSize() - 1;
    }
    changeOperatingFlashcard(index);
  }

  /**
   * Changes the operating flashcard to the flashcard at the given index. Updates the flashcard
   * counter.
   */
  private void changeOperatingFlashcard(int index) {
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

  private void setFlashcardNumberInputFieldBehavior(AnchorPane root) {
    TextField flashcardNumberInputField = (TextField) root.lookup("#flashcardNumberInputField");

    setDefocusBehavior(root, flashcardNumberInputField);
    setClearTextFieldOnClickBehavior(flashcardNumberInputField);
    setEnterKeyPressedBehavior(root, flashcardNumberInputField);
  }

  private void setDefocusBehavior(AnchorPane root, TextField flashcardNumberInputField) {
    root.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
      boolean isNumberInputFieldFocused = flashcardNumberInputField.isFocused();
      boolean isClickOutsideInputField = !flashcardNumberInputField.getBoundsInParent()
          .contains(event.getSceneX(), event.getSceneY());
      if (isClickOutsideInputField && isNumberInputFieldFocused) {
        defocusFlashcardNumberInputField(root);
      }
    });
  }

  private void setClearTextFieldOnClickBehavior(TextField flashcardNumberInputField) {
    flashcardNumberInputField.setOnMouseClicked(event -> {
      flashcardNumberInputField.clear();
    });
  }

  private void setEnterKeyPressedBehavior(AnchorPane root, TextField flashcardNumberInputField) {
    flashcardNumberInputField.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == javafx.scene.input.KeyCode.ENTER) {
        handleEnterKeyPressed(root, flashcardNumberInputField);
      }
    });
  }

  private void defocusFlashcardNumberInputField(AnchorPane root) {
    updateFlashcardCounter();
    root.requestFocus();
  }

  private void handleEnterKeyPressed(AnchorPane root, TextField flashcardNumberInputField) {
    if (flashcardNumberInputField.getText().isEmpty()) {
      defocusFlashcardNumberInputField(root);
    } else {
      int newFlashcardNumber = Integer.parseInt(flashcardNumberInputField.getText());
      handleValidFlashcardNumber(root, flashcardNumberInputField, newFlashcardNumber);
    }
  }

  private void handleValidFlashcardNumber(AnchorPane root, TextField flashcardNumberInputField,
      int newFlashcardNumber) {
    if (newFlashcardNumber >= 1 && newFlashcardNumber <= FlashcardDataManager.getSize()) {
      int newFlashcardIndex = newFlashcardNumber - 1;
      navigateToFlashcard(root, newFlashcardIndex);
    } else {
      updateFlashcardCounter();
    }

    defocusFlashcardNumberInputField(root);
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
    FlashcardDataManager.saveAll();
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
    boolean flashcardSaved = FlashcardDataManager.isSaved(operatingFlashcard);
    String flashcardCounterText = "/ " + FlashcardDataManager.getSize();
    if (!flashcardSaved) {
      flashcardCounterText += " (Unsaved)";
    }

    TextField flashcardNumberInputField = (TextField) stage.getScene().getRoot()
        .lookup("#flashcardNumberInputField");
    flashcardNumberInputField.setText(String.valueOf(currentFlashcardCount));
    flashcardCounter.setText(flashcardCounterText);
  }

  private String getWordTargetEditorContent() {
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    TextField wordTargetEditor = (TextField) root.lookup("#wordTargetEditor");
    String wordTargetEditorContent = wordTargetEditor.getText();
    return wordTargetEditorContent;
  }

  private String getWordDefinitionEditorContent() {
    AnchorPane root = (AnchorPane) stage.getScene().getRoot();
    TextField wordDefinitionEditor = (TextField) root.lookup("#wordDefinitionEditor");
    String wordDefinitionEditorContent = wordDefinitionEditor.getText();
    return wordDefinitionEditorContent;
  }
}
