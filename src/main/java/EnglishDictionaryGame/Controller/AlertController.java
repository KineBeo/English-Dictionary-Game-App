package EnglishDictionaryGame.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AlertController extends WordOperation {

  @FXML private AnchorPane anchorPane;

  @FXML private Label messageLabel;

  @Override
  public void saveWord() {}

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  public void setMessage(String message) {
    messageLabel.setText(message);
  }
}
