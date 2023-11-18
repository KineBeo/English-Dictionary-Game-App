package EnglishDictionaryGame.Server;

import static EnglishDictionaryGame.Main.database;

import java.util.*;

public class QuizFactory {
  enum questionType {
    chooseMeaning,
    chooseSynonym,
    fillTheBlank,
    translateIntoVietnamese
  }

  private String word;
  private String chooseAnswer;
  private String correctAnswer; // Key
  private int playerScore;
  private int questionNumber; // Số câu hỏi
  private questionType type;
  private WordInfo randomWord;
  private String[] choices = new String[4];

  private static final ArrayList<WordInfo> allWords = database.getAllWordTargets();
  private static final Map<String, String> hashMapGetWordOfMeaning = new HashMap<>();
  private static final Map<String, String> hashMapGetMeaningOfWord = new HashMap<>();
  private static final Map<String, String> hashMapGetWordOfSynonym = new HashMap<>();
  private static final Map<String, String> hashMapGetSynonymOfWord = new HashMap<>();

  public QuizFactory() {
    this.word = "";
    this.chooseAnswer = "";
    this.correctAnswer = "";
    this.playerScore = 0;
    this.questionNumber = 0;
    this.type = questionType.chooseMeaning;
    for (WordInfo x : allWords) {
      hashMapGetWordOfMeaning.put(x.getMeaning(), x.getWord());
      hashMapGetMeaningOfWord.put(x.getWord(), x.getMeaning());
      hashMapGetWordOfSynonym.put(x.getSynonym(), x.getWord());
      hashMapGetSynonymOfWord.put(x.getWord(), x.getSynonym());
    }
  }

  public String createQuestion() {
    randomChooseQuestionType();
    return switch (type) {
      case chooseMeaning -> "What is the meaning of '" + word + "'?";
      case chooseSynonym -> "What is the synonym of '" + word + "'?";
      case fillTheBlank -> "Fill the blank: '" + word + "' means ________.";
      case translateIntoVietnamese -> "Translate '" + word + "' into Vietnamese.";
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
      int random = (int) (Math.random() * allWords.size());
      WordInfo randomWordTarget = allWords.get(random);
      randomWord = database.findWord(randomWordTarget.getWord());
    }

    word = randomWord.getWord();
    return word;
  }

  public String getRandomMeaning() {
    try {
      int random = (int) (Math.random() * allWords.size());
      return allWords.get(random).getMeaning();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return "";
    }
  }

  public String getRandomSynonym() {
    try {
      int random = (int) (Math.random() * allWords.size());
      return allWords.get(random).getSynonym();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return "";
    }
  }

  public boolean suitableChoice(String choice) {
    if (choice.equals("")) {
      return false;
    }
    if (choice.trim().isEmpty()) {
      return false;
    }

    String lowerCaseChoice = choice.trim().toLowerCase();
    if (lowerCaseChoice.startsWith("of")
        || lowerCaseChoice.startsWith("alt")
        || lowerCaseChoice.startsWith(" ")) {
      return false;
    }
    return choice.length() > 15;
  }

  public int countCharacterOfString(String string) {
    int count = 0;
    for (int i = 0; i < string.length(); i++) {
      if (string.charAt(i) != '\n') {
        count++;
      }
    }
    return count;
  }

  public void createRandomQuiz(questionType type) {
    switch (type) {
      case chooseMeaning -> createRandomChooseMeaningQuiz();

      case chooseSynonym -> createRandomChooseSynonymQuiz();

      case fillTheBlank -> createRandomFillTheBlankQuiz();

      case translateIntoVietnamese -> createRandomTranslateIntoVietnameseQuiz();
    }
  }

  private void createRandomChooseMeaningQuiz() {
    for (int i = 0; i < 4; i++) {
      String tmp = getRandomMeaning();
      while (suitableChoice(tmp) || countCharacterOfString(tmp) > 15) {
        tmp = getRandomMeaning();
      }
      choices[i] = tmp;
      System.out.println(
          "Word number " + i + " " + getWordFromMeaning(choices[i]) + "\n" + choices[i]);
    }
    int random = (int) (Math.random() * 4);
    setWord(getWordFromMeaning(choices[random]));
    System.out.println(this.word);
  }

  private void createRandomChooseSynonymQuiz() {
    Set<String> chosenSynonyms = new HashSet<>();
    for (int i = 0; i < 4; i++) {
      String randomSynonym = getRandomSynonym();
      while (chosenSynonyms.contains(randomSynonym)
          || randomSynonym.isEmpty()
          || randomSynonym.isBlank()) {
        randomSynonym = getRandomSynonym();
      }
      chosenSynonyms.add(randomSynonym);
      choices[i] = randomSynonym;
      System.out.println("Synonym num: " + i + ": " + choices[i]);
    }

    setWord(getWordFromSynonym(choices[(int) (Math.random() * 4)]));
    System.out.println("Word: " + this.word);
  }

  private void createRandomFillTheBlankQuiz() {}

  private void createRandomTranslateIntoVietnameseQuiz() {}

  private static String getWordFromMeaning(String meaning) {
    try {
      return hashMapGetWordOfMeaning.get(meaning);
    } catch (Exception e) {
      return "";
    }
  }

  private static String getWordFromSynonym(String synonym) {
    try {
      return hashMapGetWordOfSynonym.get(synonym);
    } catch (Exception e) {
      return "";
    }
  }

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

  public String[] getChoices() {
    return choices;
  }

  public void setChoices(String[] choices) {
    this.choices = choices;
  }
}
