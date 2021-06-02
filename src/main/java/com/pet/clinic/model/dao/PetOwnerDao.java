package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.PetOwner;

import java.sql.*;
import java.text.ParseException;

import java.time.LocalDate;

public class PetOwnerDao {

    public static int savePetOwner(PetOwner petOwner){
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

    public static void savePetOwnerPhoto(int id,String photoName){
        String query = "update petOwner set photo=? where id=?";
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

}
