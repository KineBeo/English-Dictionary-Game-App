package EnglishDictionaryGame.CommandLine;

import EnglishDictionaryGame.Data.Dictionary;

public class DictionaryCommandLine {

  public DictionaryCommandLine() {
  }

  public void showAllWords(Dictionary dictionary) {
    if (dictionary.getSize() == 0) {
      System.out.println("The dictionary is empty.");
      return;
    }

    /* The words in the dictionary are printed in the following format:
      No | English | Vietnamese
      1  | Hello | Xin chao
      2  | House | Ngoi nha
      3  | Love | Yeu thuong
     */

    System.out.println("No | English | Vietnamese");
    for (int wordIndex = 0; wordIndex < dictionary.getSize(); wordIndex++) {
      System.out.println(
          (wordIndex + 1) + "  | " + dictionary.getWord(wordIndex).getWordTarget() + " | "
              + dictionary.getWord(wordIndex).getWordExplain());
    }
  }

  public void dictionaryBasic() {
    Dictionary dictionary = new Dictionary();
    DictionaryManagement dictionaryManagement = new DictionaryManagement();

    dictionaryManagement.insertFromCommandLine(dictionary);
    showAllWords(dictionary);
  }
}
