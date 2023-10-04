package EnglishDictionaryGame.Server;

import java.util.Map;
import java.util.TreeMap;

public class TrieNode {
  Map<Character, TrieNode> children = new TreeMap<>();
  boolean isEndOfWord;

  TrieNode() {
    isEndOfWord = false;
  }
}
