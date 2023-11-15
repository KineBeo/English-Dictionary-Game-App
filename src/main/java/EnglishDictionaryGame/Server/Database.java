package EnglishDictionaryGame.Server;

import java.sql.*;
import java.util.ArrayList;

public class Database {

  public static final String URL = "jdbc:mysql://localhost:3306/CompleteDictionary";
  public static final String USE_NAME = "root";
//  public static final String PASSWORD = "1392004";
      public static final String PASSWORD = "password";
  private static Connection connection = null;

  public void connectToDatabase() throws SQLException {
    System.out.println("Connecting to database...");
    connection = DriverManager.getConnection(URL, USE_NAME, PASSWORD);
    System.out.println("System connected to database successfully!");
  }

  public void initialize() throws SQLException {
    connectToDatabase();
    ArrayList<String> targets = getAllWordTargets();
    for (String word : targets) {
      Trie.insert(word);
    }
  }

  public ArrayList<String> getAllWordTargets() {
    final String SQL_QUERY = "SELECT * FROM english";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      try {
        ResultSet rs = ps.executeQuery();
        try {
          ArrayList<String> targets = new ArrayList<>();
          while (rs.next()) {
            targets.add(rs.getString(2));
          }
          return targets;
        } finally {
          close(rs);
        }
      } finally {
        close(ps);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return new ArrayList<>();
  }

  public ArrayList<String> getAllWordsFromDatabase() {
    final String SQL_QUERY = "SELECT * FROM english";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      try {
        ResultSet rs = ps.executeQuery();
        try {
          ArrayList<String> targets = new ArrayList<>();
          while (rs.next()) {
            targets.add(rs.getString(2));
          }
          return targets;
        } finally {
          close(rs);
        }
      } finally {
        close(ps);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return new ArrayList<>();
  }

  /** Return definition of `target` word. */
  public String lookUpWord(final String target) {
    final String SQL_QUERY = "SELECT meaning FROM english WHERE word = ?";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      ps.setString(1, target);
      try {
        ResultSet rs = ps.executeQuery();
        try {
          if (rs.next()) {
            return rs.getString("meaning");
          } else {
            return "Not found!";
          }
        } finally {
          close(rs);
        }
      } finally {
        close(ps);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "Not found!";
  }

  public WordInfo findWord(final String target) {
    final String SQL_QUERY =
        "SELECT word, type, meaning, pronunciation, example, synonym, antonyms FROM english WHERE word = ?";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      ps.setString(1, target);
      try {
        ResultSet rs = ps.executeQuery();
        try {
          if (rs.next()) {
            return new WordInfo(
                rs.getString("word"),
                rs.getString("type"),
                rs.getString("meaning"),
                rs.getString("pronunciation"),
                rs.getString("example"),
                rs.getString("synonym"),
                rs.getString("antonyms"));
          } else {
            return null;
          }
        } finally {
          close(rs);
        }
      } finally {
        close(ps);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean insertWord(
      final String target,
      final String definition,
      final String type,
      final String pronunciation,
      final String example,
      final String synonym,
      final String antonyms) {
    final String SQL_QUERY =
        "INSERT INTO english(word, type, meaning, pronunciation, example, synonym, antonyms) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      ps.setString(1, target);
      ps.setString(2, type);
      ps.setString(3, definition);
      ps.setString(4, pronunciation);
      ps.setString(5, example);
      ps.setString(6, synonym);
      ps.setString(7, antonyms);
      try {
        ps.executeUpdate();
      } catch (SQLIntegrityConstraintViolationException e) {
        // `word` is already in database
        return false;
      } finally {
        close(ps);
      }
      Trie.insert(target);
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteWord(final String target) {
    final String SQL_QUERY = "DELETE FROM english WHERE word = ?";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      ps.setString(1, target);
      try {
        int deleteRows = ps.executeUpdate();
        if (deleteRows == 0) {
          return false;
        }
      } finally {
        close(ps);
      }
      Trie.delete(target);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean updateWordDefinition(final String target, final String definition) {
    final String SQL_QUERY = "UPDATE english SET meaning = ? WHERE word = ?";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      ps.setString(1, definition);
      ps.setString(2, target);
      try {
        int updateRows = ps.executeUpdate();
        if (updateRows == 0) {
          return false;
        }
      } finally {
        close(ps);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private void close(ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void close(PreparedStatement ps) {
    try {
      if (ps != null) {
        ps.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean isWordExist(String target) {
    return !lookUpWord(target).equals("Not found!");
  }
}
