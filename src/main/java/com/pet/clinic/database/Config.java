package com.pet.clinic.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Config {
    public static String usedDbHost = "localhost";
    public static int usedDbPort = 3306;
    public static String usedDbUser = "k3";
    public static String usedDbName = "";
    public static String usedDbPass = "123456";

    public static void loadFirstConnection(){
        try {
            ResultSet res = DbConnect.getSqliteConnection().createStatement().executeQuery("select * from configs " +
                    "where isUsed=1");
            if(res.next()){
                usedDbHost = res.getString("host");
                usedDbPort = res.getInt("port");
                usedDbUser = res.getString("user");
                usedDbName = res.getString("database");
                usedDbPass = res.getString("password");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ConfigVar getConnectionUsed(){
        ConfigVar cv = new ConfigVar();
        try {
            ResultSet res = DbConnect.getSqliteConnection().createStatement().executeQuery("select * from configs " +
                    "where isUsed=1");
            if(res.next()){
                cv.setConnectionId(res.getInt("connectionId"));
                cv.setDbHost(res.getString("host"));
                cv.setDbPort(res.getInt("port"));
                cv.setDbUser(res.getString("user"));
                cv.setDbPass(res.getString("password"));
                cv.setDbName(res.getString("database"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cv;
    }

    public static void setUsed(ConfigVar configVar){
        usedDbHost = configVar.getDbHost();
        usedDbPort = configVar.getDbPort();
        usedDbUser = configVar.getDbUser();
        usedDbName = configVar.getDbName();
        usedDbPass = configVar.getDbPass();
    }

    public static void setActive(ConfigVar configVar){
        try {
            DbConnect.getSqliteConnection().createStatement().executeUpdate("update configs set isUsed=0 where isUsed=1");
            DbConnect.getSqliteConnection().createStatement().executeUpdate("update configs set isUsed=1 where " +
                    "connectionId="+configVar.getConnectionId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public static ArrayList<ConfigVar> getConfigsHistory(){
        ArrayList<ConfigVar> configVarList = new ArrayList<>();
        try {
            ResultSet res = DbConnect.getSqliteConnection().createStatement().executeQuery("select * from configs");
            while (res.next()){
                ConfigVar cv = new ConfigVar();
                cv.setConnectionId(res.getInt("connectionId"));
                cv.setDbHost(res.getString("host"));
                cv.setDbPort(res.getInt("port"));
                cv.setDbUser(res.getString("user"));
                cv.setDbPass(res.getString("password"));
                cv.setDbName(res.getString("database"));
                configVarList.add(cv);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return configVarList;
    }
    public static int insertConfig(ConfigVar configVar){
        int id=0;
        String query = "insert into configs(host,port,user,password,database,isUsed) values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DbConnect.getSqliteConnection().prepareStatement(query);
            ps.setString(1, configVar.getDbHost());
            ps.setInt(2,configVar.getDbPort());
            ps.setString(3, configVar.getDbUser());
            ps.setString(4, configVar.getDbPass());
            ps.setString(5, configVar.getDbName());
            ps.setInt(6,0);
            if(ps.executeUpdate() > 0);
            ResultSet res = DbConnect.getSqliteConnection().createStatement().executeQuery("select last_insert_rowid()");
            res.next();
            id = res.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return id;
    }

    public static void deleteHistory(){
        String query ="delete from configs where isUsed=0";
        try {
            DbConnect.getSqliteConnection().createStatement().executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}

