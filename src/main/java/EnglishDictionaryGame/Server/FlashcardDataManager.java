package EnglishDictionaryGame.Server;

import java.util.ArrayList;
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
    Flashcard editingEmptyFlashcard = new Flashcard("", "");
    Flashcard mainEmptyFlashcard = new Flashcard("", "");
    editingFlashcardDatabase.addFlashcard(editingEmptyFlashcard);
    flashcardDatabase.addFlashcard(mainEmptyFlashcard);
    saveMap.put(editingEmptyFlashcard, false);
  }

  public static int getSize() {
    return editingFlashcardDatabase.size();
  }

  public static void updateDatabase() {
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      Flashcard editingFlashcard = editingFlashcardDatabase.getFlashcard(i);
      Flashcard flashcard = flashcardDatabase.getFlashcard(i);

      boolean flashcardSaved = saveMap.get(editingFlashcard);

      // Save the flashcard, else if it's not saved and is empty, remove it.
      // The flashcard can only be empty if the user chooses to "Add a new flashcard" and not save it.
      if (flashcardSaved) {
        flashcard.setFrontText(editingFlashcard.getFrontText());
        flashcard.setBackText(editingFlashcard.getBackText());
      } else if (flashcard.getFrontText().equals("") && flashcard.getBackText().equals("")) {
        flashcardDatabase.remove(i);
      }
    }

    // Reset the editing database
    editingFlashcardDatabase = new FlashcardDatabase(flashcardDatabase);

    // Reset the save map.
    saveMap = new HashMap<Flashcard, Boolean>();
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      saveMap.put(editingFlashcardDatabase.getFlashcard(i), true);
    }
  }

  public static void updateFile() {
    FlashcardFileManager.saveDataToFile(flashcardDatabase);
  }

  public static boolean isSaved(int flashcardIndex) {
    Flashcard flashcard = editingFlashcardDatabase.getFlashcard(flashcardIndex);
    return saveMap.get(flashcard);
  }

  public static boolean isSaved(Flashcard flashcard) {
    if (!saveMap.containsKey(flashcard)) {
      System.out.println("Flashcard not found in save map.");
    }

    return saveMap.get(flashcard);
  }
  public static boolean isAllSaved() {
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      Flashcard flashcard = editingFlashcardDatabase.getFlashcard(i);
      if (!saveMap.get(flashcard)) {
        return false;
      }
    }

    return true;
  }

  public static void saveAll() {
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      Flashcard flashcard = editingFlashcardDatabase.getFlashcard(i);
      if (saveMap.containsKey(flashcard)) {
        saveMap.put(flashcard, true);
      } else {
        System.out.println("Save all failed. Flashcard not found in save map.");
      }
    }
  }

  public static ArrayList<Integer> getUnsavedFlashcardsNumber() {
    ArrayList<Integer> unsavedFlashcardsNumber = new ArrayList<Integer>();
    for (int i = 0; i < editingFlashcardDatabase.size(); i++) {
      Flashcard flashcard = editingFlashcardDatabase.getFlashcard(i);
      if (!saveMap.get(flashcard)) {
        Integer flashcardNumber = i + 1;
        unsavedFlashcardsNumber.add(flashcardNumber);
      }
    }

    return unsavedFlashcardsNumber;
  }
}
