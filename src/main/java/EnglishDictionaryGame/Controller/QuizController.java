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
  @FXML private static boolean answerSelected = false;

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

    newQuizButton.setOnMouseClicked(mouseEvent -> startNewQuiz());
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
    System.out.println("Controller correct answer check: " + quiz.getCorrectAnswer());
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
    totalSecond = TIME;
    Timer timer = new Timer();
    TimerTask timerTask =
        new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(
                () -> {
                  convertTime();
                  if (totalSecond < 0) {
                    timer.cancel();
                    quizTimer.setText("00:00:00");
                  }
                });
          }
        };

    // preriod: lặp lại
    timer.schedule(timerTask, 0, 1000);
  }

  private void chooseButton(Button button) {
    button.setOnMouseClicked(
        mouseEvent -> {
          if (!answerSelected) {
            answerSelected = true;
            quiz.setChooseAnswer(button.getText());
            if (checkAnswer()) {
              System.out.println("ok");
              correct++;
            } else if (!checkAnswer()) {
              System.out.println("no");
              wrong++;
            }
            System.out.println(
                "Choice: "
                    + quiz.getChooseAnswer()
                    + ", check correct answer: "
                    + quiz.getChooseAnswer().equals(quiz.getCorrectAnswer()));
            System.out.println("correct point: " + correct + ", wrong point: " + wrong);
          }
        });
  }

  private void handleNextQuestion() {
    if (answerSelected) {
      answerSelected = false;
      counter++;
      if (counter <= numberOfQuestion) {
        loadQuestion();
        setChoices();
        setShowingQuestionNumber();
      } else if (counter > 10) {
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
    }
  }

  private void setCss() {
    for (Button button : List.of(option1, option2, option3, option4, newQuizButton)) {
      button
          .getStylesheets()
          .add(Objects.requireNonNull(Main.class.getResource("css/quiz.css")).toExternalForm());
    }
    question
        .getStylesheets()
        .add(Objects.requireNonNull(Main.class.getResource("css/quiz.css")).toExternalForm());
    showingQuestionNumber
        .getStylesheets()
        .add(Objects.requireNonNull(Main.class.getResource("css/quiz.css")).toExternalForm());
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
}
