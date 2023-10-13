package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeleteWord extends WordOperation {

  @FXML private TextField inputText;
  @FXML private AnchorPane anchorPane;

  @Override
  public void saveWord() {
    String target = inputText.getText();
    Database database = new Database();
    if (database.deleteWord(target)) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Thông báo");
      alert.setContentText("Xóa từ `" + target + "` thành công!");
      alert.show();
    } else {
      if (!database.isWordExist(target)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setContentText("Từ `" + target + "` không tồn tại!");
        alert.show();
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setContentText("Xóa từ `" + target + "` không thành công!");
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
