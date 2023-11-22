package EnglishDictionaryGame.Server;

import java.util.ArrayList;

public class FlashcardDatabase {
  private ArrayList<Flashcard> flashcards = new ArrayList<>();

  public FlashcardDatabase() {
  }

  public FlashcardDatabase(FlashcardDatabase flashcardDatabase) {
    this.flashcards = new ArrayList<Flashcard>(flashcardDatabase.flashcards);
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

  public void remove(Flashcard flashcard) {
    flashcards.remove(flashcard);
  }

  public void remove(int index) {
    flashcards.remove(index);
  }

  public int size() {
    return flashcards.size();
  }
}
