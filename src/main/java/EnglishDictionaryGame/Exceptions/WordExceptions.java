package EnglishDictionaryGame.Exceptions;

public class WordExceptions extends Exception {
  private static final String PROJECT_CLASS_PREFIX = "dictionaryapplication.englishdictionarygameapp";
  public Exceptions(String message) {
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

  public static class Utils {
    public static void printElementStackTrace(Exceptions we) {
      System.out.println(we.getMessage());

      StackTraceElement[] stackTraceElements = we.getStackTrace();

      // Find the highest relevant stack trace element index
      if (stackTraceElements.length > 0) {
        int startIndex = -1;
        for (int i = stackTraceElements.length - 1; i > -1; i--) {
          if (stackTraceElements[i].toString()
              .startsWith(PROJECT_CLASS_PREFIX)) {
            startIndex = i;
            break;
          }
        }

        // Print out all stack elements starting from the top relevant one
        if (startIndex > -1) {
          for (int i = startIndex; i > -1; i--) {
            System.out.println("at: " + stackTraceElements[i].toString());
          }
        }

        System.out.println();
      }
    }
  }
}
