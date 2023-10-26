package EnglishDictionaryGame.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingController extends WordOperation {
  @FXML private Label doneButton;
  @FXML private AnchorPane anchorPane;

  @FXML
  public void initialize() {
    doneButton.setOnMouseClicked(
        mouseEvent -> {
          Stage stage = (Stage) anchorPane.getScene().getWindow();
          stage.close();
        });
  }

  @Override
  public void saveWord() {}

  @Override
  public void quitScreen() {}
}
