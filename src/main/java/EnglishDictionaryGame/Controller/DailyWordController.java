package EnglishDictionaryGame.Controller;

import static EnglishDictionaryGame.Controller.Application.allWords;
import static EnglishDictionaryGame.Main.database;

import EnglishDictionaryGame.Server.PronunciationService;
import EnglishDictionaryGame.Server.WordInfo;
import java.io.*;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class DailyWordController {

  @FXML private AnchorPane anchorPane;
  @FXML private WebView webView;
  @FXML private Label randomWord;
  @FXML private ImageView speaker;

  private static final LocalDate currentDate = LocalDate.now();
  private static LocalDate previousDate;
  private static WordInfo currentRandomWord;

  @FXML
  public void initialize() {
    savePreviousDateAndWordToFile();
  }

  private void loadNewWord() {
    int random = (int) (Math.random() * 100000);
    currentRandomWord = allWords.get(random);
  }

  private void savePreviousDateAndWordToFile() {
    try {
      File file =
          new File(
              "D:\\English-Dictionary-Game-App\\src\\main\\resources\\EnglishDictionaryGame\\database\\wordOfTheDay.txt");
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;

      StringBuilder content = new StringBuilder();
      StringBuilder todayWordStringInFile = new StringBuilder();
      StringBuilder todayWord = new StringBuilder();

      LocalDate previousDateFromFile = null;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("Previous day: ")) {
          previousDateFromFile = LocalDate.parse(line.substring(14));
        }

        if (line.startsWith("Today Word: ")) {
          todayWordStringInFile.append(line);
          todayWord.append(todayWordStringInFile.substring(12));
        }
      }

      System.out.println("Today word: " + todayWord);
      System.out.println(
          "Previous day: "
              + previousDateFromFile
              + "Check: "
              + currentDate.equals(previousDateFromFile));

      if (currentDate.equals(previousDateFromFile)) {
        randomWord.setText(todayWord.toString());
        loadWebView(database.findWord(todayWord.toString()));
        content
            .append("Previous day: ")
            .append(currentDate)
            .append("\n")
            .append("Today Word: ")
            .append(todayWord);
      } else if (!currentDate.equals(previousDateFromFile)) {
        loadNewWord();
        randomWord.setText(currentRandomWord.getWord());
        loadWebView(currentRandomWord);
        todayWordStringInFile.delete(0, todayWordStringInFile.length());
        todayWordStringInFile.append("Today Word: ").append(currentRandomWord.getWord());
        content
            .append("Previous day: ")
            .append(currentDate)
            .append("\n")
            .append(todayWordStringInFile);
      }

      reader.close();

      // Ghi lại toàn bộ nội dung vào file
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      writer.write(content.toString());
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void pronounceWord(String word) {
    speaker.setOnMouseClicked(
        mouseEvent -> new Thread(() -> PronunciationService.pronounce(word, "en")).start());
  }

  private void loadWebView(WordInfo inputWord) {
    String word = inputWord.getWord();
    String type = !inputWord.getType().equals("") ? inputWord.getType() : "Not found";
    String meaning =
        !inputWord.getMeaning().equals("") ? inputWord.getMeaning() : "No meaning found";
    String pronunciation =
        !inputWord.getPronunciation().equals("")
            ? inputWord.getPronunciation()
            : "Not pronunciation found";
    String example =
        !inputWord.getExample().equals("") ? inputWord.getExample() : "No example found";
    String synonym =
        !inputWord.getSynonym().equals("") ? inputWord.getSynonym() : "No synonym found";
    String antonyms =
        !inputWord.getAntonyms().equals("") ? inputWord.getAntonyms() : "No antonyms found";

    String htmlContent =
        "<html><body bgcolor='white' style='color:; font-weight: bold; font-size: 18px;'>"
            + "<p><b>Word: </b>"
            + word
            + "</p>"
            + "<p><i>Type: </i>"
            + type
            + "</p>"
            + "<p><b>Definition: </b>"
            + meaning
            + "</p>"
            + "<p><b>Pronunciation: </b>"
            + pronunciation
            + "</p>"
            + "<p><b>Example: </b>"
            + example
            + "</p>"
            + "<p><b>Synonym: </b>"
            + synonym
            + "</p>"
            + "<p><b>Antonyms: </b>"
            + antonyms
            + "</p>"
            + "</body></html>";
    webView.getEngine().loadContent(htmlContent, "text/html");
  }
}
