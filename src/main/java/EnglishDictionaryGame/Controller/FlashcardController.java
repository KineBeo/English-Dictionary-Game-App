package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Exceptions.Utils;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardFileManager;
import EnglishDictionaryGame.Server.FlashcardDataManager;
import EnglishDictionaryGame.Server.FlashcardDatabase;
import EnglishDictionaryGame.Server.FlashcardStageFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FlashcardController {

  private FlashcardDatabase flashcardDatabase;
  private FlashcardViewController flashcardViewController;
  private Stage stage = null;
  private Flashcard currentFlashcard = null;
  private int currentFlashcardCount = -1;

  public FlashcardController() {
    FlashcardDataManager.initialize();
    flashcardDatabase = FlashcardDataManager.getFlashcardDatabase();
    this.currentFlashcard = flashcardDatabase.getFlashcard(0);
    flashcardViewController = new FlashcardViewController(currentFlashcard);
    currentFlashcardCount = 1;

    this.stage = FlashcardStageFactory.createFlashcardStage();
  }

  public void createFlashcardWindow() {
    StackPane root = (StackPane) stage.getScene().getRoot();
    setAllElementsBehaviors(root);
    updateFlashcardCounter((Label) root.lookup("#flashcardCounter"));
    try {
      root.getChildren().addAll(currentFlashcard.getCardView());
    } catch (Exception e) {
      System.out.println("Flashcard controller's currentFlashcard is null");
      Utils.printRelevantStackTrace(e);
    }

    this.stage.show();
  }

  private void setAllElementsBehaviors(StackPane root) {
    setFlipFlashcardButtonBehavior(root);
    setNextFlashcardButtonBehavior(root);
    setPreviousFlashcardButtonBehavior(root);
    setExitFlashcardsButtonBehavior(root);
    setEditFlashcardsButtonBehavior(root);
  }

  private void setFlipFlashcardButtonBehavior(StackPane root) {
    Button flipFlashcardButton = (Button) root.lookup("#flipFlashcardButton");
    flipFlashcardButton.setOnMouseClicked(e -> {
      flashcardViewController.flipFlashcard();
    });
  }

  private void setNextFlashcardButtonBehavior(StackPane root) {
    Button nextFlashcardButton = (Button) root.lookup("#nextFlashcardButton");
    nextFlashcardButton.setOnMouseClicked(e -> {
      int currentFlashcardIndex = flashcardDatabase.getIndexOf(currentFlashcard);
      int nextFlashcardIndex = currentFlashcardIndex + 1;
      if (nextFlashcardIndex >= flashcardDatabase.size()) {
        nextFlashcardIndex = 0;
      }

      changeFlashcard(nextFlashcardIndex);
      currentFlashcardCount = nextFlashcardIndex + 1;
      updateFlashcardCounter((Label) root.lookup("#flashcardCounter"));
    });
  }

  private void setPreviousFlashcardButtonBehavior(StackPane root) {
    Button previousFlashcardButton = (Button) root.lookup("#previousFlashcardButton");
    previousFlashcardButton.setOnMouseClicked(e -> {
      int currentFlashcardIndex = flashcardDatabase.getIndexOf(currentFlashcard);
      int previousFlashcardIndex = currentFlashcardIndex - 1;
      if (previousFlashcardIndex < 0) {
        previousFlashcardIndex = flashcardDatabase.size() - 1;
      }

      changeFlashcard(previousFlashcardIndex);
      currentFlashcardCount = previousFlashcardIndex + 1;
      updateFlashcardCounter((Label) root.lookup("#flashcardCounter"));
    });
  }

  private void setExitFlashcardsButtonBehavior(StackPane root) {
    Button exitFlashcardsButton = (Button) root.lookup("#exitFlashcardsButton");
    exitFlashcardsButton.setOnMouseClicked(e -> {
      closeFlashcards();
    });
  }

  private void setEditFlashcardsButtonBehavior(StackPane root) {
    Button editFlashcardsButton = (Button) root.lookup("#editFlashcardsButton");
    editFlashcardsButton.setOnMouseClicked(e -> {
      EditFlashcardController editFlashcardController = new EditFlashcardController();
      editFlashcardController.createWindow();
      System.out.println("Edit flashcard controller exited.");

      // Update the flashcard database.
      this.flashcardDatabase = FlashcardDataManager.getFlashcardDatabase();

      // Reload the current flashcard.
      flashcardViewController.reloadFlashcardData();

      // Reload the flashcard counter.
      updateFlashcardCounter((Label) root.lookup("#flashcardCounter"));
    });
  }


  private void changeFlashcard(Flashcard newFlashcard) {
    StackPane root = (StackPane) stage.getScene().getRoot();

    try {
      if (root.getChildren().contains(currentFlashcard.getCardView())) {
        root.getChildren().remove(currentFlashcard.getCardView());
      }

      root.getChildren().add(newFlashcard.getCardView());
      currentFlashcard = newFlashcard;
      flashcardViewController.setFlashcard(newFlashcard);
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }
  }

  private void changeFlashcard(int flashcardIndex) {
    Flashcard newFlashcard = flashcardDatabase.getFlashcard(flashcardIndex);
    if (newFlashcard == null) {
      Flashcard emptyFlashcard = new Flashcard("Empty", "Empty");
      newFlashcard = emptyFlashcard;
    }

    changeFlashcard(newFlashcard);
  }

  private void updateFlashcardCounter(Label flashcardCounter) {
    flashcardCounter.setText(currentFlashcardCount + " / " + flashcardDatabase.size());
  }

  private void closeFlashcards() {
    stage.close();
  }
}