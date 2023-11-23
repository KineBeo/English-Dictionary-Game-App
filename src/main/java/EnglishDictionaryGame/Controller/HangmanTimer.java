package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HangmanTimer extends GameTimer {
  public static void handleTotalSecondRunToZero(Label quizTimer) {
    if (totalSecond < 0) {
      currentTimer.cancel();
      quizTimer.setText("00:00:00");
      showAlert("Time out! You lose bro!", "GAME OVER");
    }
  }

  public static void showAlert(String message, String title) {
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
