package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.QuizFactory;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.swing.*;

public class QuizController {
  @FXML private Button option1;

  @FXML private Button option2;

  @FXML private Button option3;

  @FXML private Button option4;

  @FXML private Label question;

  private static QuizFactory quiz;

  public QuizController() {
    quiz = new QuizFactory();
  }

  @FXML
  private void initialize() {

    startQuizGame();

    /** Generate options. */
    option1.setOnMouseClicked(mouseEvent -> option1Clicked());
    option2.setOnMouseClicked(mouseEvent -> option2Clicked());
  }

  int counter = 0;
  static int correct = 0;
  static int wrong = 0;

  private void startQuizGame() {
    loadQuestion();
    setChoices();
  }

  private void loadQuestion() {
    if (counter == 0) {
      question.setText(quiz.createQuestion());
    }
  }

  private void setChoices() {
    for (Button button : List.of(option1, option2, option3, option4)) {
      button.setText(
          quiz.getChoices()[List.of(option1, option2, option3, option4).indexOf(button)]);
    }
  }

  private boolean checkAnswer(String answer) {
    if (counter == 0) {
      if (answer.equals("to give up")) {
        return true;
      } else {
        return false;
      }
    }

    if (counter == 1) {
      if (answer.equals("to lower in rank")) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  public void option1Clicked() {
    if (checkAnswer(option1.getText())) {
      correct++;
    } else {
      wrong++;
    }

    if (counter == 9) {

    } else {
      counter++;
      loadQuestion();
    }
  }

  public void option2Clicked() {
    checkAnswer(option2.getText());
    if (checkAnswer(option2.getText())) {
      correct++;
    } else {
      wrong++;
    }

    if (counter == 9) {

    } else {
      counter++;
      loadQuestion();
    }
  }

  public void option3Clicked(Action event) {}

  public void option4Clicked(Action event) {}
}
