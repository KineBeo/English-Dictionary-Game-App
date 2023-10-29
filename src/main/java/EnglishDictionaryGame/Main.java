package EnglishDictionaryGame;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
  public Stage mainStage;

  @Override
  public void start(Stage stage) {
    try {
      mainStage = stage;
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/Application.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      scene
          .getStylesheets()
          .add(
              Objects.requireNonNull(Main.class.getResource("css/blueTheme.css")).toExternalForm());
      scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
      mainStage.setResizable(false);
      Image icon =
          new Image(Objects.requireNonNull(Main.class.getResourceAsStream("image/icon.png")));
      mainStage.getIcons().add(icon);
      mainStage.setScene(scene);
      mainStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
      mainStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Stage getMainStage() {
    return this.mainStage;
  }

  public static void main(String[] args) {
    launch();
  }
}
