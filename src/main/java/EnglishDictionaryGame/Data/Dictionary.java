package EnglishDictionaryGame.Data;
import java.util.ArrayList;

public class Dictionary {
  private ArrayList<Word> words = new ArrayList<Word>();

  public void addWord(Word word) {
    words.add(word);
  }

  public void addWord(String wordTarget, String wordExplain) {
    Word word = new Word(wordTarget, wordExplain);
    words.add(word);
  }
  public Word getWord(int index) {
    return words.get(index);
  }
}
