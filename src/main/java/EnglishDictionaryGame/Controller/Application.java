package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Application implements Initializable {

    @FXML
    private TextField inputText;

    @FXML
    private ListView<String> searchList;

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database database = new Database();
        try {
            database.connectToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<String> words = database.getAllWordFromDatabase();

        ObservableList<String> items = FXCollections.observableArrayList(words);
        searchList.setItems(items);
    }

}
