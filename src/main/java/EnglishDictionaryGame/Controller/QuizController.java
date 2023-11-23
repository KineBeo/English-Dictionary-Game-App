package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.QuizFactory;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuizController {

  @FXML private AnchorPane anchorPane;

  @FXML private Button option1;

  @FXML private Button option2;

  @FXML private Button option3;

  @FXML private Button option4;

  @FXML private Label question;

  @FXML private Label showingQuestionNumber;
  @FXML private Label quizTimer;
  @FXML private Button nextQuestion;
  @FXML private Button newQuizButton;
  @FXML private Label correctAnswerText;
  @FXML private static boolean answerSelected = false;

  private static QuizFactory quiz;
  private static final int NUMBER_OF_QUESTIONS = 10;

  static int counter = 1;
  static int correct = 0;
  static int wrong = 0;
  static int numberOfQuestion;
  public QuizController() {
    quiz = new QuizFactory();
    quiz.setQuestionNumber(NUMBER_OF_QUESTIONS);
    numberOfQuestion = quiz.getQuestionNumber();
  }

  @FXML
  private void initialize() {
    setCss();
    startQuizGame();
    GameTimer object = new QuizTimer();
    QuizTimer.setTimer(quizTimer, object);
    chooseButton(option1);
    chooseButton(option2);
    chooseButton(option3);
    chooseButton(option4);

    newQuizButton.setOnMouseClicked(
        mouseEvent -> {
          correctAnswerText.setText("");
          QuizTimer.resetTimer(quizTimer);
          startNewQuiz();
        });
    nextQuestion.setOnMouseClicked(event -> handleNextQuestion());
  }

  private void startNewQuiz() {
    resetQuizGame();
    startQuizGame();
  }

  private void startQuizGame() {
    loadQuestion();
    setChoices();
    setShowingQuestionNumber();
  }

  private void loadQuestion() {
    question.setText(quiz.createQuestion());
  }

  private void setChoices() {
    for (Button button : List.of(option1, option2, option3, option4)) {
      button.setText(
          quiz.getChoices()[List.of(option1, option2, option3, option4).indexOf(button)]);
    }
  }

  private void chooseButton(Button button) {
    button.setOnMouseClicked(
        mouseEvent -> {
          if (!answerSelected) {
            answerSelected = true;
            quiz.setChooseAnswer(button.getText());
            if (checkAnswer()) {
              correct++;
              correctAnswerText.setStyle("-fx-text-fill: green; -fx-font-weight: bold");
              correctAnswerText.setText("You made the right choice!");
            } else if (!checkAnswer()) {
              correctAnswerText.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
              correctAnswerText.setText(
                  "You made the wrong choice, the correct answer is: " + quiz.getCorrectAnswer());
              wrong++;
            }
            System.out.println(
                "Choice: "
                    + quiz.getChooseAnswer()
                    + ", check correct answer: "
                    + quiz.getChooseAnswer().equals(quiz.getCorrectAnswer()));
            System.out.println("String: " + correctAnswerText.getText());
          }
        });
  }

  private void handleNextQuestion() {
    if (answerSelected) {
      answerSelected = false;
      correctAnswerText.setText("");
      counter++;
      if (counter <= numberOfQuestion) {
        loadQuestion();
        setChoices();
        setShowingQuestionNumber();
      } else if (counter > 10) {
        openResultStage();
        QuizTimer.getCurrentTimer().cancel();
      }
    }
  }

  private void setCss() {
    for (Button button : List.of(option1, option2, option3, option4, newQuizButton, nextQuestion)) {
      button
          .getStylesheets()
          .add(Objects.requireNonNull(Main.class.getResource("css/Quiz.css")).toExternalForm());
    }
    for (Label label : List.of(question, showingQuestionNumber, quizTimer)) {
      label
          .getStylesheets()
          .add(Objects.requireNonNull(Main.class.getResource("css/Quiz.css")).toExternalForm());
    }
  }

  private boolean checkAnswer() {
    System.out.println("Correct Answer: " + quiz.getCorrectAnswer());
    return quiz.checkAnswer();
  }

  private void setShowingQuestionNumber() {
    showingQuestionNumber.setText("Questions: " + counter + "/10");
  }

  public static void resetQuizGame() {
    correct = wrong = 0;
    counter = 1;
    quiz = new QuizFactory();
    QuizTimer.setTotalSecond(QuizTimer.TIME);
    numberOfQuestion = NUMBER_OF_QUESTIONS;
  }

  public void openResultStage() {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/QuizResult.fxml"));
      Scene quizscene = new Scene(loader.load());
      quizscene.setFill(Color.TRANSPARENT);
      Stage quizstage = new Stage();
      quizstage.setScene(quizscene);
      quizstage.initStyle(StageStyle.TRANSPARENT);
      quizstage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Timer getCurrentTimer() {
    return QuizTimer.getCurrentTimer();
  }

  public static void cancelTimer() {
    if (!Application.currentStage.equals("QUIZ")) {
      QuizTimer.getCurrentTimer().cancel();
    }
  }
}
