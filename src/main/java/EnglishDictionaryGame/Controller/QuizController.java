package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.QuizFactory;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
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
  @FXML private static Timer currentTimer;

  private static QuizFactory quiz;
  private static final int NUMBER_OF_QUESTIONS = 10;
  private static final long TIME = 60;
  static int counter = 1;
  static int correct = 0;
  static int wrong = 0;
  static int numberOfQuestion;

  private static long min, sec, hour, totalSecond = 0;

  public QuizController() {
    quiz = new QuizFactory();
    quiz.setQuestionNumber(NUMBER_OF_QUESTIONS);
    numberOfQuestion = quiz.getQuestionNumber();
  }

  @FXML
  private void initialize() {
    setCss();
    startQuizGame();
    setTimer();
    chooseButton(option1);
    chooseButton(option2);
    chooseButton(option3);
    chooseButton(option4);

    newQuizButton.setOnMouseClicked(
        mouseEvent -> {
          resetTimer();
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

  public String format(long value) {
    if (value < 10) {
      return 0 + String.valueOf(value);
    }

    return String.valueOf(value);
  }

  public void convertTime() {
    min = TimeUnit.SECONDS.toMinutes(totalSecond);
    sec = totalSecond - TimeUnit.MINUTES.toSeconds(min);
    hour = TimeUnit.MINUTES.toHours(min);
    min = min - TimeUnit.HOURS.toMinutes(hour);
    quizTimer.setText(format(hour) + ":" + format(min) + ":" + format(sec));
    totalSecond--;
  }

  public void setTimer() {
    if (currentTimer != null) {
      currentTimer.cancel();
    }
    totalSecond = TIME;
    currentTimer = new Timer();
    TimerTask timerTask =
        new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(
                () -> {
                  convertTime();
                  System.out.println("still");
                  if (totalSecond < 0) {
                    currentTimer.cancel();
                    quizTimer.setText("00:00:00");
                    newQuizButton.setOnMouseClicked(
                        event -> {
                          resetTimer();
                          startNewQuiz();
                        });
                    try {
                      FXMLLoader loader =
                          new FXMLLoader(Main.class.getResource("fxml/QuizResult.fxml"));
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
                });
          }
        };

    // preriod: lặp lại
    currentTimer.schedule(timerTask, 0, 1000);
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
        currentTimer.cancel();
      }
    }
  }

  private void setCss() {
    for (Button button : List.of(option1, option2, option3, option4, newQuizButton, nextQuestion)) {
      button
          .getStylesheets()
          .add(Objects.requireNonNull(Main.class.getResource("css/quiz.css")).toExternalForm());
    }
    for (Label label : List.of(question, showingQuestionNumber, quizTimer)) {
      label
          .getStylesheets()
          .add(Objects.requireNonNull(Main.class.getResource("css/quiz.css")).toExternalForm());
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
    totalSecond = TIME;
    numberOfQuestion = NUMBER_OF_QUESTIONS;
  }

  public void resetTimer() {
    if (currentTimer != null) {
      currentTimer.cancel();
      totalSecond = TIME;
      setTimer();
    }
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
    return currentTimer;
  }

  public static void cancelTimer() {
    if (!Application.currentStage.equals("QUIZ")) {
      currentTimer.cancel();
    }
  }
}
