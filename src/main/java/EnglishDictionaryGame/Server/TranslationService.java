package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Exceptions.Utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

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
    String translationResponse = getURLResponse(translationRequestURL);
    return translationResponse;
  }

  private static URL buildTranslationRequestURL(String sourceText, String sourceLang,
      String targetLang) throws Exception {
    StringBuilder urlStringBuilder = new StringBuilder();
    urlStringBuilder.append(
            "https://script.google.com/macros/s/AKfycbyWj4Rkeu92H3M9GLpJScoG-l5-LoZFORE4o2Kr27J6897Br1PPw0HEeDflNuqaHz3FTg/exec")
        .append("?q=").append(URLEncoder.encode(sourceText, StandardCharsets.UTF_8))
        .append("&source=").append(sourceLang)
        .append("&target=").append(targetLang);

    URL translationRequestURL = new URL(urlStringBuilder.toString());
    return translationRequestURL;
  }

  private static String getURLResponse(URL url) throws Exception {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("User-Agent", "Mozilla/5.0");

    BufferedReader inputReader = new BufferedReader(
        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
    String inputLine = "";
    StringBuilder responseBuilder = new StringBuilder();
    while ((inputLine = inputReader.readLine()) != null) {
      responseBuilder.append(inputLine);
    }

    inputReader.close();
    return responseBuilder.toString();
  }
}
