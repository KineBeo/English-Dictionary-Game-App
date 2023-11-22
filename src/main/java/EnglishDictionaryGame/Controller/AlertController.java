package EnglishDictionaryGame.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AlertController {

  @FXML private AnchorPane anchorPane;

  @FXML private Label messageLabel;

  @FXML private Label titleLabel;
  @FXML private Pane bar;

  public void quit() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }

  public void setMessage(String message) {
    messageLabel.setText(message);
  }

  public void setTitle(String message) {
    titleLabel.setText(message);
  }
}
