package EnglishDictionaryGame.Server;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TranslationService {

  private static final int MAX_SOURCE_TEXT_LENGTH = 100;
  public static String translate(String sourceText, String sourceLang, String targetLang) {
    String translation = "";
    try {
      translation = getTranslation(sourceText, sourceLang, targetLang);
    } catch (IOException internetNotFoundException) {
      translation = "It seems a connection cannot be established.\n"
          + "Please check your internet connection.";
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }

    return translation;
  }

  private static String getTranslation(String sourceText, String sourceLang, String targetLang)
      throws MalformedURLException, URISyntaxException, IOException {
    if (sourceText.length() >= MAX_SOURCE_TEXT_LENGTH) {
      String lengthExceededResponse = "Text length exceeded maximum length.\n" +
          "Please limit to " + MAX_SOURCE_TEXT_LENGTH + " characters.";
      return lengthExceededResponse;
    }

    URL translationRequestURL = buildTranslationRequestURL(sourceText, sourceLang, targetLang);
    String translationResponse = getTranslationRequestResponse(translationRequestURL);
    return translationResponse;
  }

  private static URL buildTranslationRequestURL(
      String sourceText, String sourceLang, String targetLang)
      throws MalformedURLException, URISyntaxException {
    StringBuilder urlStringBuilder = new StringBuilder();
    urlStringBuilder
        .append(
            "https://script.google.com/macros/s/AKfycbwCw24C-pC1vmV9dc5SJEoKE_B4bby9xRCSG7WigJpqFiCf_Zi0LmXXgohO8KNLbaFX/exec")
        .append("?text=")
        .append(URLEncoder.encode(sourceText, StandardCharsets.UTF_8))
        .append("&source=")
        .append(sourceLang)
        .append("&target=")
        .append(targetLang);

    URI translationRequestURI = new URI(urlStringBuilder.toString());
    URL translationRequestURL = translationRequestURI.toURL();
    return translationRequestURL;
  }

  private static String getTranslationRequestResponse(URL url) throws IOException {
    Scanner scanner = new Scanner(url.openStream());
    String content = scanner.useDelimiter("\\Z").next();
    scanner.close();

    return content;
  }
}
