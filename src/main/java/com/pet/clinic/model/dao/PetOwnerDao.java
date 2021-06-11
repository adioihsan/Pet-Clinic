package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetOwner;
import com.pet.clinic.model.Prescription;

import java.sql.*;
import java.text.ParseException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

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

    public static boolean savePetOwner(PetOwner petOwner){
        String query = "update petOwner set firstName=? , lastName=?, dob=? , gender=? ,phoneNumber=? , address=? " +
                " where petOwnerId = ?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, petOwner.getFirstName());
            ps.setString(2, petOwner.getLastName());
            ps.setDate(3, Date.valueOf(petOwner.getDob()));
            ps.setString(4,petOwner.getGender());
            ps.setDouble(5,petOwner.getPhoneNumber());
            ps.setString(6,petOwner.getAddress());
            ps.setInt(7,petOwner.getId());

            return ps.executeUpdate() > 0 ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
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
    public static ArrayList<PetOwner> getAllPetOwner(int limit){
        ArrayList<PetOwner> petOwners = new ArrayList<>();
        String query = "select * from petOwner limit ? ";
        Connection con =  DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,limit);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                PetOwner petOwner = new PetOwner();
                petOwner.setId(res.getInt("petOwnerId"));
                petOwner.setFirstName(res.getString("firstName"));
                petOwner.setLastName(res.getString("lastName"));
                petOwner.setDob(res.getDate("dob").toLocalDate());
                petOwner.setGender(res.getString("gender"));
                petOwner.setPhoneNumber(res.getDouble("phoneNumber"));
                petOwner.setAddress(res.getString("address"));
                petOwner.setPhoto(res.getString("photo"));
                petOwners.add(petOwner);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  petOwners;
    }

    public static ArrayList<PetOwner> findPetOwners(String keyword , int limit){
        ArrayList<PetOwner> petOwners = new ArrayList<>();
        String query = "select * from petOwner where petOwnerId=? or firstName like(?) or lastName like(?) " +
                "or gender like(?) or phoneNumber=?  or address like(?) limit ?";
        Connection con =  DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,keyword);
            ps.setString(2,"%"+keyword+"%");
            ps.setString(3,"%"+keyword+"%");
            ps.setString(4,"%"+keyword+"%");
            ps.setString(5,keyword);
            ps.setString(6,"%"+keyword+"%");;
            ps.setInt(7,limit);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                PetOwner petOwner = new PetOwner();
                petOwner.setId(res.getInt("petOwnerId"));
                petOwner.setFirstName(res.getString("firstName"));
                petOwner.setLastName(res.getString("lastName"));
                petOwner.setDob(res.getDate("dob").toLocalDate());
                petOwner.setGender(res.getString("gender"));
                petOwner.setPhoneNumber(res.getDouble("phoneNumber"));
                petOwner.setAddress(res.getString("address"));
                petOwner.setPhoto(res.getString("photo"));
                petOwners.add(petOwner);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  petOwners;
    }

    public static boolean deletePetOwner(int petOwnerId){
        boolean status = false;
        String query = "delete from petOwner where petOwnerId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,petOwnerId);
            status = ps.executeUpdate() > 0 ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    };


    public static ArrayList<String> getPetsName(int petOwnerId){
        ArrayList<String> petsName = new ArrayList<>();
        String query = "select * from pet where petOwnerId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,petOwnerId);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                String petName = "("+res.getInt("petId") +") - "+ res.getString("name");
                petsName.add(petName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return petsName;
    }


public static void main(String[] args){
  System.out.println(deletePetOwner(2003));
    }

}
