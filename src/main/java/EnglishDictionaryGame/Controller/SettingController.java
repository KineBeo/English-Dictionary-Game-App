package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import java.util.ArrayList;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SettingController {
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

  /** Set color theme. */
  public void setBarTheme(HBox temp, ArrayList<Label> buttons, ListView<String> listView) {
    changeColor(redTheme, "css/RedTheme.css", "#DA000E", temp, buttons, listView, "#B60000");
    changeColor(whiteBlueTheme, "css/WhiteBlueTheme.css", "#30abf3", temp, buttons, listView, "#0088CD");
    changeColor(blueTheme, "css/BlueTheme.css", "#0768ad", temp, buttons, listView, "#0077ff");
    changeColor(yellowTheme, "css/YellowTheme.css", "#f79410", temp, buttons, listView, "#CB7000");
  }

  public void changeColor(
      HBox theme,
      String fxmlPath,
      String backgroundColorCode,
      HBox temp,
      ArrayList<Label> buttons,
      ListView<String> listView,
      String color) {
    theme.setOnMouseClicked(
        mouseEvent -> {
          temp.setStyle("-fx-background-color: " + backgroundColorCode);
          Application.definitionColor = backgroundColorCode;
            Application.wordColor = color;
          buttons.forEach(
              button -> {
                button.getStylesheets().clear();
                button
                    .getStylesheets()
                    .add(Objects.requireNonNull(Main.class.getResource(fxmlPath)).toExternalForm());
              });
          listView.getStylesheets().clear();
          listView
              .getStylesheets()
              .add(Objects.requireNonNull(Main.class.getResource(fxmlPath)).toExternalForm());

        });
  }
}
