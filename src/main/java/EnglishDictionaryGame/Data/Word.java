package EnglishDictionaryGame.Data;

import EnglishDictionaryGame.Exceptions.WordExceptions.WordTargetIsEmptyException;
import EnglishDictionaryGame.Exceptions.WordExceptions.WordExplainIsEmptyException;
import EnglishDictionaryGame.Exceptions.Utils;
public class Word {

  private String wordTarget = "";
  private String wordExplain = "";

  private void checkIfWordTargetIsEmpty(String wordTarget) throws WordTargetIsEmptyException {
    if (wordTarget.isEmpty()) {
      throw new WordTargetIsEmptyException("Word target cannot be empty");
    }
  }

  private void checkIfWordExplainIsEmpty(String wordExplain) throws WordExplainIsEmptyException {
    if (wordExplain.isEmpty()) {
      throw new WordExplainIsEmptyException("Word explain cannot be empty");
    }
  }
  public Word() {}

  public Word(String wordTarget, String wordExplain) {
    this.setWordTarget(wordTarget);
    this.setWordExplain(wordExplain);
  }

  public String getWordTarget() {
    try {
      checkIfWordTargetIsEmpty(this.wordTarget);
    } catch (WordTargetIsEmptyException wtiee) {
      Utils.printRelevantStackTrace(wtiee);
    }

    return wordTarget;
  }

  public void setWordTarget(String wordTarget) {
    try {
      checkIfWordTargetIsEmpty(wordTarget);
    } catch (WordTargetIsEmptyException wtiee) {
      Utils.printRelevantStackTrace(wtiee);
    }

    this.wordTarget = wordTarget;
  }

  public String getWordExplain() {
    try {
      checkIfWordExplainIsEmpty(this.wordExplain);
    } catch (WordExplainIsEmptyException weiee) {
      Utils.printRelevantStackTrace(weiee);
    }

    return wordExplain;
  }

  public void setWordExplain(String wordExplain) {
    try {
      checkIfWordExplainIsEmpty(wordExplain);
    } catch (WordExplainIsEmptyException weiee) {
      Utils.printRelevantStackTrace(weiee);
    }

    this.wordExplain = wordExplain;
  }
}
