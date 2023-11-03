package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class testController {
  @FXML private BorderPane borderPane;

  @FXML private Button settingButton;

  @FXML
  private void initialize() {
    settingButton.setOnMouseClicked(
        event -> {
          try {
            AnchorPane view = FXMLLoader.load(Main.class.getResource("fxml/Setting.fxml"));
            borderPane.setCenter(view);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }
}
