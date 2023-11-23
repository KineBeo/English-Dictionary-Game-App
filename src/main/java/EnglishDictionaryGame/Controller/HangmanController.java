package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.TranslationService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class HangmanController {
  @FXML private Pane man;
  @FXML private Pane base1;
  @FXML private Pane base2;
  @FXML private Pane base3;
  @FXML private Pane pole;
  @FXML private Pane rod;
  @FXML private Pane rope1;
  @FXML private Pane rope2;
  @FXML private Text text;
  @FXML private Pane buttons;
  @FXML private Text winStatus;
  @FXML private Text realWord;
  @FXML private Label hintText;
  @FXML private Label lengthHintText;

  private int mistakes;
  private int correct;
  private final WordsOfHangMan word = new WordsOfHangMan();
  private String myWord;
  private StringBuilder displayWord;
  private StringBuilder guessedLetters;

  public void initialize() {
    base1.setVisible(false);
    base2.setVisible(false);
    base3.setVisible(false);
    pole.setVisible(false);
    rod.setVisible(false);
    rope1.setVisible(false);
    rope2.setVisible(false);
    man.setVisible(false);
    mistakes = 0;
    correct = 0;
    myWord = word.getRandomWord().toLowerCase(); // Make it case-insensitive
    new Thread(() -> Platform.runLater(this::setHint)).start();
    displayWord = new StringBuilder();
    guessedLetters = new StringBuilder();

    for (int i = 0; i < myWord.length(); i++) {
      if (Character.isWhitespace(myWord.charAt(i))) {
        displayWord.append(" "); // Display spaces
      } else {
        displayWord.append("_");
      }
    }

    text.setText(displayWord.toString());
    winStatus.setText("");
    realWord.setText("");
    buttons.setDisable(false);
  }

  private void setHint() {
    String hint = TranslationService.translate(myWord, "en", "vi");
    hintText.setText("Hint: " + hint);
    lengthHintText.setText("Length: " + myWord.length());
  }

  public void onClick(ActionEvent event) {
    String letter =
        ((Button) event.getSource()).getText().toLowerCase(); // Make it case-insensitive
    ((Button) event.getSource()).setDisable(true);

    if (myWord.contains(letter)) {
      handleCorrectGuess(letter);
    } else {
      handleIncorrectGuess();
    }
  }

  private void handleCorrectGuess(String letter) {
    guessedLetters.append(letter);
    for (int i = 0; i < myWord.length(); i++) {
      if (Character.toString(myWord.charAt(i)).equals(letter)) {
        displayWord.setCharAt(i, letter.charAt(0));
        correct++;
      }
    }

    text.setText(displayWord.toString());

    if (correct == myWord.length()) {
      winStatus.setText("You Win!");
      buttons.setDisable(true);
    }
  }

  private void handleIncorrectGuess() {
    mistakes++;
    showHangmanPart();

    if (mistakes == 8) {
      endGame(false);
    }
  }

  private void showHangmanPart() {
    switch (mistakes) {
      case 1 -> base1.setVisible(true);
      case 2 -> base2.setVisible(true);
      case 3 -> base3.setVisible(true);
      case 4 -> pole.setVisible(true);
      case 5 -> rod.setVisible(true);
      case 6 -> rope1.setVisible(true);
      case 7 -> rope2.setVisible(true);
      case 8 -> {
        rope2.setVisible(false);
        man.setVisible(true);
        endGame(true);
      }
    }
  }

  private void endGame(boolean playerWins) {
    if (playerWins) {
      winStatus.setText("You Lose!");
      realWord.setText("The actual word was " + myWord);
    }
    buttons.setDisable(true);
  }

  public void newGame() {
    for (int i = 0; i < 26; i++) {
      buttons.getChildren().get(i).setDisable(false);
    }
    initialize();
  }
}
