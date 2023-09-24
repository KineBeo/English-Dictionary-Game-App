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

  public static class WordTargetContainsNewlineException extends WordExceptions {
    public WordTargetContainsNewlineException(String message) {
      super(message);
    }
  }

  public static class WordExplainContainsNewlineException extends WordExceptions {
    public WordExplainContainsNewlineException(String message) {
      super(message);
    }
  }
}
