package EnglishDictionaryGame.Controller;

import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class HangmanController {
  @FXML
  private Pane man;
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

  private int mistakes;
  private int correct;
  private final WordsOfHangMan word = new WordsOfHangMan();
  private String myWord;
  private List<String> myLetters;
  private List<String> answer;

  public HangmanController() {}

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
    myWord = word.getRandomWord();
    myLetters = Arrays.asList(myWord.split(""));
    answer = Arrays.asList(new String[myLetters.size() * 2]);
    for (int i = 0; i < myLetters.size() * 2; i++) {
      if (i % 2 == 0) {
        answer.set(i, "_");
      } else {
        answer.set(i, " ");
      }
    }
    String res = String.join("", answer);
    text.setText(res);
    winStatus.setText("");
    realWord.setText("");
    buttons.setDisable(false);
  }

  public void onClick(ActionEvent event) {
    String letter = ((Button) event.getSource()).getText();
    ((Button) event.getSource()).setDisable(true);
    if (myLetters.contains(letter)) {
      correct++;
      int letterIndex = myLetters.indexOf(letter);
      answer.set(letterIndex * 2, letter);
      String res = String.join("", answer);
      text.setText(res);
      if (correct == myWord.length()) {
        winStatus.setText("You Win!");
        buttons.setDisable(true);
      }
    } else {
      mistakes++;
      if (mistakes == 1) base1.setVisible(true);
      else if (mistakes == 2) base2.setVisible(true);
      else if (mistakes == 3) base3.setVisible(true);
      else if (mistakes == 4) pole.setVisible(true);
      else if (mistakes == 5) rod.setVisible(true);
      else if (mistakes == 6) rope1.setVisible(true);
      else if (mistakes == 7) rope2.setVisible(true);
      else if (mistakes == 8) {
        rope2.setVisible(false);
        man.setVisible(true);
        winStatus.setText("You Lose!");
        realWord.setText("The actual word was " + myWord);
        buttons.setDisable(true);
      }
    }
  }

  public void newGame() {
    for (int i = 0; i < 26; i++) {
      buttons.getChildren().get(i).setDisable(false);
    }
    initialize();
  }
}
