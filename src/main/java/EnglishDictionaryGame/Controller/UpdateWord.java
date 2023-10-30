package EnglishDictionaryGame.Controller;

import static EnglishDictionaryGame.Controller.Application.database;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Database;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateWord extends WordOperation {

  @FXML private AnchorPane anchorPane;

  @FXML private HTMLEditor htmlEditor;

  @FXML
  private void initialize() {
    htmlEditor.setHtmlText(database.lookUpWord(Application.editTarget));
  }

  @Override
  public void saveWord() {
    String definition = htmlEditor.getHtmlText();
    Database database = new Database();
    if (database.updateWordDefinition(Application.editTarget, definition)) {
      try {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Alert.fxml"));
        Parent root = loader.load();
        AlertController alertController = loader.getController();
        alertController.setMessage("Cập nhật từ `" + Application.editTarget + "` thành công!");
        alertController.setTitle("Notification");
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Stage addStage = new Stage();
        scene
            .getStylesheets()
            .add(Objects.requireNonNull(Main.class.getResource("css/Alert.css")).toExternalForm());
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
      if (!database.isWordExist(Application.editTarget)) {
        try {
          FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Alert.fxml"));
          Parent root = loader.load();
          AlertController alertController = loader.getController();
          alertController.setMessage("Từ `" + Application.editTarget + "` không tồn tại!");
          alertController.setTitle("Error");
          Scene scene = new Scene(root);
          scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
          Stage addStage = new Stage();
          scene
              .getStylesheets()
              .add(
                  Objects.requireNonNull(Main.class.getResource("css/Alert.css")).toExternalForm());
          addStage.setScene(scene);
          addStage.setResizable(false);
          addStage.initModality(Modality.APPLICATION_MODAL);
          addStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
          addStage.initOwner(new Main().getMainStage());
          addStage.showAndWait();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        try {
          FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Alert.fxml"));
          Parent root = loader.load();
          AlertController alertController = loader.getController();
          alertController.setMessage(
              "Cập nhật từ `" + Application.editTarget + "` không thành công!");
          alertController.setTitle("Error");
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
