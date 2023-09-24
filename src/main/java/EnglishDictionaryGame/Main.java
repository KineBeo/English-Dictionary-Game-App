package EnglishDictionaryGame;

import EnglishDictionaryGame.CommandLine.DictionaryCommandLine;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 810, 500);
    stage.setTitle("Dictionary!");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    // launch();
    DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
    dictionaryCommandLine.dictionaryBasic();
  }
}

