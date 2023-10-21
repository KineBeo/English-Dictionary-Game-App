package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Trie;
import java.util.ArrayList;
import java.util.Random;

public class WordsOfHangMan {
  private final ArrayList<String> words;

  public WordsOfHangMan() {
    words = new ArrayList<>();
    ArrayList<String> wordsFromDatabase = Trie.getAllWordsFromTrie();
    words.addAll(wordsFromDatabase);
    System.out.println(words.size());
  }

  public String getRandomWord() {
    return words.get(new Random().nextInt(words.size())).toUpperCase();
  }
}
