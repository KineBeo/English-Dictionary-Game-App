package EnglishDictionaryGame.CommandLine;

import EnglishDictionaryGame.Data.Dictionary;
import java.util.Scanner;
public class DictionaryManagement {
  private Scanner wordScanner = new Scanner(System.in);
  public void insertFromCommandLine(Dictionary dictionary) {
    int numOfWordInputs = 0;
    System.out.print("Insert number of words: ");
    numOfWordInputs = wordScanner.nextInt();

    String wordTarget = "";
    String wordExplain = "";

    while (wordTarget.isEmpty()) {
      System.out.println("Enter English word: ");
      wordTarget = wordScanner.nextLine();
    }

    while (wordExplain.isEmpty()) {
      System.out.println("Enter Vietnamese definition: ");
      wordExplain = wordScanner.nextLine();
    }

    dictionary.addWord(wordTarget, wordExplain);
  }
}
