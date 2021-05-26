package com.pet.clinic.database;
import  java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mariadb.jdbc.MariaDbPoolDataSource;
public class DbConnect extends  Config{

    private static Connection connection;
    private static MariaDbPoolDataSource pool;

/*    public static Connection getConnection() throws ClassNotFoundException, SQLException {
       // Class.forName("org.mariadb.jdbc.MariaDbPoolDataSource");
      //  Class.forName("org.mariadb.jdbc.Driver");
       if(connection == null){
           String connectionString = "jdbc:mariadb://"+dbHost+":"+dbPort+"/"+
                   dbName+"?user="+dbUser+"&password="+dbPass;
           connection = DriverManager.getConnection(connectionString);
       }
       return connection;
    }*/
    public static Connection getConnection(){
        if(pool == null){
            pool = new MariaDbPoolDataSource();
            try {
                pool.setServerName(dbHost);
                pool.setPort(Integer.parseInt(dbPort));
                pool.setDatabaseName(dbName);
                pool.setUser(dbUser);
                pool.setPassword(dbPass);
                pool.setMaxPoolSize(10);
                pool.setMinPoolSize(1);
                connection = pool.getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }
}
