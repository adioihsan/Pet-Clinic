package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.ActionsData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActionsDataDao {

    public  static boolean saveActionsData(ActionsData actionsData){
        boolean status = false;
        String query = "insert into actionsData(medicRecordId,actionId,description) values(?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,actionsData.getMedicRecordId());
            ps.setInt(2,actionsData.getActionId());
            ps.setString(3,actionsData.getDescription());
            status = ps.executeUpdate() > 0 ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }
    public  static boolean updateActionsData(ActionsData actionsData){
        boolean status = false;
        String query = "insert into actionsData(medicRecordId,actionId,description) values(?,?,?)";
        Connection con = DbConnect.getConnection();
        try {

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,actionsData.getMedicRecordId());
            ps.setInt(2,actionsData.getActionId());
            ps.setString(3,actionsData.getDescription());
            status = ps.executeUpdate() > 0 ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static  boolean deleteActionsData(int medicRecordId){
        String query = "delete from actionsData where medicRecordId="+medicRecordId;
        try {
            return  DbConnect.getConnection().createStatement().executeUpdate(query) > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static ArrayList<ActionsData> getActionsData(int medicRecordId){
        ArrayList<ActionsData> actionsDataList = new ArrayList<>();
        try {
            ResultSet res = DbConnect.getConnection().createStatement().executeQuery("select * from actionsData" +
                    " join action using(actionId) where medicRecordId="+medicRecordId);
            while (res.next()){
                ActionsData actionsData = new ActionsData();
                actionsData.setMedicRecordId(res.getInt("medicRecordId"));
                actionsData.setActionId(res.getInt("actionId"));
                actionsData.setActionName(res.getString("name"));
                actionsData.setPrice(res.getDouble("price"));
                actionsData.setDescription(res.getString("description"));
                actionsDataList.add(actionsData);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  actionsDataList;
    }

}
