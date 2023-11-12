package EnglishDictionaryGame.Server;

public class WordInfo {
    private String word;
    private String type;
    private String meaning;
    private String pronunciation;
    private String example;
    private String synonym;
    private String antonyms;

    public WordInfo(String word, String type, String meaning, String pronunciation, String example, String synonym, String antonyms) {
        this.word = word;
        this.type = type;
        this.meaning = meaning;
        this.pronunciation = pronunciation;
        this.example = example;
        this.synonym = synonym;
        this.antonyms = antonyms;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(String antonyms) {
        this.antonyms = antonyms;
    }
}
