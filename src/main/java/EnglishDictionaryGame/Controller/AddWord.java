package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Database;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddWord extends WordOperation {
  @FXML private AnchorPane anchorPane;

  @FXML private HTMLEditor htmlEditor;

  @FXML private TextField inputText;

  @FXML
  private void initialize() {}

  public void saveWord() {
    String target = inputText.getText();
    String definition = htmlEditor.getHtmlText();
    Database database = new Database();
    if (database.insertWord(target, definition)) {
      try {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Alert.fxml"));
        Parent root = loader.load();
        AlertController alertController = loader.getController();
        alertController.setMessage("Thêm từ `" + target + "` thành công!");
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Stage addStage = new Stage();
        scene
            .getStylesheets()
            .add(Objects.requireNonNull(Main.class.getResource("css/Alert.css")).toExternalForm());
        addStage.setTitle("Thông báo");
        addStage.setScene(scene);
        addStage.setResizable(false);
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
        addStage.initOwner(new Main().getMainStage());
        addStage.showAndWait();
        addStage.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      if (database.isWordExist(target)) {
        try {
          FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Alert.fxml"));
          Parent root = loader.load();
          AlertController alertController = loader.getController();
          alertController.setMessage("Từ `" + target + "` đã tồn tại!");
          Scene scene = new Scene(root);
          scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
          Stage addStage = new Stage();
          scene
              .getStylesheets()
              .add(
                  Objects.requireNonNull(Main.class.getResource("css/Alert.css")).toExternalForm());
          addStage.setTitle("Error");
          addStage.setScene(scene);
          addStage.setResizable(false);
          addStage.initModality(Modality.APPLICATION_MODAL);
          addStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
          addStage.initOwner(new Main().getMainStage());
          addStage.showAndWait();
          addStage.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        try {
          FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Alert.fxml"));
          Parent root = loader.load();
          AlertController alertController = loader.getController();
          alertController.setMessage("Thêm từ `" + target + "` không thành công!");
          Scene scene = new Scene(root);
          scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
          Stage addStage = new Stage();
          scene
              .getStylesheets()
              .add(
                  Objects.requireNonNull(Main.class.getResource("css/Alert.css")).toExternalForm());
          addStage.setTitle("Error");
          addStage.setScene(scene);
          addStage.setResizable(false);
          addStage.initModality(Modality.APPLICATION_MODAL);
          addStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
          addStage.initOwner(new Main().getMainStage());
          addStage.showAndWait();
          addStage.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
