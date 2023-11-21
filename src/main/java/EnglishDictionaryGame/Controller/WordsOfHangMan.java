package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Server.Trie;
import EnglishDictionaryGame.Server.WordInfo;

import java.util.ArrayList;
import java.util.Random;

import static EnglishDictionaryGame.Main.database;

public class WordsOfHangMan {
  private final ArrayList<WordInfo> words;

  public WordsOfHangMan() {
    words = new ArrayList<>();
    ArrayList<WordInfo> wordsFromDatabase = database.getAllWordTargets();
    words.addAll(wordsFromDatabase);
    System.out.println(words.size());
  }

  public String getRandomWord() {
    int random = (int) (Math.random() * words.size());
    return words.get(random).getWord().toUpperCase();
  }
}
