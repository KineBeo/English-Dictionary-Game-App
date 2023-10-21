package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import EnglishDictionaryGame.Server.Database;
import EnglishDictionaryGame.Server.Trie;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Application implements Initializable {

  @FXML private TextField inputText;

  @FXML private ListView<String> searchList;

  @FXML private WebView webView;

  @FXML private Label menu;

  @FXML private Label menuClose;

  @FXML private AnchorPane slider;

  @FXML private Label addButton;

  @FXML private Label deleteButton;

  @FXML private Label editButton;

  @FXML private Label hangmanButton;

  @FXML private Label quizButton;

  @FXML private Label translateButton;

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
    menuSlider();
    addingWord();
    deleteWord();
    updateWord();
    hangMan();
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
    definition =
        "<html><body bgcolor='white' style='color:#34586F; font-weight: bold; font-size: 20px;'>"
            + definition
            + "</body></html>";

    if (definition.equals("Not found!")) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("CẢNH BÁO");
      alert.setContentText("KHÔNG TÌM THẤY TỪ '" + target + "!");
      alert.show();
    }
    webView.getEngine().loadContent(definition, "text/html");
  }

  @FXML
  public void addingWord() {
    addButton.setOnMouseClicked(
        mouseEvent -> {
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
        });
  }

  @FXML
  public void deleteWord() {
    deleteButton.setOnMouseClicked(
        mouseEvent -> {
          String target = searchList.getSelectionModel().getSelectedItem();
          database.deleteWord(target);
          searchList.getItems().remove(target);
        });
  }

  @FXML
  public void updateWord() {
    editButton.setOnMouseClicked(
        mouseEvent -> {
          String target = searchList.getSelectionModel().getSelectedItem();
            UpdateWord.setTarget(target);
          try {
            FXMLLoader loader =
                new FXMLLoader(Main.class.getResource("fxml/UpdateWordScreen.fxml"));
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
        });
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

  public void menuSlider() {
    slider.setTranslateX(-182);
    menu.setOnMouseClicked(
        mouseEvent -> {
          System.out.println("clicked Menu");
          TranslateTransition slide = new TranslateTransition();
          slide.setDuration(Duration.seconds(0.35));
          slide.setNode(slider);

          slide.setToX(0);
          slide.play();

          slider.setTranslateX(-182);

          slide.setOnFinished(
              (ActionEvent e) -> {
                menu.setVisible(false);
                menuClose.setVisible(true);
              });
        });

    menuClose.setOnMouseClicked(
        mouseEvent -> {
          System.out.println("Clicked Menu Close");
          TranslateTransition slide = new TranslateTransition();
          slide.setDuration(Duration.seconds(0.35));
          slide.setNode(slider);

          slide.setToX(-182);
          slide.play();

          slider.setTranslateX(0);

          slide.setOnFinished(
              (ActionEvent e) -> {
                menu.setVisible(true);
                menuClose.setVisible(false);
              });
        });
  }

  public void hangMan() {
    hangmanButton.setOnMouseClicked(
        mouseEvent -> {
          try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/Hangman.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage addStage = new Stage();
            addStage.setTitle("Hangman");
            addStage.setScene(scene);
            addStage.setResizable(false);
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.initOwner(new Main().getMainStage());
            addStage.showAndWait();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }
}
