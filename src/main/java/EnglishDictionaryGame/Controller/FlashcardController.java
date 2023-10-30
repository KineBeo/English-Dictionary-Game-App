package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Exceptions.Utils;
import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlashcardController {

  private Stage stage = null;
  private Flashcard currentFlashcard = null;
  private static final String FLASHCARD_SCREEN_FXML_PATH = "fxml/FlashcardScreen.fxml";

  public FlashcardController() {
  }
  public FlashcardController(Stage stage, Flashcard controlledFlashcard) {
    this.stage = stage;
    this.currentFlashcard = controlledFlashcard;
  }

  public void addFlashcard(String frontText, String backText) {
    this.currentFlashcard = new Flashcard(frontText, backText);
  }

  public void createFlashcardWindow() {
    this.stage = createFlashcardStage();
    this.stage.showAndWait();
  }

  private Stage createFlashcardStage() {
    StackPane root = createRoot();
    Scene scene = createScene(root);
    setAllButtonsBehaviors(root);
    return createStage(scene);
  }

  private StackPane createRoot() {
    StackPane root = new StackPane(); // Initialize root in case of an exception

    // Loading the buttons in the FXML file.
    FXMLLoader loader = new FXMLLoader(Main.class.getResource(FLASHCARD_SCREEN_FXML_PATH));
    try {
      root = loader.load();
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }

    // Loading the current controller's currentFlashcard.
    try {
      root.getChildren().addAll(currentFlashcard.getCardView());
    } catch (Exception e) {
      System.out.println("Flashcard controller's currentFlashcard is null");
      Utils.printRelevantStackTrace(e);
    }

    return root;
  }

  private Scene createScene(Parent root) {
    Scene flashcardScene = new Scene(root);
    flashcardScene.setCamera(new PerspectiveCamera());
    return flashcardScene;
  }

  private Stage createStage(Scene scene) {
    Stage flashcardStage = new Stage();
    flashcardStage.setScene(scene);
    flashcardStage.setTitle("Flashcard");
    return flashcardStage;
  }

  private void setAllButtonsBehaviors(Parent root) {
    Button flipFlashcardButton = (Button) root.lookup("#flipFlashcardButton");
    setFlipFlashcardButtonBehavior(flipFlashcardButton);

    Button nextFlashcardButton = (Button) root.lookup("#nextFlashcardButton");
    setNextFlashcardButtonBehavior(nextFlashcardButton);
  }

  private void setFlipFlashcardButtonBehavior(Button flipFlashcardButton) {
    flipFlashcardButton.setOnMouseClicked(e -> {
      flipFlashcard();
    });
  }
  private void setNextFlashcardButtonBehavior(Button nextFlashcardButton) {
    nextFlashcardButton.setOnMouseClicked(e -> {
      Flashcard testFlashcard = new Flashcard("Test front", "Test back");
      changeFlashcard(testFlashcard);
    });
  }

  public void flipFlashcard() {
    RotateTransition rotator = createRotator();
    PauseTransition ptChangeCardFace = changeCardFace();

    ParallelTransition parallelTransition = new ParallelTransition(rotator, ptChangeCardFace);
    parallelTransition.play();
  }

  private RotateTransition createRotator() {
    RotateTransition rotator = new RotateTransition(Duration.millis(1000), currentFlashcard.getCardView());
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
}