package EnglishDictionaryGame.Controller;

import EnglishDictionaryGame.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuizTimer extends GameTimer {
        public static void handleTotalSecondRunToZero(Label quizTimer) {
            if (totalSecond < 0) {
                currentTimer.cancel();
                quizTimer.setText("00:00:00");
                try {
                    FXMLLoader loader =
                            new FXMLLoader(Main.class.getResource("fxml/QuizResult.fxml"));
                    Scene quizscene = new Scene(loader.load());
                    quizscene.setFill(Color.TRANSPARENT);
                    Stage quizstage = new Stage();
                    quizstage.setScene(quizscene);
                    quizstage.initStyle(StageStyle.TRANSPARENT);
                    quizstage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void resetTimer(Label quizTimer) {
        if (currentTimer != null) {
            currentTimer.cancel();
            totalSecond = TIME;
            setTimer(quizTimer, new QuizTimer());
        }
    }
}
