package EnglishDictionaryGame.Controller;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InformationController {

  @FXML private Hyperlink link;
  @FXML private TextArea text;
  @FXML private AnchorPane anchorPane;

  @FXML
  private void initialize() {
    link.setOnAction(
        event -> {
          try {
            openLink();
          } catch (URISyntaxException e) {
            throw new RuntimeException(e);
          }
        });
    text.setEditable(false);
  }

  @FXML
  private static void openLink() throws URISyntaxException {
    try {
      Desktop.getDesktop()
          .browse(new URI("https://github.com/KineBeo/English-Dictionary-Game-App"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void exit() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
