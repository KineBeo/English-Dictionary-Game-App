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

  @FXML
  private void initialize() {
    // Dòng code dùng để hiển thị nội dung của từ cần chỉnh sửa
    htmlEditor.setHtmlText(database.lookUpWord(Application.editTarget));
  }
  public static void setTarget(String target) {
    UpdateWord.editingWord = target;
  }

  @Override
  public void saveWord() {
    String definition = htmlEditor.getHtmlText();
    Database database = new Database();
    if (database.updateWordDefinition(Application.editTarget, definition)) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Thông báo");
      alert.setContentText("Cập nhật từ `" + Application.editTarget + "` thành công!");
      alert.show();
    } else {
      if (!database.isWordExist(Application.editTarget)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setContentText("Từ `" + Application.editTarget + "` không tồn tại!");
        alert.show();
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setContentText("Cập nhật từ `" + Application.editTarget + "` không thành công!");
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
