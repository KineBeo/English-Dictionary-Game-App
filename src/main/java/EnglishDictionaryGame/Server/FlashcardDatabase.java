package EnglishDictionaryGame.Server;
import java.util.ArrayList;
public class FlashcardDatabase {
  private ArrayList<Flashcard> flashcards;

  public FlashcardDatabase() {
    flashcards = new ArrayList<Flashcard>();
  }

  public void addFlashcard(Flashcard flashcard) {
    flashcards.add(flashcard);
  }

  public Flashcard getFlashcard(int index) {
    return flashcards.get(index);
  }
  public int getIndexOf(Flashcard flashcard) {
    if (!flashcards.contains(flashcard)) return -1;
    return flashcards.indexOf(flashcard);
  }

  public int size() {
    return flashcards.size();
  }
}
