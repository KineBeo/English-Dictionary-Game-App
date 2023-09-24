package EnglishDictionaryGame.CommandLine;

import EnglishDictionaryGame.Data.Dictionary;
import EnglishDictionaryGame.Data.Word;
import java.util.Scanner;

public class DictionaryManagement {

  public void insertFromCommandLine(Dictionary dictionary) {
    Scanner scanner = new Scanner(System.in);
    int totalNewWords = 0;

    String totalNewWordsInput = "";
    while (true) {
      System.out.print("Enter the total number of new words: ");
      totalNewWordsInput = scanner.nextLine();
      try {
        totalNewWords = Integer.parseInt(totalNewWordsInput);
        break; // Exit the loop if input is valid
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }

    if (totalNewWords == 0) {
      return;
    }

    System.out.println("Enter the words and their definitions: ");
    String wordTargetInput = "";
    String wordExplainInput = "";
    for (int newWordCount = 0; newWordCount < totalNewWords; newWordCount++) {
      do {
        System.out.print("Word " + (newWordCount + 1) + ": ");
        wordTargetInput = scanner.nextLine();
      } while (wordTargetInput.isEmpty());

      do {
        System.out.print("Definition: ");
        wordExplainInput = scanner.nextLine();
      } while (wordExplainInput.isEmpty());

      dictionary.addWord(new Word(wordTargetInput, wordExplainInput));
    }

    dictionary.sortAlphabetically();
    scanner.close();
    System.out.println("Successfully added " + totalNewWords + " new words to the dictionary.");
  }
}