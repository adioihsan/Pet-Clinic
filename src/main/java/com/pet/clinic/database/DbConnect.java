package com.pet.clinic.database;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import  java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pet.clinic.App;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.mariadb.jdbc.MariaDbPoolDataSource;

public class DbConnect{
    private static MariaDbPoolDataSource pool;
    private static Connection connection = null;
    private static Connection liteConnection = null;
    public static Connection getConnection(){
        if(pool == null){
            pool = new MariaDbPoolDataSource();
            try {
                pool.setServerName(Config.usedDbHost);
                pool.setPort(Config.usedDbPort);
                pool.setDatabaseName(Config.usedDbName);
                pool.setUser(Config.usedDbUser);
                pool.setPassword(Config.usedDbPass);
                pool.setMaxPoolSize(10);
                pool.setMinPoolSize(1);
                pool.setLoginTimeout(10);
                connection = pool.getConnection();
            } catch (SQLException throwables) {
                pool.close();
                pool = null;
            }
        }
        return connection;
    }

    public static boolean isConnected(){
        return getConnection() != null;
    }

    public static Connection getSqliteConnection(){
       if(liteConnection == null) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:sqlite:conf/connection.sqlite";
        try {
            liteConnection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       }
        return liteConnection;
    }
    public static boolean testConnection(ConfigVar configVar){
        Connection test = null;
            String connectionString = "jdbc:mariadb://"+configVar.getDbHost()+":"+configVar.getDbPort()+"/"+
                    configVar.getDbName()+"?user="+configVar.getDbUser()+"&password="+configVar.getDbPass();
        try {
            test = DriverManager.getConnection(connectionString);
        } catch (SQLException throwables) {
            System.out.println(configVar);
        }
        return test != null;
    }

    public static boolean checkTables(){
        String query1="select * from pet";
        String query2="select * from petOwner";
        String query3="select * from prescription";
        try {
            ResultSet res = DbConnect.getConnection().createStatement().executeQuery(query1);
            ResultSet res2 = DbConnect.getConnection().createStatement().executeQuery(query2);
            ResultSet res3 = DbConnect.getConnection().createStatement().executeQuery(query3);
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }
    public static boolean checkDatabase(){
        try {
            DbConnect.getConnection().createStatement().executeUpdate("use petclinic");
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public static boolean runScript(String scriptLocation){
        try {
            Reader reader = new BufferedReader(new FileReader(scriptLocation));
            ScriptRunner sr = new ScriptRunner(getConnection());
            sr.runScript(reader);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String args[]){
        runScript("conf/petclinic-createDB.sql");
    }

}
