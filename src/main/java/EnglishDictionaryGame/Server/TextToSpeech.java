package EnglishDictionaryGame.Server;
import EnglishDictionaryGame.Exceptions.Utils;
import com.almasb.fxgl.audio.AudioPlayer;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import javazoom.jl.player.Player;

public class TextToSpeech {

  /** Play the English or Vietnamese sound of a piece of text. */
  public static final String LANGUAGE_ENGLISH = "en";
  public static final String LANGUAGE_VIETNAMESE = "vi";

  public static void playPronunciationSound(String text, String language) {
    try {
      String urlContent = getPronunciationSoundURL(text, language);
      URL url = new URL(urlContent);
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
      InputStream audioSrc = connection.getInputStream();
      new Player(audioSrc).play();
      connection.disconnect();
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }
  }

  private static String getPronunciationSoundURL(String text, String language) {
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder.append("https://translate.google.com.vn/translate_tts?ie=UTF-8&tl=");
    urlBuilder.append(language);
    urlBuilder.append("&client=tw-ob&q=");
    urlBuilder.append(URLEncoder.encode(text, StandardCharsets.UTF_8));

    return urlBuilder.toString();
  }

}
