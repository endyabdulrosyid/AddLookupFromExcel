package tsel.acim.prepropulate;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db
{
  private static final String _version = "1.1";
  private Connection connection;
  private Statement statement;


  public Db()
  {
  }

  public void open(String driverClass, String dbUrl, String dbUsername, String dbPassword) throws Exception {
    System.out.println("open db connection using " + getClass().getName() + " " + "1.1");
    try
    {
    	System.out.println(" driver: " + driverClass + " url: " + dbUrl + " username: " + dbUsername);
    	
      Class.forName(driverClass);
      this.connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
      this.statement = this.connection.createStatement();
    }
    catch (ClassNotFoundException cnfe) {
    	System.out.println(" db connection error : Class Not Found");
      cnfe.printStackTrace();
      throw new Exception("E");
    } catch (SQLException sqle) {
    	System.out.println(" db connection error : Sql Exception");
      sqle.printStackTrace();
      throw new Exception("E");
    }
  }

  public void close() throws Exception {
    try {
      this.statement.close();
      this.connection.close();
      System.out.println("close db connection");
    }
    catch (SQLException sqle) {
    	System.out.println(" db connection error");
      sqle.printStackTrace();
      throw new Exception("E");
    }
  }

  public void commit() throws Exception {
    try {
      this.connection.commit();
    }
    catch (Exception e) {
    	System.out.println(" db execution error");
      e.printStackTrace();
      throw new Exception("E");
    }
  }

  public ResultSet query(String query) throws Exception
  {
	ResultSet resultSet;
    try
    {
    	System.out.println(" query: " + query);
      resultSet = this.statement.executeQuery(query);
    }
    catch (Exception e)
    {
      System.out.println(" db execution error");
      e.printStackTrace();
      throw new Exception("E");
    }
    return resultSet;
  }

  public void save(String query) throws Exception {
    try {
      System.out.println(" query: " + query);
      this.statement.executeUpdate(query);
    }
    catch (Exception e) {
    	System.out.println(" db execution error");
      e.printStackTrace();
      throw new Exception("E");
    }
  }

  public void updateGeneric(String tableName, String attributeName, String attributeValue, String keyName, String keyValue) throws Exception {
    String query = "UPDATE " + tableName + 
      " SET " + attributeName + "='" + attributeValue + "' " + 
      " WHERE " + keyName + "='" + keyValue + "'";
    save(query);
  }

  public void deleteGeneric(String tableName, String keyName, String keyValue) throws Exception {
    String query = "DELETE FROM " + tableName + 
      " WHERE " + keyName + "='" + keyValue + "'";
    save(query);
  }
}
