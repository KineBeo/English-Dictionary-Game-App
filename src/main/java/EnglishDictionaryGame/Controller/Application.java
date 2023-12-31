package EnglishDictionaryGame.Controller;

import static EnglishDictionaryGame.Main.database;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.PronunciationService;
import EnglishDictionaryGame.Server.Trie;
import EnglishDictionaryGame.Server.WordInfo;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Application implements Initializable {

  @FXML private Pane homeSlider;

  @FXML private BorderPane borderPane;

  @FXML private Label homeButton;

  @FXML private TextField inputText;

  @FXML private ListView<String> searchList;

  @FXML private WebView webView;

  @FXML private Label menu;

  @FXML private Label menuClose;

  @FXML private AnchorPane slider;

  @FXML private Label addButton;

  @FXML private Label deleteButton;

  @FXML private Label editButton;

  @FXML private Label hangmanButton;

  @FXML private Label quizButton;

  @FXML private Label translateButton;

  @FXML private Label pronounceButton;

  @FXML private Label informationButton;

  @FXML private Label dailyWordButton;

  @FXML private Label flashCardButton;

  @FXML private Label settingButton;

  @FXML private ImageView exitButton;

  @FXML private HBox applicationBar;
  private int lastIndex = 0;
  public static String editTarget;
  public static String definitionColor = "#34586F";
  public static String wordColor = "#0088CD";
  public static ArrayList<Label> buttons = new ArrayList<>();
  private volatile String currentQuery = "";
  public static String currentStage = "";

  public static ArrayList<WordInfo> allWords = new ArrayList<>();

  enum stageType {
    HOME,
    HANGMAN_GAME,
    QUIZ_GAME,
    FLASHCARD_GAME,
    DAILY_WORD,
    SETTING,
    ABOUT
  }

  public Application() {
    try {
      database.initialize();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    allWords = database.getAllWordTargets();
    borderPane.setVisible(false);
    preparedSearchList();
    inputText
        .textProperty()
        .addListener((observableValue, oldValue, newValue) -> performSearch(newValue));
    applicationBar.setStyle("-fx-background-color: #30abf3");
    buttons.add(addButton);
    buttons.add(deleteButton);
    buttons.add(editButton);
    buttons.add(translateButton);
    buttons.add(pronounceButton);
    buttons.add(hangmanButton);
    buttons.add(homeButton);
    buttons.add(quizButton);
    buttons.add(informationButton);
    buttons.add(dailyWordButton);
    buttons.add(settingButton);
    buttons.add(flashCardButton);
    menuSlider();
    home();
    addingWord();
    deleteWord();
    updateWord();
    translateWord();
    pronounceWord();
    hangMan();
    about();
    dailyWord();
    flashCard();
    quizGame();
    setting();
    exitButton.setOnMouseClicked(mouseEvent -> System.exit(0));
  }

  /** Refresh the list view. */
  public void preparedSearchList() {
    String target = inputText.getText();
    ArrayList<String> words = Trie.search(formatFirstLetter(target));
    searchList.setItems(FXCollections.observableArrayList(words));
  }

  private void performSearch(String target) {
    if (target == null) {
      return;
    }

    currentQuery = target;
    Task<WordInfo> searchTask =
        new Task<>() {
          @Override
          protected WordInfo call() {
            return database.findWord(target);
          }
        };

    searchTask.setOnSucceeded(
        (WorkerStateEvent event) -> {
          WordInfo wordInfo = searchTask.getValue();
          if (target.equals(currentQuery)) {
            updateWebView(wordInfo);
          }
        });

    new Thread(searchTask).start();
  }

  @FXML
  public void findWord() {
    String target = inputText.getText();
    performSearch(formatFirstLetter(target));
  }

  private String formatFirstLetter(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }

    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }

  private void updateWebView(WordInfo wordInfo) {
    String htmlContent;
    if (inputText.getText().isBlank() || inputText.getText().isEmpty()) {
      htmlContent = "";
    } else if (wordInfo == null || wordInfo.getWord() == null) {
      htmlContent =
          "<html><body bgcolor='white' style='color:; font-weight: bold; font-size: 20px;'>"
              + "<p>Cannot find word: "
              + inputText.getText()
              + "!</p>"
              + "</body></html>";
    } else {
      String word = wordInfo.getWord();
      String type = !wordInfo.getType().equals("") ? wordInfo.getType() : "Not found";
      String meaning =
          !wordInfo.getMeaning().equals("") ? wordInfo.getMeaning() : "No meaning found";
      String pronunciation =
          !wordInfo.getPronunciation().equals("")
              ? wordInfo.getPronunciation()
              : "Not pronunciation found";
      String example =
          !wordInfo.getExample().equals("") ? wordInfo.getExample() : "No example found";
      String synonym =
          !wordInfo.getSynonym().equals("") ? wordInfo.getSynonym() : "No synonym found";
      String antonyms =
          !wordInfo.getAntonyms().equals("") ? wordInfo.getAntonyms() : "No antonyms found";

      htmlContent =
          "<html><body bgcolor='white' style='font-weight: bold; font-size: 18px;'>"
              + "<p style='font-size: 30px; color: "
              + wordColor
              + ";'><b>Word: </b>"
              + word
              + "</p>"
              + "<p style='font-size: 20px; font-style: italic; font-weight: bold; color: black;'><i>Pronunciation: </i>"
              + pronunciation
              + "</p>"
              + "<p style='font-size: 18px; font-style: italic; font-weight: bold; color: black;'><i>Type: </i>"
              + type
              + "</p>"
              + "<p style='font-weight: normal;'><b>Definition: </b>"
              + meaning
              + "</p>"
              + "<p style='font-style: italic; font-weight: normal; color: black;'><i>Example: </i>"
              + example
              + "</p>"
              + "<p style='color: green;'><b>Synonym: </b>"
              + synonym
              + "</p>"
              + "<p style='color: red;'><b>Antonyms: </b>"
              + antonyms
              + "</p>"
              + "</body></html>";
    }

    //        Platform.runLater(() -> webView.getEngine().loadContent(htmlContent, "text/html"));
    webView.getEngine().loadContent(htmlContent, "text/html");
  }

  @FXML
  public void addingWord() {
    new Thread(
            () ->
                addButton.setOnMouseClicked(
                    mouseEvent -> {
                      try {
                        AnchorPane view =
                            FXMLLoader.load(
                                Objects.requireNonNull(
                                    Main.class.getResource("fxml/AddWord.fxml")));
                        homeSlider.setVisible(false);
                        borderPane.setVisible(true);
                        borderPane.setCenter(view);
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }))
        .start();
  }

  @FXML
  public void deleteWord() {
    deleteButton.setOnMouseClicked(
        mouseEvent -> {
          String target = searchList.getSelectionModel().getSelectedItem();
          if (target == null || target.equals("")) {
            showAlert("Please choose your word", "Error");
          } else {
            database.deleteWord(target);
            searchList.getItems().remove(target);
            showAlert("Delete the selected word successfully!", "Notification");
            String content = "";
            webView.getEngine().loadContent(content);
          }
        });
  }

  @FXML
  public void updateWord() {
    editTarget = searchList.getSelectionModel().getSelectedItem();

    new Thread(
            () ->
                editButton.setOnMouseClicked(
                    mouseEvent -> {
                      if (editTarget == null) {
                        showAlert("Please choose your word", "Error");
                      } else {
                        try {
                          AnchorPane view =
                              FXMLLoader.load(
                                  Objects.requireNonNull(
                                      Main.class.getResource("fxml/UpdateWord.fxml")));
                          homeSlider.setVisible(false);
                          borderPane.setVisible(true);
                          borderPane.setCenter(view);
                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                      }
                    }))
        .start();
  }

  public void translateWord() {
    new Thread(
            () ->
                translateButton.setOnMouseClicked(
                    mouseEvent -> {
                      try {
                        AnchorPane view =
                            FXMLLoader.load(
                                Objects.requireNonNull(
                                    Main.class.getResource("fxml/TranslateWord.fxml")));
                        homeSlider.setVisible(false);
                        borderPane.setVisible(true);
                        borderPane.setCenter(view);
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }))
        .start();
  }

  @FXML
  public void pronounceWord() {
    pronounceButton.setOnMouseClicked(
        mouseEvent ->
            new Thread(
                    () -> {
                      if (inputText.getText().isBlank() || inputText.getText().isEmpty()) {
                        Platform.runLater(
                            () -> showAlert("Please enter a word to pronounce!", "Error"));
                      }
                      PronunciationService.pronounce(inputText.getText(), "en");
                    })
                .start());
  }

  public void hangMan() {
    new Thread(
            () ->
                hangmanButton.setOnMouseClicked(
                    mouseEvent -> {
                      currentStage = stageType.HANGMAN_GAME.toString();
                      quitTimerOfQuiz();
                      try {
                        AnchorPane view =
                            FXMLLoader.load(
                                Objects.requireNonNull(
                                    Main.class.getResource("fxml/Hangman.fxml")));
                        homeSlider.setVisible(false);
                        borderPane.setVisible(true);
                        borderPane.setCenter(view);
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }))
        .start();
  }

  public void about() {
    informationButton.setOnMouseClicked(
        mouseEvent -> {
          currentStage = stageType.ABOUT.toString();
          quitTimerOfQuiz();
          openStage("fxml/InformationScreen.fxml", "About");
        });
  }

  private void quizGame() {
    new Thread(
            () ->
                quizButton.setOnMouseClicked(
                    mouseEvent -> {
                      currentStage = stageType.QUIZ_GAME.toString();
                      try {
                        AnchorPane view =
                            FXMLLoader.load(
                                Objects.requireNonNull(Main.class.getResource("fxml/Quiz.fxml")));
                        homeSlider.setVisible(false);
                        borderPane.setVisible(true);
                        borderPane.setCenter(view);
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }))
        .start();
  }

  public void flashCard() {
    flashCardButton.setOnMouseClicked(
        mouseEvent -> {
          currentStage = stageType.FLASHCARD_GAME.toString();
          quitTimerOfQuiz();
          FlashcardController flashcardController = new FlashcardController();
          StackPane root = new StackPane();
          try {
            root =
                FXMLLoader.load(
                    Objects.requireNonNull(Main.class.getResource("fxml/FlashcardScreen.fxml")));
            homeSlider.setVisible(false);
            borderPane.setVisible(true);
            borderPane.setCenter(root);
          } catch (Exception e) {
            e.printStackTrace();
          }
          flashcardController.createFlashcardWindow(root);
        });
  }

  /** Open the setting screen. */
  public void setting() {
    settingButton.setOnMouseClicked(
        mouseEvent -> {
          currentStage = stageType.SETTING.toString();
          quitTimerOfQuiz();
          try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Setting.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage addStage = new Stage();
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            addStage.setTitle("Setting");
            SettingController st = loader.getController();
            st.setBarTheme(applicationBar, buttons, searchList);
            addStage.setScene(scene);
            addStage.setResizable(false);
            addStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.initOwner(new Main().getMainStage());
            addStage.showAndWait();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  public void dailyWord() {

    new Thread(
            () ->
                dailyWordButton.setOnMouseClicked(
                    mouseEvent -> {
                      currentStage = stageType.DAILY_WORD.toString();
                      quitTimerOfQuiz();
                      try {
                        AnchorPane view =
                            FXMLLoader.load(
                                Objects.requireNonNull(
                                    Main.class.getResource("fxml/DailyWord.fxml")));
                        homeSlider.setVisible(false);
                        borderPane.setVisible(true);
                        borderPane.setCenter(view);
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }))
        .start();
  }

  private void home() {
    homeButton.setOnMouseClicked(
        mouseEvent -> {
          currentStage = stageType.HOME.toString();
          quitTimerOfQuiz();
          homeSlider.setVisible(true);
          borderPane.setVisible(false);
        });
  }

  public void openStage(String fxml, String title) {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      Stage addStage = new Stage();
      scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
      scene
          .getStylesheets()
          .add(Objects.requireNonNull(Main.class.getResource("css/Alert.css")).toExternalForm());
      addStage.setTitle(title);
      addStage.setScene(scene);
      addStage.setResizable(false);
      addStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
      addStage.initModality(Modality.APPLICATION_MODAL);
      addStage.initOwner(new Main().getMainStage());
      addStage.showAndWait();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void showAlert(String message, String title) {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Alert.fxml"));
      Parent root = loader.load();
      AlertController alertController = loader.getController();
      alertController.setMessage(message);
      alertController.setTitle(title);
      Scene scene = new Scene(root);
      scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
      Stage addStage = new Stage();
      scene
          .getStylesheets()
          .add(Objects.requireNonNull(Main.class.getResource("css/Alert.css")).toExternalForm());
      addStage.setScene(scene);
      addStage.setResizable(false);
      addStage.initModality(Modality.APPLICATION_MODAL);
      addStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
      addStage.initOwner(new Main().getMainStage());
      addStage.showAndWait();
      addStage.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void clickToSelectWord(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 1) {
      String target = searchList.getSelectionModel().getSelectedItem();
      editTarget = target;
      inputText.setText(target);
      findWord();
    }
  }

  @FXML
  public void selectWordUsingKeyBoard(KeyEvent e) {
    if (searchList.getSelectionModel().getSelectedIndices().isEmpty()) {
      return;
    }
    if (e.getCode() == KeyCode.ENTER) {
      String target = searchList.getSelectionModel().getSelectedItem();
      inputText.setText(target);
      findWord();
    } else if (e.getCode() == KeyCode.UP) {
      if (searchList.getSelectionModel().getSelectedIndex() == 0 && lastIndex == 0) {
        inputText.requestFocus();
      }
    }
    lastIndex = searchList.getSelectionModel().getSelectedIndex();
  }

  public void menuSlider() {
    double duration = 0.2;
    homeSlider.setTranslateX(-75);
    borderPane.setTranslateX(-75);
    slider.setTranslateX(-142);

    menu.setOnMouseClicked(
        mouseEvent -> {

          TranslateTransition slide = new TranslateTransition(Duration.seconds(duration), slider);
          slide.setToX(0);

          TranslateTransition slide2 =
              new TranslateTransition(Duration.seconds(duration), homeSlider);
          slide2.setToX(0);

          TranslateTransition slide3 =
              new TranslateTransition(Duration.seconds(duration), borderPane);
          slide3.setToX(0);

          ParallelTransition parallelTransition = new ParallelTransition(slide, slide2, slide3);
          parallelTransition.play();

          parallelTransition.setOnFinished(
              (ActionEvent e) -> {
                menu.setVisible(false);
                menuClose.setVisible(true);
              });
        });

    menuClose.setOnMouseClicked(
        mouseEvent -> {

          TranslateTransition slide = new TranslateTransition(Duration.seconds(duration), slider);
          slide.setToX(-142);

          TranslateTransition slide2 =
              new TranslateTransition(Duration.seconds(duration), homeSlider);
          slide2.setToX(-75);

          TranslateTransition slide3 =
              new TranslateTransition(Duration.seconds(duration), borderPane);
          slide3.setToX(-75);
          ParallelTransition parallelTransition = new ParallelTransition(slide, slide2, slide3);
          parallelTransition.play();

          parallelTransition.setOnFinished(
              (ActionEvent e) -> {
                menu.setVisible(true);
                menuClose.setVisible(false);
              });
        });
  }

  public void quitTimerOfQuiz() {
    if (QuizController.getCurrentTimer() != null) {
      QuizController.cancelTimer();
    }
  }
}
