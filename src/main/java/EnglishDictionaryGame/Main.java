package EnglishDictionaryGame;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
  public Stage mainStage;
  double x, y;

  @Override
  public void start(Stage stage) {
    try {
      mainStage = stage;
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/Application.fxml"));
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root);
      scene
          .getStylesheets()
          .add(
              Objects.requireNonNull(Main.class.getResource("css/blueTheme.css")).toExternalForm());
      scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
      root.setOnMousePressed(
          mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
          });

      root.setOnMouseDragged(
          mouseEvent -> {
            mainStage.setX(mouseEvent.getScreenX() - x);
            mainStage.setY(mouseEvent.getScreenY() - y);
          });
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
