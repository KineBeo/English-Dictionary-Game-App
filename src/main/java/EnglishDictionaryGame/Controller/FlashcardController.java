package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Utils;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDataManager;
import EnglishDictionaryGame.Server.FlashcardDatabase;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class FlashcardController {

  private FlashcardDatabase flashcardDatabase;
  private FlashcardViewController flashcardViewController;
  private StackPane root;
  private Flashcard currentFlashcard = null;
  private int currentFlashcardCount = -1;

  public FlashcardController() {
    FlashcardDataManager.initialize();
    flashcardDatabase = FlashcardDataManager.getFlashcardDatabase();
    this.currentFlashcard = flashcardDatabase.getFlashcard(0);
    flashcardViewController = new FlashcardViewController(currentFlashcard);
    currentFlashcardCount = 1;
  }

  public void createFlashcardWindow(StackPane root) {
    this.root = root;
    setAllElementsBehaviors(root);
    updateFlashcardCounter((Label) root.lookup("#flashcardCounter"));
    try {
      root.getChildren().addAll(currentFlashcard.getCardView());
    } catch (Exception e) {
      System.out.println("Flashcard controller's currentFlashcard is null");
      Utils.printRelevantStackTrace(e);
    }
  }

  private void setAllElementsBehaviors(StackPane root) {
    setFlipFlashcardButtonBehavior(root);
    setNextFlashcardButtonBehavior(root);
    setPreviousFlashcardButtonBehavior(root);
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

  private void setEditFlashcardsButtonBehavior(StackPane root) {
    Button editFlashcardsButton = (Button) root.lookup("#editFlashcardsButton");
    editFlashcardsButton.setOnMouseClicked(e -> {
      EditFlashcardController editFlashcardController = new EditFlashcardController();
      editFlashcardController.createWindow();
      System.out.println("Edit flashcard controller exited.");

      // Update the flashcard database.
      this.flashcardDatabase = FlashcardDataManager.getFlashcardDatabase();

      // Reload every flashcard in the database.
      for (int i = 0; i < flashcardDatabase.size(); i++) {
        Flashcard flashcard = flashcardDatabase.getFlashcard(i);
        flashcard.reloadData();
      }

      // Reload the flashcard counter.
      updateFlashcardCounter((Label) root.lookup("#flashcardCounter"));
    });
  }


  private void changeFlashcard(Flashcard newFlashcard) {
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
}