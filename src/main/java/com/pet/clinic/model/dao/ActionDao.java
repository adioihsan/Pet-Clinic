package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Action;
import javafx.beans.property.ReadOnlySetProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActionDao {

    public static ArrayList<Action>  getAllAction(){
       ArrayList<Action> actions = new ArrayList<>();
       String query = "select * from action";
        try {
            ResultSet res = DbConnect.getConnection().createStatement().executeQuery(query);
            while(res.next()){
                Action action = new Action();
                action.setActionId(res.getInt("actionId"));
                action.setName(res.getString("name"));
                action.setPrice(res.getDouble("price"));
                actions.add(action);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return actions;
    }

    public static boolean insertAction(String actionName,Double actionPrice){
        String query = "insert into action(name,price) values(?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,actionName);
            ps.setDouble(2,actionPrice);
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public static boolean deleteAction(int actionId){
        try {
            return DbConnect.getConnection().createStatement().executeUpdate("delete from action where actionId="
                    +actionId) > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Action> findAction(String keyword , int limit){
        ArrayList<Action> actions = new ArrayList<>();
        String query = "select * from action where actionId=? or name like(?) limit "+limit;
        try {
            PreparedStatement ps = DbConnect.getConnection().prepareStatement(query);
            ps.setString(1,keyword);
            ps.setString(2,"%"+keyword+"%");
            ResultSet res = ps.executeQuery();
            while(res.next()){
                Action action = new Action();
                action.setActionId(res.getInt("actionId"));
                action.setName(res.getString("name"));
                action.setPrice(res.getDouble("price"));
                actions.add(action);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return actions;
    }

}
