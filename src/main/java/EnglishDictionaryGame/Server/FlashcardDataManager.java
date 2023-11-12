package EnglishDictionaryGame.Server;

import java.util.ArrayList;
import java.util.HashMap;

public class FlashcardDataManager {

  private static FlashcardDatabase editingFlashcardDatabase;
  private static FlashcardDatabase flashcardDatabase;
  private static HashMap<Flashcard, Boolean> saveMap;

  public static void initialize() {
    editingFlashcardDatabase = FlashcardDataFileManager.getDataFromFile();
    flashcardDatabase = FlashcardDataFileManager.getDataFromFile();
    saveMap = new HashMap<Flashcard, Boolean>();

    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      saveMap.put(editingFlashcardDatabase.getFlashcard(i), true);
    }
  }

  public static FlashcardDatabase getFlashcardDatabase() {
    return flashcardDatabase;
  }


}
