package EnglishDictionaryGame.Data;

import EnglishDictionaryGame.Exceptions.WordExceptions.*;
import EnglishDictionaryGame.Exceptions.Utils;

public class Word {

  private String wordTarget = "";
  private String wordExplain = "";

  private void checkIfWordTargetIsValid(String wordTarget)
      throws WordTargetIsEmptyException, WordTargetContainsNewlineException {
    if (wordTarget.isEmpty()) {
      throw new WordTargetIsEmptyException("EXCEPTION: Word target cannot be empty");
    } else if (wordTarget.contains("\n")) {
      throw new WordTargetContainsNewlineException("EXCEPTION: Word target cannot contain newline");
    }
  }

  private void checkIfWordExplainIsValid(String wordExplain)
      throws WordExplainIsEmptyException, WordExplainContainsNewlineException {
    if (wordExplain.isEmpty()) {
      throw new WordExplainIsEmptyException("EXCEPTION: Word explain cannot be empty");
    } else if (wordExplain.contains("\n")) {
      throw new WordExplainContainsNewlineException("EXCEPTION: Word explain cannot contain newline");
    }
  }

  public Word() {
  }

  public Word(String wordTarget, String wordExplain) {
    this.setWordTarget(wordTarget);
    this.setWordExplain(wordExplain);
  }

  public String getWordTarget() {
    try {
      checkIfWordTargetIsValid(this.wordTarget);
    } catch (WordTargetIsEmptyException | WordTargetContainsNewlineException wte) {
      Utils.printRelevantStackTrace(wte);
    }

    return wordTarget;
  }

  public void setWordTarget(String wordTarget) {
    try {
      checkIfWordTargetIsValid(wordTarget);
    } catch (WordTargetIsEmptyException | WordTargetContainsNewlineException wte) {
      Utils.printRelevantStackTrace(wte);
    }

    this.wordTarget = wordTarget;
  }

  public String getWordExplain() {
    try {
      checkIfWordExplainIsValid(this.wordExplain);
    } catch (WordExplainIsEmptyException | WordExplainContainsNewlineException wee) {
      Utils.printRelevantStackTrace(wee);
    }

    return wordExplain;
  }

  public void setWordExplain(String wordExplain) {
    try {
      checkIfWordExplainIsValid(wordExplain);
    } catch (WordExplainIsEmptyException | WordExplainContainsNewlineException wee) {
      Utils.printRelevantStackTrace(wee);
    }

    this.wordExplain = wordExplain;
  }
}
