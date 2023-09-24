package EnglishDictionaryGame.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.lang3.StringUtils;

public class Dictionary {
  private ArrayList<Word> words = new ArrayList<Word>();

  private static final Comparator<Word> wordComparator = new Comparator<Word>() {
    @Override
    public int compare(Word word1, Word word2) {
      return word1.getWordTarget().compareTo(word2.getWordTarget());
    }
  };

  public Word getWord(int index) {
    return words.get(index);
  }

  public void addWord(Word word) {
    word.setWordTarget(StringUtils.capitalize(word.getWordTarget()));
    words.add(word);
  }

  public void removeWord(int index) {
    words.remove(index);
  }

  public int getSize() {
    return words.size();
  }

  public void sortAlphabetically() {
    Collections.sort(words, wordComparator);
  }
}
