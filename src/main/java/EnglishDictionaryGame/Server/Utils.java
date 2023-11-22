package EnglishDictionaryGame.Server;

public class Utils {
  private static final String PROJECT_CLASS_PREFIX = "dictionaryapplication.englishdictionarygameapp";

  public static void printRelevantStackTrace(Exception e) {
    StackTraceElement[] stackTraceElements = e.getStackTrace();

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
      System.out.println(e.getMessage());
      if (startIndex > -1) {
        for (int i = startIndex; i > -1; i--) {
          System.out.println("at: " + stackTraceElements[i].toString());
        }
      }
      System.out.println();
    }
  }
}
