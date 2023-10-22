package EnglishDictionaryGame.Server;

import java.sql.*;
import java.util.ArrayList;

public class Database {

  public static final String URL = "jdbc:mysql://localhost:3306/EnglishDictionary";
  public static final String USE_NAME = "root";
  public static final String PASSWORD = "1392004";
  //  public static final String PASSWORD = "password";
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
    final String SQL_QUERY = "SELECT * FROM dictionary";
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
    final String SQL_QUERY = "SELECT * FROM dictionary";
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
    final String SQL_QUERY = "SELECT definition FROM dictionary WHERE target = ?";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      ps.setString(1, target);
      try {
        ResultSet rs = ps.executeQuery();
        try {
          if (rs.next()) {
            return rs.getString("definition");
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

  public boolean insertWord(final String target, final String definition) {
    final String SQL_QUERY = "INSERT INTO dictionary (target, definition) VALUES (?, ?)";
    try {
      PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
      ps.setString(1, target);
      ps.setString(2, definition);
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
    final String SQL_QUERY = "DELETE FROM dictionary WHERE target = ?";
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
    final String SQL_QUERY = "UPDATE dictionary SET definition = ? WHERE target = ?";
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
