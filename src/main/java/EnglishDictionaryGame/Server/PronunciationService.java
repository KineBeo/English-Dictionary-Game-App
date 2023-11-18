package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Exceptions.Utils;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import javazoom.jl.player.Player;

public class PronunciationService {

  /** Play the English or Vietnamese sound of a piece of text. */
  public static final String LANGUAGE_ENGLISH = "en";

  public static final String LANGUAGE_VIETNAMESE = "vi";

  public static final String LANGUAGE_SPANISH = "es";
  public static final String LANGUAGE_FRENCH = "fr";
  public static final String LANGUAGE_JAPANESE = "ja";

  public static void pronounce(String text, String language) {
    try {
      playPronunciationSound(text, language);
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }
  }

  private static void playPronunciationSound(String text, String language) throws Exception {
    URL sourceAudioURL = getPronunciationRequestURL(text, language);
    playAudioFromURL(sourceAudioURL);
  }

  private static URL getPronunciationRequestURL(String text, String language) throws Exception {
    String urlBuilder = "https://translate.google.com.vn/translate_tts?ie=UTF-8&tl=" +
            language +
            "&client=tw-ob&q=" +
            URLEncoder.encode(text, StandardCharsets.UTF_8);

    URI pronunciationRequestURI = new URI(urlBuilder);
    URL pronunciationRequestURL = pronunciationRequestURI.toURL();

    return pronunciationRequestURL;
  }

  private static void playAudioFromURL(URL audioSourceURL) throws Exception {
    HttpsURLConnection connection = (HttpsURLConnection) audioSourceURL.openConnection();
    InputStream audioSource = connection.getInputStream();
    Player audioPlayer = new Player(audioSource);
    audioPlayer.play();
    audioSource.close();
    connection.disconnect();
  }
}
