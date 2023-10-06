package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Database;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class Application implements Initializable {

  @FXML private TextField inputText;

  @FXML private ListView<String> searchList;

  @FXML private WebView webView;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Database database = new Database();
    try {
      database.connectToDatabase();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ArrayList<String> words = database.getAllWordsFromDatabase();

    ObservableList<String> items = FXCollections.observableArrayList(words);
    inputText
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              ArrayList<String> filteredWords = new ArrayList<>();
              for (String word : words) {
                if (word.startsWith(newValue)) {
                  filteredWords.add(word);
                }
              }
              ObservableList<String> filteredItems =
                  FXCollections.observableArrayList(filteredWords);
              searchList.setItems(filteredItems);
            });
    searchList.setItems(items);
  }

  @FXML
  public void findWord() {
    String target = inputText.getText();
    Database database = new Database();
    String definition = database.lookUpWord(target);
    webView.getEngine().loadContent(definition, "text/html");
  }
}
