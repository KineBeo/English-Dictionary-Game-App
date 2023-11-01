package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Trie;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class DailyWordController extends WordOperation {

  @FXML private AnchorPane anchorPane;
  @FXML private WebView webView;
  @FXML private Label randomWord;

  @Override
  public void saveWord() {}

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void initialize() {
    Random random = new Random();
    String randomWord = Trie.getAllWordsFromTrie().get(random.nextInt(200));
    this.randomWord.setText(randomWord);
    String definition = Application.database.lookUpWord(randomWord);
    definition =
        "<html><body bgcolor='white' style='color:"
            + "black"
            + "; font-weight: bold; font-size: 20px;'>"
            + definition
            + "</body></html>";

    webView.getEngine().loadContent(definition, "text/html");
  }
}
