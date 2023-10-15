package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Database;
import EnglishDictionaryGame.Server.Trie;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Application implements Initializable {

  @FXML private TextField inputText;

  @FXML private ListView<String> searchList;

  @FXML private WebView webView;

  @FXML private Button addButton;

  @FXML private Button deleteButton;
  private int lastIndex = 0;

  public static Database database = new Database();

  public Application() {
    try {
      database.initialize();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    preparedSearchList();
    inputText
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              Platform.runLater(
                  () -> {
                    preparedSearchList();
                  });
            });
  }

  /** Refresh the list view. */
  public void preparedSearchList() {
    searchList.getItems().clear();
    String target = inputText.getText().trim();
    ArrayList<String> searchedWords =
        target.isEmpty() ? Trie.getAllWordsFromTrie() : Trie.search(target);
    searchList.setItems(FXCollections.observableArrayList(searchedWords));
  }

  @FXML
  public void findWord() {
    String target = inputText.getText();
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
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/AddWordScreen.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      Stage addStage = new Stage();
      addStage.setTitle("Thêm từ");
      addStage.setScene(scene);
      addStage.setResizable(false);
      addStage.initModality(Modality.APPLICATION_MODAL);
      addStage.initOwner(new Main().getMainStage());
      addStage.showAndWait();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void deleteWord(ActionEvent event) {
    String target = searchList.getSelectionModel().getSelectedItem();
    database.deleteWord(target);
    searchList.getItems().remove(target);
  }

  @FXML
  public void updateWord(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/UpdateWordScreen.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      Stage addStage = new Stage();
      addStage.setTitle("Sửa từ");
      addStage.setScene(scene);
      addStage.setResizable(false);
      addStage.initModality(Modality.APPLICATION_MODAL);
      addStage.initOwner(new Main().getMainStage());
      addStage.showAndWait();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Update update!!");
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
