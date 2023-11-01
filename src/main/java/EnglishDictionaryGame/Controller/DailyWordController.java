package EnglishDictionaryGame.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DailyWordController extends WordOperation {

  @FXML private AnchorPane anchorPane;

  @Override
  public void saveWord() {}

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
