package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Exceptions.Utils;
import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Flashcard;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlashcardController {

  private Stage stage = null;
  private Flashcard flashcard = null;
  private static final String FLASHCARD_SCREEN_FXML_PATH = "fxml/FlashcardScreen.fxml";

  public FlashcardController() {
  }
  public FlashcardController(Stage stage, Flashcard controlledFlashcard) {
    this.stage = stage;
    this.flashcard = controlledFlashcard;
  }

  public void createFlashcard(String frontText, String backText) {
    this.flashcard = new Flashcard(frontText, backText);
  }

  public void showFlashcardStage() {
    this.stage = createFlashcardStage();
    this.stage.showAndWait();
  }

  public void flipFlashcard() {
    RotateTransition rotator = createRotator();
    PauseTransition ptChangeCardFace = changeCardFace();

    ParallelTransition parallelTransition = new ParallelTransition(rotator, ptChangeCardFace);
    parallelTransition.play();
  }

  private void setFlipFlashcardButtonBehavior(Button flipFlashcardButton) {
    flipFlashcardButton.setOnMouseClicked(e -> {
      flipFlashcard();
    });
  }

  private Stage createFlashcardStage() {
    Stage flashcardStage = new Stage();
    StackPane root = new StackPane(); // Initalize root in case of exception

    // Loading the buttons in the FXML file.
    FXMLLoader loader = new FXMLLoader(Main.class.getResource(FLASHCARD_SCREEN_FXML_PATH));
    try {
      root = loader.load();
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }


    // Loading the current controller's flashcard.
    try {
      root.getChildren().addAll(flashcard.getCardView());
    } catch (Exception e) {
      System.out.println("Flashcard controller's flashcard is null");
      Utils.printRelevantStackTrace(e);
    }

    // Setting the scene.
    Scene flashcardScene = new Scene(root);
    flashcardScene.setCamera(new PerspectiveCamera());

    // Setting the stage.
    flashcardStage.setScene(flashcardScene);
    flashcardStage.setTitle("Flashcard");

    // Setting the button's behaviors
    Button flipFlashcardButton = (Button) root.lookup("#flipFlashcardButton");
    setFlipFlashcardButtonBehavior(flipFlashcardButton);

    return flashcardStage;
  }

  private RotateTransition createRotator() {
    RotateTransition rotator = new RotateTransition(Duration.millis(1000), flashcard.getCardView());
    rotator.setAxis(Rotate.X_AXIS);

    if (flashcard.isShowingFront()) {
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
          flashcard.flipCardView();
        }
    );

    return pause;
  }
}