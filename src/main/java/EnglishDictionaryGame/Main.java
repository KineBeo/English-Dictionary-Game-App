package EnglishDictionaryGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  public Stage mainStage;
  @Override
  public void start(Stage stage) {
    try {
      mainStage = stage;
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/Application.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      mainStage.setTitle("Dictionary!");
      mainStage.setResizable(false);
      mainStage.setScene(scene);
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
