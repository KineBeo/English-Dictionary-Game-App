package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Exceptions.Utils;
import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import EnglishDictionaryGame.Server.FlashcardDatabase;
import EnglishDictionaryGame.Server.FlashcardStageFactory;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlashcardController {

  private FlashcardDatabase flashcardDatabase;
  private Stage stage = null;
  private Flashcard currentFlashcard = null;
  private int currentFlashcardCount = -1;
  private final String FLASHCARD_SCREEN_FXML_PATH = "fxml/FlashcardScreen.fxml";

  public FlashcardController() {
    flashcardDatabase = new FlashcardDatabase();
    this.currentFlashcard = flashcardDatabase.getFlashcard(0);
    currentFlashcardCount = 1;

    this.stage = FlashcardStageFactory.createFlashcardStage();
  }

  public void createFlashcardWindow() {
    StackPane root = (StackPane) stage.getScene().getRoot();
    setAllElementsBehaviors(root);
    updateFlashcardCount((Label) root.lookup("#flashcardCounter"));
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
      flipFlashcard();
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
      updateFlashcardCount((Label) root.lookup("#flashcardCounter"));
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
      updateFlashcardCount((Label) root.lookup("#flashcardCounter"));
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
      EditFlashcardController editFlashcardController = createAddFlashcardController();
      editFlashcardController.createWindow();
      System.out.println("Edit flashcard controller exited.");

      // Update the flashcard database.
      this.flashcardDatabase = editFlashcardController.getNewFlashcardDatabase();
      this.flashcardDatabase.saveToFile();

      // Reload the current flashcard.
      currentFlashcard.flipCardView();
      currentFlashcard.flipCardView();
    });
  }

  public void flipFlashcard() {
    RotateTransition rotator = createRotator();
    PauseTransition ptChangeCardFace = changeCardFace();

    ParallelTransition parallelTransition = new ParallelTransition(rotator, ptChangeCardFace);
    parallelTransition.play();
  }



  private PauseTransition changeCardFace() {
    PauseTransition pause = new PauseTransition(Duration.millis(500));
    pause.setOnFinished(
        e -> {
          currentFlashcard.flipCardView();
        }
    );

    return pause;
  }

  private void changeFlashcard(Flashcard newFlashcard) {
    StackPane root = (StackPane) stage.getScene().getRoot();

    try {
      if (root.getChildren().contains(currentFlashcard.getCardView())) {
        root.getChildren().remove(currentFlashcard.getCardView());
      }

      root.getChildren().add(newFlashcard.getCardView());
      currentFlashcard = newFlashcard;
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

  private void updateFlashcardCount(Label flashcardCounter) {
    flashcardCounter.setText(currentFlashcardCount + " / " + flashcardDatabase.size());
  }

  private EditFlashcardController createAddFlashcardController() {
    EditFlashcardController editFlashcardController = new EditFlashcardController(
        this.flashcardDatabase);
    return editFlashcardController;
  }

  private RotateTransition createRotator() {
    RotateTransition rotator = new RotateTransition(Duration.millis(1000),
        currentFlashcard.getCardView());
    rotator.setAxis(Rotate.X_AXIS);

    if (currentFlashcard.isShowingFront()) {
      rotator.setFromAngle(0);
      rotator.setToAngle(180);
    } else {
      rotator.setFromAngle(180);
      rotator.setToAngle(360);
    }
    rotator.setInterpolator(Interpolator.LINEAR);
    rotator.setCycleCount(1);

    return rotator;
  }
  private void closeFlashcards() {
    stage.close();
  }
}