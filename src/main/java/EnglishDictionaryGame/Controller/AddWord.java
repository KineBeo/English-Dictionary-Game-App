package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.io.IOException;

public class AddWord {
  @FXML private AnchorPane anchorPane;

  @FXML private HTMLEditor htmlEditor;

  @FXML private TextField inputText;

  @FXML
  private void initialize() {}

  public void saveWord(ActionEvent event) {
    String target = inputText.getText();
    String definition = htmlEditor.getHtmlText();
    Database database = new Database();
    if (database.insertWord(target, definition)) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Thông báo");
      alert.setContentText("Thêm từ `" + target + "` thành công!");
      alert.show();
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setContentText("Thêm từ `" + target + "` không thành công!");
      alert.show();
    }
  }

  public void quitAddWordScreen(ActionEvent event) {
  }
}
