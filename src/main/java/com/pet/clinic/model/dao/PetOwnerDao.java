package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.PetOwner;

import java.sql.*;
import java.text.ParseException;

import java.time.LocalDate;

public class PetOwnerDao {

    public static int insertPetOwner(PetOwner petOwner){
        int  id = 0;
        boolean isOk = false;
        String query = "insert into petOwner(firstname,lastName,dob,gender,phoneNumber,address,timestamp) values(?,?,?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,petOwner.getFirstName());
            ps.setString(2,petOwner.getLastName());
            ps.setDate(3, Date.valueOf(petOwner.getDob()));
            ps.setString(4,petOwner.getGender());
            ps.setDouble(5,petOwner.getPhoneNumber());
            ps.setString(6,petOwner.getAddress());
            ps.setTimestamp(7,petOwner.getTimestamp());
            isOk = ps.executeUpdate() > 0;
            if(isOk){
                ResultSet res = con.createStatement().executeQuery("select LAST_INSERT_ID()");
                res.next();
                id = res.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public static void insertPetOwnerPhoto(int id, String photoName){
        String query = "update petOwner set photo=? where petOwnerId=?";
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

    public static PetOwner getOwner(int id){
        PetOwner petOwner = null;
        String query = "select * from petOwner where petOwnerId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet res = ps.executeQuery();
            if(res.next()){
                petOwner = new PetOwner();
                petOwner.setId(res.getInt("petOwnerId"));
                petOwner.setFirstName(res.getString("firstName"));
                petOwner.setLastName(res.getString("lastName"));
                petOwner.setDob(res.getDate("dob").toLocalDate());
                petOwner.setGender(res.getString("gender"));
                petOwner.setPhoneNumber(res.getDouble("phoneNumber"));
                petOwner.setPhoto(res.getString("photo"));
                petOwner.setAddress(res.getString("address"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return petOwner;
    }


}
