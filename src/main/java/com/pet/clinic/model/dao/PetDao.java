package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Pet;
import javafx.collections.FXCollections;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PetDao {

    public static int insertPet(Pet pet){
        boolean isOk = false;
        int id = 0;
        String query = "insert into pet(ownerId,name,dob,gender,kind,race,color,timestamp) values(?,?,?,?,?,?,?,?)";
        String query2 = "select LAST_INSERT_ID()";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,pet.getOwnerId());
            ps.setString(2,pet.getName());
            ps.setDate(3, Date.valueOf(pet.getDob()));
            ps.setString(4,pet.getGender());
            ps.setString(5,pet.getKind());
            ps.setString(6,pet.getRace());
            ps.setString(7, pet.getColor());
            ps.setTimestamp(8,pet.getTimestamp());
            isOk = ps.executeUpdate() > 0;
            System.out.println(isOk);
            if(isOk){
                ResultSet res = con.createStatement().executeQuery(query2);
                res.next();
                id = res.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public static void insertPetPhoto(int id, String photoName){
        String query = "update pet set photo=? where id=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,photoName);
            ps.setInt(2,id);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

/*    public static void getAllPet(){
        String query = "select * from pet";
        ArrayList petsList = new ArrayList();
        Connection con = DbConnect.getConnection();
        try {
           ResultSet res = con.createStatement().executeQuery(query);
           while(res.next()){
               petsList.add()
           }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }*/
}