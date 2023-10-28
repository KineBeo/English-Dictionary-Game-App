package EnglishDictionaryGame.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SettingController extends WordOperation {
  @FXML private Label doneButton;
  @FXML private AnchorPane anchorPane;
  @FXML private HBox redTheme;
  @FXML private HBox whiteBlueTheme;
  @FXML private HBox yellowTheme;
  @FXML private HBox blueTheme;

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

  /** Set color theme. */
  public void setBarTheme(HBox temp) {
    redTheme.setOnMouseClicked(
        mouseEvent -> temp.setStyle("-fx-background-color: #df4147"));

    whiteBlueTheme.setOnMouseClicked(
        mouseEvent -> temp.setStyle("-fx-background-color: #30abf3"));

    blueTheme.setOnMouseClicked(
        mouseEvent -> temp.setStyle("-fx-background-color: #0768ad"));

    yellowTheme.setOnMouseClicked(
        mouseEvent -> temp.setStyle("-fx-background-color: #f79410"));
  }
}
