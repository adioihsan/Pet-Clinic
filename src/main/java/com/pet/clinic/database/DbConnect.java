package com.pet.clinic.database;
import  java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
public class DbConnect extends  Config{

    private static Connection connection;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
       // Class.forName("org.mariadb.jdbc.MariaDbPoolDataSource");
        Class.forName("org.mariadb.jdbc.Driver");
       if(connection == null){
           String connectionString = "jdbc:mariadb://"+dbHost+":"+dbPort+"/"+
                   dbName+"?user="+dbUser+"&password="+dbPass;
           connection = DriverManager.getConnection(connectionString);
       }
       return connection;
    }

    public static  void main(String[] args){
        try {
            Connection my = getConnection();
            System.out.println(my.getCatalog());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
