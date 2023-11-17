package EnglishDictionaryGame.Server;

import EnglishDictionaryGame.Controller.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizFactory {
  enum questionType {
    chooseMeaning,
    chooseSynonym,
    fillTheBlank,
    translateIntoVietnamese
  }

  private String word;
  private String chooseAnswer;
  private String correctAnswer;
  private int playerScore;
  private int questionNumber;
  private questionType type;
  private WordInfo randomWord;

  public QuizFactory() {
    this.word = "";
    this.chooseAnswer = "";
    this.correctAnswer = "";
    this.playerScore = 0;
    this.questionNumber = 0;
    this.type = questionType.chooseMeaning;
  }

  public String createQuestion() {
    randomChooseQuestionType();
    getRandomWord();
    return switch (type) {
      case chooseMeaning -> "What is the meaning of '" + word + "'?";
      case chooseSynonym -> "What is the synonym of '" + word + "'?";
      case fillTheBlank -> "Fill the blank: '" + word + "' means ________.";
      case translateIntoVietnamese -> "Translate '" + word + "' into Vietnamese.";
      default -> "";
    };
  }

  public void randomChooseQuestionType() {
    int random = (int) (Math.random() * 4);
    switch (random) {
      case 0 -> type = questionType.chooseMeaning;
      case 1 -> type = questionType.chooseSynonym;
      case 2 -> type = questionType.fillTheBlank;
      case 3 -> type = questionType.translateIntoVietnamese;
    }

    createRandomQuiz(type);
  }

  public String getRandomWord() {
    if (randomWord == null) {
      ArrayList<String> allWords = Application.getDatabase().getAllWordTargets();

      int random = (int) (Math.random() * allWords.size());
      String randomWordTarget = allWords.get(random);
      randomWord = Application.getDatabase().findWord(randomWordTarget);
    }

    word = randomWord.getWord();
    return word;
  }
  public void createRandomQuiz(questionType type) {
    switch (type) {
      case chooseMeaning -> {
        createRandomChooseMeaningQuiz();
      }

      case chooseSynonym -> {
        createRandomChooseSynonymQuiz();
      }

      case fillTheBlank -> {
        createRandomFillTheBlankQuiz();
      }

      case translateIntoVietnamese -> {
        createRandomTranslateIntoVietnameseQuiz();
      }
    }
  }

  private void createRandomChooseMeaningQuiz() {}

  private void createRandomChooseSynonymQuiz() {}

  private void createRandomFillTheBlankQuiz() {}

  private void createRandomTranslateIntoVietnameseQuiz() {}

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getChooseAnswer() {
    return chooseAnswer;
  }

  public void setChooseAnswer(String chooseAnswer) {
    this.chooseAnswer = chooseAnswer;
  }

  public String getCorrectAnswer() {
    return correctAnswer;
  }

  public void setCorrectAnswer(String correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public int getPlayerScore() {
    return playerScore;
  }

  public void setPlayerScore(int playerScore) {
    this.playerScore = playerScore;
  }

  public int getQuestionNumber() {
    return questionNumber;
  }

  public void setQuestionNumber(int questionNumber) {
    this.questionNumber = questionNumber;
  }

  public questionType getType() {
    return type;
  }

  public void setType(questionType type) {
    this.type = type;
  }
}
