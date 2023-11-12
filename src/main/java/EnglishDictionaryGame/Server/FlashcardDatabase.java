package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Exceptions.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FlashcardDatabase {
  private ArrayList<Flashcard> flashcards;

  public FlashcardDatabase() {
    this.flashcards = new ArrayList<>();
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
