package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Flashcard;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class FlashcardViewController {

  private Flashcard flashcard;
  private boolean isAnimationInProgress = false;

  public FlashcardViewController(Flashcard flashcard) {
    this.flashcard = flashcard;
  }

  public void setFlashcard(Flashcard flashcard) {
    this.flashcard = flashcard;
  }

  public void flipFlashcard() {
    if (!isAnimationInProgress) {
      isAnimationInProgress = true;

      RotateTransition rotator = createRotator();
      PauseTransition ptChangeCardFace = changeCardFace();

      ParallelTransition parallelTransition = new ParallelTransition(rotator, ptChangeCardFace);

      // Set an event handler to reset the flag after the animation is finished
      parallelTransition.setOnFinished(event -> isAnimationInProgress = false);

      parallelTransition.play();
    }
  }

  public void reloadFlashcardData() {
    flashcard.reloadData();
  }

  private RotateTransition createRotator() {
    RotateTransition rotator = new RotateTransition(Duration.millis(1000),
        flashcard.getCardView());
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
