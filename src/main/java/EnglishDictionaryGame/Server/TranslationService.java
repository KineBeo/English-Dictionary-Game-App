package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Exceptions.Utils;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TranslationService {

  public static String translate(String sourceText, String sourceLang, String targetLang) {
    String translation = "";
    try {
      translation = getTranslation(sourceText, sourceLang, targetLang);
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }

    return translation;
  }


  private static String getTranslation(String sourceText, String sourceLang, String targetLang)
      throws Exception {
    URL translationRequestURL = buildTranslationRequestURL(sourceText, sourceLang, targetLang);
    String translationResponse = getTranslationRequestResponse(translationRequestURL);
    return translationResponse;
  }

  private static URL buildTranslationRequestURL(String sourceText, String sourceLang,
      String targetLang) throws Exception {
    StringBuilder urlStringBuilder = new StringBuilder();
    urlStringBuilder.append(
            "https://script.google.com/macros/s/AKfycbwCw24C-pC1vmV9dc5SJEoKE_B4bby9xRCSG7WigJpqFiCf_Zi0LmXXgohO8KNLbaFX/exec")
        .append("?text=").append(URLEncoder.encode(sourceText, StandardCharsets.UTF_8))
        .append("&source=").append(sourceLang)
        .append("&target=").append(targetLang);

    URI translationRequestURI = new URI(urlStringBuilder.toString());
    URL translationRequestURL = translationRequestURI.toURL();
    return translationRequestURL;
  }

  private static String getTranslationRequestResponse(URL url) throws Exception {
    Scanner scanner = new Scanner(url.openStream());
    String content = scanner.useDelimiter("\\Z").next();
    scanner.close();

    return content;
  }
}
