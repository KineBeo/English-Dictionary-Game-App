package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Exceptions.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FlashcardDatabase {

  private final String FLASHCARD_DATABASE_FILE_PATH = "src/main/resources/EnglishDictionaryGame/database/flashcard_database.txt";
  private ArrayList<Flashcard> flashcards;

  public FlashcardDatabase() {
    try {
      flashcards = readFlashcardDatabase(FLASHCARD_DATABASE_FILE_PATH);
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
      System.out.println("Failed to read flashcard database file");
    }
  }

  public void addFlashcard(Flashcard flashcard) {
    flashcards.add(flashcard);
  }

  public Flashcard getFlashcard(int index) {
    return flashcards.get(index);
  }

  public int getIndexOf(Flashcard flashcard) {
    if (!flashcards.contains(flashcard)) {
      return -1;
    }
    return flashcards.indexOf(flashcard);
  }

  public int size() {
    return flashcards.size();
  }

  public void close() {
    try {
      writeFlashcardDatabase(FLASHCARD_DATABASE_FILE_PATH);
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
      System.out.println("Failed to write flashcard database file");
    }
  }

  private ArrayList<Flashcard> readFlashcardDatabase(String flashcardDatabaseFilePath)
      throws IOException {
    ArrayList<Flashcard> result = new ArrayList<Flashcard>();

    BufferedReader reader = new BufferedReader(new FileReader(flashcardDatabaseFilePath));
    String line;
    boolean headerLineSkipped = false;

    while ((line = reader.readLine()) != null) {
      if (!headerLineSkipped) {
        headerLineSkipped = true;
        continue;
      }

      String[] flashcardData = line.split("\\|");

      String flashcardWord = flashcardData[0];
      String flashcardDefinition = flashcardData[1];

      Flashcard readFlashcard = new Flashcard(flashcardWord, flashcardDefinition);
      result.add(readFlashcard);
    }

    return result;
  }

  private void writeFlashcardDatabase(String flashcardDatabaseFilePath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(flashcardDatabaseFilePath));
    // Write the header.
    writer.write("Word|Definition\n");

    for (Flashcard flashcard : flashcards) {
      String flashcardWord = flashcard.getFrontText();
      String flashcardDefinition = flashcard.getBackText();

      writer.write(flashcardWord + "|" + flashcardDefinition + "\n");
    }
  }
}
