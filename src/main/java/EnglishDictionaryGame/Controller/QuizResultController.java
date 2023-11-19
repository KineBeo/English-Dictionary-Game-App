package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class QuizResultController {
  @FXML public Label remark, marks, marksText, correctText, wrongText;
  @FXML public ProgressIndicator correct_progress, wrong_progress;

  @FXML private Button newQuizButton;

  int correct;
  int wrong;

  @FXML
  private void initialize() {
    newQuizButton
        .getStylesheets()
        .add(Objects.requireNonNull(Main.class.getResource("css/quiz.css")).toExternalForm());
    correct = QuizController.correct;
    wrong = QuizController.wrong;

    correctText.setText("Correct Answers : " + correct);
    wrongText.setText("Incorrect Answers : " + QuizController.wrong);

    marks.setText(correct + "/10");
    float correctf = (float) correct / 10;
    correct_progress.setProgress(correctf);

    float wrongf = (float) wrong / 10;
    wrong_progress.setProgress(wrongf);

    marksText.setText(correct + " Marks Scored");

    if (correct < 2) {
      remark.setText(
          "Oh no..! You have failed the quiz. It seems that you need to improve your general knowledge. Practice daily! Check your results here.");
    } else if (correct < 5) {
      remark.setText(
          "Oops..! You have scored less marks. It seems like you need to improve your general knowledge. Check your results here.");
    } else if (correct <= 7) {
      remark.setText(
          "Good. A bit more improvement might help you to get better results. Practice is the key to success. Check your results here.");
    } else if (correct == 8 || correct == 9) {
      remark.setText(
          "Congratulations! Its your hardwork and determination which helped you to score good marks. Check you results here.");
    } else if (correct == 10) {
      remark.setText(
          "Congratulations! You have passed the quiz with full marks because of your hardwork and dedication towards studies. Keep it up! Check your results here.");
    }
  }

  private void newQuiz() {
    try {
      newQuizButton.setOnMouseClicked(
          mouseEvent -> {
            System.out.println("Clicked!");
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
