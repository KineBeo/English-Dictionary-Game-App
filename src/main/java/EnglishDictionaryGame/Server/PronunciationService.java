package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Exceptions.Utils;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import javazoom.jl.player.Player;

public class PronunciationService {

  /**
   * Play the English or Vietnamese sound of a piece of text.
   */
  public static final String LANGUAGE_ENGLISH = "en";
  public static final String LANGUAGE_VIETNAMESE = "vi";

  public static void pronounce(String text, String language) {
    try {
      playPronunciationSound(text, language);
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }
  }


  private static void playPronunciationSound(String text, String language) throws Exception {
    URL sourceAudioURL = getPronunciationRequestURL(text, language);
    InputStream audioSource = getPronunciationRequestAudio(sourceAudioURL);
    playPronunciationAudio(audioSource);
  }

  private static URL getPronunciationRequestURL(String text, String language)
      throws Exception {
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder.append("https://translate.google.com.vn/translate_tts?ie=UTF-8&tl=");
    urlBuilder.append(language);
    urlBuilder.append("&client=tw-ob&q=");
    urlBuilder.append(URLEncoder.encode(text, StandardCharsets.UTF_8));

    URI pronunciationRequestURI = new URI(urlBuilder.toString());
    URL pronunciationRequestURL = pronunciationRequestURI.toURL();

    return pronunciationRequestURL;
  }

  private static InputStream getPronunciationRequestAudio(URL sourceAudioURL) throws Exception {
    HttpsURLConnection connection = (HttpsURLConnection) sourceAudioURL.openConnection();
    InputStream audioSource = connection.getInputStream();

    return audioSource;
  }

  private static void playPronunciationAudio(InputStream audioSource) throws Exception {
    new Player(audioSource).play();
  }
}
