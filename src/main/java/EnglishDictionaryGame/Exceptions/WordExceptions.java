package EnglishDictionaryGame.Exceptions;

public class WordExceptions extends Exception {
  public WordExceptions(String message) {
    super(message);
  }

  public static class WordTargetIsEmptyException extends WordExceptions {
    public WordTargetIsEmptyException(String message) {
      super(message);
    }
  }

  public static class WordExplainIsEmptyException extends WordExceptions {
    public WordExplainIsEmptyException(String message) {
      super(message);
    }
  }
}
