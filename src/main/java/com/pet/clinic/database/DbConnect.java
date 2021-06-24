package com.pet.clinic.database;
import  java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import org.mariadb.jdbc.MariaDbPoolDataSource;
public class DbConnect implements Runnable{
    private static MariaDbPoolDataSource pool;
    private static Connection connection = null;
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
        Connection connection = null;
        String url = "jdbc:sqlite:localDB/connection.sqlite";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    @Override
    public void run() {

    }
}
