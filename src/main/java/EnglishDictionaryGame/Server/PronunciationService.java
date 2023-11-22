package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Exceptions.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javax.net.ssl.HttpsURLConnection;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PronunciationService {

  /**
   * Play the English or Vietnamese sound of a piece of text.
   */
  public static final String LANGUAGE_ENGLISH = "en";

  public static final String LANGUAGE_VIETNAMESE = "vi";

  public static final String LANGUAGE_SPANISH = "es";
  public static final String LANGUAGE_FRENCH = "fr";
  public static final String LANGUAGE_JAPANESE = "ja";

  public static void pronounce(String text, String language) {
    try {
      if (text == null || text.isEmpty() || text.isBlank()) {
        return;
      }
      playPronunciationSound(text, language);
    } catch (IOException internetNotFoundException) {
      Platform.runLater(() -> {
        Alert internetNotFoundAlert = createInternetNotFoundAlert();
        internetNotFoundAlert.show();
      });
    } catch (Exception e) {
      Utils.printRelevantStackTrace(e);
    }
  }

  private static void playPronunciationSound(String text, String language)
      throws URISyntaxException, MalformedURLException, IOException, JavaLayerException {
    if (text.isEmpty()) {
      return;
    }

    URL sourceAudioURL = getPronunciationRequestURL(text, language);
    playAudioFromURL(sourceAudioURL);
  }

  private static URL getPronunciationRequestURL(String text, String language)
      throws URISyntaxException, MalformedURLException {
    String urlBuilder = "https://translate.google.com.vn/translate_tts?ie=UTF-8&tl=" +
        language +
        "&client=tw-ob&q=" +
        URLEncoder.encode(text, StandardCharsets.UTF_8);

    URI pronunciationRequestURI = new URI(urlBuilder);
    URL pronunciationRequestURL = pronunciationRequestURI.toURL();

    return pronunciationRequestURL;
  }

  private static void playAudioFromURL(URL audioSourceURL) throws IOException, JavaLayerException {
    HttpsURLConnection connection = (HttpsURLConnection) audioSourceURL.openConnection();
    InputStream audioSource = connection.getInputStream();
    Player audioPlayer = new Player(audioSource);
    audioPlayer.play();
    audioSource.close();
    connection.disconnect();
  }

  private static Alert createInternetNotFoundAlert() {
    Alert internetNotFoundAlert = new Alert(Alert.AlertType.ERROR);
    internetNotFoundAlert.setTitle("Internet not found");
    internetNotFoundAlert.setHeaderText("It seems a connection cannot be established.\n"
        + "Please check your internet connection and try again.");
    return internetNotFoundAlert;
  }
}
