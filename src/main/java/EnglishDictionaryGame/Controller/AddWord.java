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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddWord extends WordOperation {
  @FXML private AnchorPane anchorPane;

  @FXML private TextField inputText;

  @FXML private TextField antonym;

  @FXML private TextField definition;

  @FXML private TextField example;

  @FXML private TextField pronunciation;

  @FXML private TextField synonym;

  @FXML private TextField type;

  @FXML
  private void initialize() {}

  public void saveWord() {
    String newWord = inputText.getText();
    String newType = type.getText();
    String newDefinition = definition.getText();
    String newPronunciation = pronunciation.getText();
    String newExample = example.getText();
    String newSynonym = synonym.getText();
    String newAntonym = antonym.getText();
    Database database = new Database();
    if (newWord.isEmpty()
        || newType.isEmpty()
        || newDefinition.isEmpty()
        || newPronunciation.isEmpty()
        || newExample.isEmpty()
        || newSynonym.isEmpty()
        || newAntonym.isEmpty()) {
      showAlert("Please fill in all fields!", "Error");
      return;
    }

    if (database.insertWord(
        newWord, newType, newDefinition, newPronunciation, newExample, newSynonym, newAntonym)) {
      showAlert("Successfully added a new word!", "Notification");
    } else {
      if (database.isWordExist(newWord)) {
        showAlert("Word `" + newWord + "` already exists!", "Error");
      } else {
        showAlert("Adding a new word failed!", "Error");
      }
    }
  }

  @Override
  public void quitScreen() {
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    stage.close();
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
}
