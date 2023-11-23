package EnglishDictionaryGame.Controller;

import static EnglishDictionaryGame.Main.database;

import EnglishDictionaryGame.Server.WordInfo;
import javafx.fxml.FXML;

public class AddWord extends WordOperation {

  @FXML
  @Override
  protected void initialize() {
    setCss();
  }

  public void saveWord() {
    String newWord = formatFirstLetter(this.getInputText().getText());
    String newType = this.getType().getText();
    String newDefinition = this.getDefinition().getText();
    String newPronunciation = this.getPronunciation().getText();
    String newExample = this.getExample().getText();
    String newSynonym = this.getSynonym().getText();
    String newAntonym = this.getAntonym().getText();

    WordInfo wordInfo =
        new WordInfo(
            newWord, newType, newDefinition, newPronunciation, newExample, newSynonym, newAntonym);
    if (newWord.isEmpty()
        || newType.isEmpty()
        || newDefinition.isEmpty()
        || newPronunciation.isEmpty()
        || newExample.isEmpty()
        || newSynonym.isEmpty()
        || newAntonym.isEmpty()) {
      showAlert("Please fill in all fields!", "Error");
      return;
    }

    if (database.insertWord(wordInfo)) {
      showAlert("Successfully added a new word!", "Notification");
    } else {
      if (database.isWordExist(newWord)) {
        showAlert("Word `" + newWord + "` already exists!", "Error");
      } else {
        showAlert("Adding a new word failed!", "Error");
      }
    }
  }

  private String formatFirstLetter(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }

    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }
}
