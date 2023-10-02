package EnglishDictionaryGame;

import EnglishDictionaryGame.CommandLine.DictionaryCommandLine;
import java.io.IOException;

import EnglishDictionaryGame.Server.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
    stage.setTitle("Dictionary!");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) throws Exception {
    Database database = new Database();
    database.connectToDatabase();
    launch();
//    DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
//    dictionaryCommandLine.dictionaryBasic();
  }
}

