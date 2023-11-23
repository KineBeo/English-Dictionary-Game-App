package EnglishDictionaryGame.Controller;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class GameTimer {
    static Timer currentTimer;
    protected static final long TIME = 60;
    private static long min;
    private static long sec;
    private static long hour;
    static long totalSecond = 0;

    public GameTimer() {
    }

    public static String format(long value) {
        if (value < 10) {
            return 0 + String.valueOf(value);
        }

        return String.valueOf(value);
    }

    public static void convertTime(Label quizTimer) {
        min = TimeUnit.SECONDS.toMinutes(totalSecond);
        sec = totalSecond - TimeUnit.MINUTES.toSeconds(min);
        hour = TimeUnit.MINUTES.toHours(min);
        min = min - TimeUnit.HOURS.toMinutes(hour);
        quizTimer.setText(format(hour) + ":" + format(min) + ":" + format(sec));
        totalSecond--;
    }

    public static void setTimer(Label gameTimer, GameTimer objects) {
        if (currentTimer != null) {
            currentTimer.cancel();
        }
        totalSecond = TIME;
        currentTimer = new Timer();
    TimerTask timerTask =
        new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(
                () -> {
                  convertTime(gameTimer);
                  System.out.println("nock");
                  setZero(gameTimer, objects);
                });
          }
        };

        // preriod: lặp lại
        currentTimer.schedule(timerTask, 0, 1000);
    }

    public static void setZero(Label gameTimer, GameTimer objects) {
        if (objects.getClass().equals(QuizTimer.class)) {
            QuizTimer.handleTotalSecondRunToZero(gameTimer);
        } else if (objects.getClass().equals(HangmanTimer.class)) {
            HangmanTimer.handleTotalSecondRunToZero(gameTimer);
        }
    }

    public static Timer getCurrentTimer() {
        return currentTimer;
    }

    public static void setCurrentTimer(Timer currentTimer) {
        GameTimer.currentTimer = currentTimer;
    }

    public static long getMin() {
        return min;
    }

    public static void setMin(long min) {
        GameTimer.min = min;
    }

    public static long getSec() {
        return sec;
    }

    public static void setSec(long sec) {
        GameTimer.sec = sec;
    }

    public static long getHour() {
        return hour;
    }

    public static void setHour(long hour) {
        GameTimer.hour = hour;
    }

    public static long getTotalSecond() {
        return totalSecond;
    }

    public static void setTotalSecond(long totalSecond) {
        GameTimer.totalSecond = totalSecond;
    }
}
