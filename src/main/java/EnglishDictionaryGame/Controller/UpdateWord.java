package EnglishDictionaryGame.Controller;

import static EnglishDictionaryGame.Main.database;

import EnglishDictionaryGame.Server.WordInfo;
import javafx.fxml.FXML;

public class UpdateWord extends WordOperation {

  @FXML
  @Override
  protected void initialize() {
    setCss();
    inputText.setText(Application.editTarget);
    WordInfo wordInfo = database.findWord(Application.editTarget);
    if (wordInfo != null) {
      type.setText(wordInfo.getType());
      definition.setText(wordInfo.getMeaning());
      pronunciation.setText(wordInfo.getPronunciation());
      example.setText(wordInfo.getExample());
      synonym.setText(wordInfo.getSynonym());
      antonym.setText(wordInfo.getAntonyms());
    }
  }

  @Override
  public void saveWord() {
    WordInfo wordInfo =
        new WordInfo(
            inputText.getText(),
            type.getText(),
            definition.getText(),
            pronunciation.getText(),
            example.getText(),
            synonym.getText(),
            antonym.getText());
    if (database.updateWordDefinition(wordInfo)) {
      Application.editTarget = null;
      showAlert("Successfully updated word!", "Notification");
    }
  }
}
