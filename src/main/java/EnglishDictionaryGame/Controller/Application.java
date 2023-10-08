package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Database;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

public class Application implements Initializable {

  @FXML private TextField inputText;

  @FXML private ListView<String> searchList;

  @FXML private WebView webView;

  @FXML private Button addButton;

  @FXML private Button deleteButton;
  private int lastIndex = 0;

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
    if (definition.equals("Not found!")) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("CẢNH BÁO");
      alert.setContentText("KHÔNG TÌM THẤY TỪ '" + target + "!");
      alert.show();
    }
    webView.getEngine().loadContent(definition, "text/html");
  }

  @FXML
  public void addingWord(ActionEvent event) {
    System.out.println("click click!!");
  }

  @FXML
  public void deleteWord(ActionEvent event) {
    System.out.println("Delete delete!!");
  }

  @FXML
  public void doubleClicktoSelectWord(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
        String target = searchList.getSelectionModel().getSelectedItem();
        inputText.setText(target);
        findWord();
    }
  }

  @FXML
  public void selectWordUsingKeyBoard(KeyEvent e) {
    if (searchList.getSelectionModel().getSelectedIndices().isEmpty()) {
      return;
    }
    if (e.getCode() == KeyCode.ENTER) {
      String target = searchList.getSelectionModel().getSelectedItem();
        inputText.setText(target);
      findWord();
    } else if (e.getCode() == KeyCode.UP) {
      if (searchList.getSelectionModel().getSelectedIndex() == 0 && lastIndex == 0) {
        inputText.requestFocus();
      }
    }
    lastIndex = searchList.getSelectionModel().getSelectedIndex();
  }
}
