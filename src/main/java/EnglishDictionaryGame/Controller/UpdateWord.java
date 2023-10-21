package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import static EnglishDictionaryGame.Controller.Application.database;

public class UpdateWord extends WordOperation {

  @FXML private AnchorPane anchorPane;

  @FXML private HTMLEditor htmlEditor;

  private static String editingWord;

  private void initialize() {
    htmlEditor.setHtmlText(database.lookUpWord(editingWord));
  }
  public static void setTarget(String target) {
    UpdateWord.editingWord = target;
  }

  @Override
  public void saveWord() {
    String definition = htmlEditor.getHtmlText();
    Database database = new Database();
    if (database.updateWordDefinition(editingWord, definition)) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Thông báo");
      alert.setContentText("Cập nhật từ `" + editingWord + "` thành công!");
      alert.show();
    } else {
      if (!database.isWordExist(editingWord)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setContentText("Từ `" + editingWord + "` không tồn tại!");
        alert.show();
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setContentText("Cập nhật từ `" + editingWord + "` không thành công!");
        alert.show();
      }
    }
  }



  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
