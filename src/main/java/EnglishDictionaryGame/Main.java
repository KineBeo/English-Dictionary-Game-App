package EnglishDictionaryGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage stage) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/Application.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setTitle("Dictionary!");
      stage.setResizable(false);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
