package EnglishDictionaryGame.Server;

import java.util.HashMap;

public class FlashcardDataManager {

  private static FlashcardDatabase editingFlashcardDatabase;
  private static FlashcardDatabase flashcardDatabase;
  private static HashMap<Flashcard, Boolean> saveMap;

  public static void initialize() {
    editingFlashcardDatabase = FlashcardFileManager.getDataFromFile();
    flashcardDatabase = FlashcardFileManager.getDataFromFile();
    saveMap = new HashMap<Flashcard, Boolean>();

    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      saveMap.put(editingFlashcardDatabase.getFlashcard(i), true);
    }
  }

  public static FlashcardDatabase getFlashcardDatabase() {
    return flashcardDatabase;
  }

  public static Flashcard getEditingFlashcard(int index) {
    return editingFlashcardDatabase.getFlashcard(index);
  }

  public static int getIndexOf(Flashcard flashcard) {
    return editingFlashcardDatabase.getIndexOf(flashcard);
  }

  public static void temporarySave(int index, String frontText, String backText) {
    Flashcard flashcard = editingFlashcardDatabase.getFlashcard(index);
    if (!saveMap.containsKey(flashcard)) {
      System.out.println("Temporary save failed. Flashcard not found in database.");
    }

    flashcard.setFrontText(frontText);
    flashcard.setBackText(backText);
    saveMap.put(flashcard, false);
  }

  public static void hardSave(int index, String frontText, String backText) {
    Flashcard flashcard = editingFlashcardDatabase.getFlashcard(index);
    if (!saveMap.containsKey(flashcard)) {
      System.out.println("Hard save failed. Flashcard not found in database.");
    }

    flashcard.setFrontText(frontText);
    flashcard.setBackText(backText);
    saveMap.put(flashcard, true);
  }

  public static void removeFlashcard(int index) {
    Flashcard flashcard = editingFlashcardDatabase.getFlashcard(index);
    editingFlashcardDatabase.remove(index);
    flashcardDatabase.remove(index);
    saveMap.remove(flashcard);
  }

  public static void addEmptyFlashcard() {
    Flashcard emptyFlashcard = new Flashcard("", "");
    editingFlashcardDatabase.addFlashcard(emptyFlashcard);
    flashcardDatabase.addFlashcard(emptyFlashcard);
  }

  public static int getSize() {
    return editingFlashcardDatabase.size();
  }
  public static void updateDatabase() {
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      Flashcard flashcard = editingFlashcardDatabase.getFlashcard(i);
      boolean flashcardSaved = saveMap.get(flashcard);
      if (flashcardSaved) {
        Flashcard savedFlashcard = flashcardDatabase.getFlashcard(i);
        savedFlashcard.setFrontText(flashcard.getFrontText());
        savedFlashcard.setBackText(flashcard.getBackText());
      }
    }

    FlashcardFileManager.saveDataToFile(flashcardDatabase);

    // Reset the editing database
    editingFlashcardDatabase = FlashcardFileManager.getDataFromFile();
  }
}
