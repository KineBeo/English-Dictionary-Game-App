package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import java.util.ArrayList;
import java.util.Objects;
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
  public void setBarTheme(HBox temp, ArrayList<Label> buttons) {
    redTheme.setOnMouseClicked(
        mouseEvent -> {
          temp.setStyle("-fx-background-color: #DA000E");
          Application.definitionColor = "#DA000E";
          buttons.forEach(
              button -> {
                button.getStylesheets().clear();
                button
                    .getStylesheets()
                    .add(
                        Objects.requireNonNull(Main.class.getResource("css/redTheme.css"))
                            .toExternalForm());
              });
        });

    whiteBlueTheme.setOnMouseClicked(
        mouseEvent -> {
          temp.setStyle("-fx-background-color: #30abf3");
          Application.definitionColor = "#30abf3";
          buttons.forEach(
              button -> {
                button.getStylesheets().clear();
                button
                    .getStylesheets()
                    .add(
                        Objects.requireNonNull(Main.class.getResource("css/whiteBlueTheme.css"))
                            .toExternalForm());
              });
        });

    blueTheme.setOnMouseClicked(
        mouseEvent -> {
          temp.setStyle("-fx-background-color: #0768ad");
          Application.definitionColor = "#0768ad";
          buttons.forEach(
              button -> {
                button.getStylesheets().clear();
                button
                    .getStylesheets()
                    .add(
                        Objects.requireNonNull(Main.class.getResource("css/blueTheme.css"))
                            .toExternalForm());
              });
        });

    yellowTheme.setOnMouseClicked(
        mouseEvent -> {
          temp.setStyle("-fx-background-color: #f79410");
          Application.definitionColor = "#f79410";
          buttons.forEach(
              button -> {
                button.getStylesheets().clear();
                button
                    .getStylesheets()
                    .add(
                        Objects.requireNonNull(Main.class.getResource("css/yellowTheme.css"))
                            .toExternalForm());
              });
        });
  }
}
