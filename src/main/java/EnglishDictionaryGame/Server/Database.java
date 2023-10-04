package EnglishDictionaryGame.Server;

import java.sql.*;
import java.util.ArrayList;

public class Database {

  public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  public static final String URL = "jdbc:mysql://localhost:3306/EnglishDictionary";
  public static final String USE_NAME = "root";
  public static final String PASSWORD = "1392004";
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
          } finally{
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

  public ArrayList<String> getAllWordFromDatabase() {
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
          } finally{
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
  private void close(ResultSet rs) {

  }

  private void close(PreparedStatement ps) {

  }
}
