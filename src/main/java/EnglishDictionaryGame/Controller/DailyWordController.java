package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.PronunciationService;
import EnglishDictionaryGame.Server.Trie;
import java.time.LocalDate;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import static EnglishDictionaryGame.Main.database;

//import static EnglishDictionaryGame.Main.database;

public class DailyWordController extends WordOperation {

  @FXML private AnchorPane anchorPane;
  @FXML private WebView webView;
  @FXML private Label randomWord;
  @FXML private ImageView speaker;

  private static LocalDate currentDate = LocalDate.now();
  private static LocalDate previousDate;
  private static String currentRandomWord;
  private static String currentDefinition;

  @Override
  public void saveWord() {}

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void initialize() {
    System.out.println(previousDate + " " + currentDate);
    if (previousDate == null || !currentDate.equals(previousDate)) {
      Random random = new Random();
      currentRandomWord = Trie.getAllWordsFromTrie().get(random.nextInt(10000));
      this.randomWord.setText(currentRandomWord);
      String definition = database.lookUpWord(currentRandomWord);
      definition =
          "<html><body bgcolor='white' style='color:"
              + "black"
              + "; font-weight: bold; font-size: 20px;'>"
              + definition
              + "</body></html>";

      webView.getEngine().loadContent(definition, "text/html");
      previousDate = currentDate;
      System.out.println(previousDate + " " + currentDate);
    } else if (currentDate.equals(previousDate)) {
      this.randomWord.setText(currentRandomWord);
      String definition = database.lookUpWord(currentRandomWord);
      definition =
          "<html><body bgcolor='white' style='color:"
              + "black"
              + "; font-weight: bold; font-size: 20px;'>"
              + definition
              + "</body></html>";

      webView.getEngine().loadContent(definition, "text/html");
    }
    pronounceWord(currentRandomWord);
  }

  private void pronounceWord(String word) {
    speaker.setOnMouseClicked(
        mouseEvent -> new Thread(() -> PronunciationService.pronounce(word, "en")).start());
  }
}
