package EnglishDictionaryGame.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FlashcardFileManager {

  private static final String FLASHCARD_DATABASE_FILE_PATH = "src/main/resources/EnglishDictionaryGame/database/flashcard_database.txt";

  public static FlashcardDatabase getDataFromFile() {
    ArrayList<Flashcard> flashcards = new ArrayList<Flashcard>();
    try {
      flashcards = readFlashcardDatabase(FLASHCARD_DATABASE_FILE_PATH);
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
      System.out.println("Failed to read flashcard database file");
    }

    FlashcardDatabase flashcardDatabase = new FlashcardDatabase();
    for (Flashcard flashcard : flashcards) {
      if (flashcard == null) {
        System.out.println("Flashcard is null");
      }
      flashcardDatabase.addFlashcard(flashcard);
    }

    System.out.println("File data read.");
    return flashcardDatabase;
  }

  public static void saveDataToFile(FlashcardDatabase flashcardDatabase) {
    try {
      writeFlashcardDatabase(flashcardDatabase);
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
      System.out.println("Failed to write flashcard database file");
    }
  }

  private static ArrayList<Flashcard> readFlashcardDatabase(String flashcardDatabaseFilePath)
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
      System.out.println(readFlashcard.getFrontText());
    }

    reader.close();
    return result;
  }

  private static void writeFlashcardDatabase(FlashcardDatabase flashcardDatabase)
      throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FLASHCARD_DATABASE_FILE_PATH));
    StringBuilder data = new StringBuilder();

    // Append the file's header.
    data.append("Word|Definition\n");

    // Append the flashcard contents.
    for (int i = 0; i < flashcardDatabase.size(); i++) {
      Flashcard flashcard = flashcardDatabase.getFlashcard(i);
      data.append(flashcard.getFrontText()).append("|").append(flashcard.getBackText())
          .append("\n");
    }

    writer.write(data.toString());
    writer.close();
  }
}
