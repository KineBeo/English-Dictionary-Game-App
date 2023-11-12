package EnglishDictionaryGame.Server;

import java.util.ArrayList;

public class FlashcardDataManager {

  private static FlashcardDatabase editingFlashcardDatabase = FlashcardDataFileManager.getDataFromFile();
  private static FlashcardDatabase flashcardDatabase = FlashcardDataFileManager.getDataFromFile();

  public static FlashcardDatabase getFlashcardDatabase() {
    return flashcardDatabase;
  }
}
