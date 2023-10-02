package EnglishDictionaryGame.Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

  public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  public static final String URL = "jdbc:mysql://localhost:3306/EnglishDictionary";
  public static final String USE_NAME = "root";
  public static final String PASSWORD = "1392004";

  private static Connection connection = null;


  public void connectToDatabase() throws Exception {
    System.out.println("Connecting to database...");
    connection = DriverManager.getConnection(URL, USE_NAME, PASSWORD);
    System.out.println("System connected to database successfully!");
  }
}
