package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Database;
import EnglishDictionaryGame.Server.WordInfo;
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

public class UpdateWord extends WordOperation {

  @FXML private AnchorPane anchorPane;

  @FXML private HTMLEditor htmlEditor;

  @FXML private TextField antonym;

  @FXML private TextField definition;

  @FXML private TextField example;

  @FXML private TextField inputText;

  @FXML private TextField pronunciation;

  @FXML private TextField synonym;

  @FXML private TextField type;

  @FXML
  private void initialize() {

    //    htmlEditor.setHtmlText(database.lookUpWord(Application.editTarget));
    inputText.setText(Application.editTarget);
    WordInfo wordInfo = Application.getDatabase().findWord(Application.editTarget);
    if (wordInfo != null) {
      type.setText(wordInfo.getType());
      definition.setText(wordInfo.getMeaning());
      pronunciation.setText(wordInfo.getPronunciation());
      example.setText(wordInfo.getExample());
      synonym.setText(wordInfo.getSynonym());
      antonym.setText(wordInfo.getAntonyms());
    }
  }

  @Override
  public void saveWord() {
    Database database = new Database();
    if (database.updateWordDefinition(
        inputText.getText(),
        type.getText(),
        definition.getText(),
        pronunciation.getText(),
        example.getText(),
        synonym.getText(),
        antonym.getText())) {
      Application.editTarget = null;
      showAlert("Successfully updated word!", "Notification");
    }
  }

  public void showAlert(String message, String title) {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Alert.fxml"));
      Parent root = loader.load();
      AlertController alertController = loader.getController();
      alertController.setMessage(message);
      alertController.setTitle(title);
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
  }

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
  }
}
