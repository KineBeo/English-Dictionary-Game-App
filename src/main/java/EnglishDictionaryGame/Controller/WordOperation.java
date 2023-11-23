package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class WordOperation {
  @FXML protected AnchorPane anchorPane;

  @FXML protected TextField inputText;

  @FXML protected TextField antonym;

  @FXML protected TextField definition;

  @FXML protected TextField example;

  @FXML protected TextField pronunciation;

  @FXML protected TextField synonym;

  @FXML protected TextField type;

  protected abstract void initialize();

  public abstract void saveWord();

  protected void setCss() {
    anchorPane
        .getStylesheets()
        .add(
            Objects.requireNonNull(Main.class.getResource("css/wordOperation.css"))
                .toExternalForm());
  }

  protected void showAlert(String message, String title) {
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

  public AnchorPane getAnchorPane() {
    return anchorPane;
  }

  public void setAnchorPane(AnchorPane anchorPane) {
    this.anchorPane = anchorPane;
  }

  public TextField getInputText() {
    return inputText;
  }

  public TextField getAntonym() {
    return antonym;
  }

  public TextField getDefinition() {
    return definition;
  }

  public TextField getExample() {
    return example;
  }

  public TextField getPronunciation() {
    return pronunciation;
  }

  public TextField getSynonym() {
    return synonym;
  }

  public TextField getType() {
    return type;
  }

}
