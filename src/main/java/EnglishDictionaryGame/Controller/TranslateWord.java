package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.TranslationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class TranslateWord extends WordOperation {

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private HTMLEditor htmlEditor;

  @FXML
  private TextField inputText;

  @FXML
  private void initialize() {
  }

  public void saveWord() {
  }

  public void translateWord() {
    String translationTarget = inputText.getText();
    String translation = TranslationService.translate(translationTarget, "en", "vi");

    htmlEditor.setHtmlText(translation);
  }

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}