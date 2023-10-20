package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordsOfHangMan {
  private ArrayList<String> words;
  private String[] letters;

  public WordsOfHangMan() throws FileNotFoundException {
    words = new ArrayList<>();
    Scanner sc = new Scanner(new File(Main.class.getResource("database/Words.txt").getFile()));
    while (sc.hasNextLine()) words.add(sc.nextLine());
  }

  public String getRandomWord() {
    return words.get(new Random().nextInt(words.size())).toUpperCase();
  }
}
