package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetKind;
import javafx.collections.FXCollections;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class PetDao {

    public static int insertPet(Pet pet){
        boolean isOk = false;
        int id = 0;
        String query = "insert into pet(petOwnerId,name,dob,gender,kind,race,color,timestamp) values(?,?,?,?,?,?,?,?)";
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

    public static  boolean updatePet(Pet pet){
        boolean status = false;
        String query = "update pet set name=?,dob=?,gender=?,kind=?,race=?,color=? where petId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,pet.getName());
            ps.setDate(2,Date.valueOf(pet.getDob()));
            ps.setString(3,pet.getGender());
            ps.setString(4,pet.getKind());
            ps.setString(5,pet.getRace());
            ps.setString(6, pet.getColor());
            ps.setInt(7,pet.getId());
            status = ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static  boolean deletePet(int id){
        boolean status = false;
        String query = "delete from pet where petId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            status = ps.executeUpdate() > 0 ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static void insertPetPhoto(int id, String photoName){
        String query = "update pet set photo=? where petId=?";
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


    public static Pet getPet(int id){
        String query = "select * from pet where petId=?";
        Pet pet = null;
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet res = ps.executeQuery();
            if(res.next()){
                pet = new Pet();
                pet.setId(res.getInt("petId"));
                pet.setOwnerId(res.getInt("petOwnerId"));
                pet.setName(res.getString("name"));
                pet.setDob(res.getDate("dob").toLocalDate());
                pet.setGender(res.getString("gender"));
                pet.setKind(res.getString("kind"));
                pet.setRace(res.getString("race"));
                pet.setColor(res.getString("color"));
                pet.setPhoto(res.getString("photo"));
                pet.setTimestamp(res.getTimestamp("timestamp"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pet;
    }

    public static ArrayList<Pet> getAllPet(int limit){
        String query = "select * from pet limit "+limit;
        ArrayList<Pet> petsList = new ArrayList();
        Connection con = DbConnect.getConnection();
        try {
           ResultSet res = con.createStatement().executeQuery(query);
           while(res.next()){
               Pet pet = new Pet();
               pet.setId(res.getInt("petId"));
               pet.setOwnerId(res.getInt("petOwnerId"));
               pet.setName(res.getString("name"));
               pet.setDob(res.getDate("dob").toLocalDate());
               pet.setGender(res.getString("gender"));
               pet.setKind(res.getString("kind"));
               pet.setRace(res.getString("race"));
               pet.setColor(res.getString("color"));
               pet.setTimestamp(res.getTimestamp("timestamp"));
               petsList.add(pet);
           }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return petsList;
    }

    public static ArrayList<Pet> findPets(String keyword,int limit){
        String query = "select * from pet where petId=? or petOwnerId=? or name like(?) or gender like(?)" +
                " or kind like(?) or race like(?) or color like(?) limit "+limit;
        ArrayList<Pet> petsList = new ArrayList();
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,keyword);
            ps.setString(2,keyword);
            ps.setString(3,"%"+keyword+"%");
            ps.setString(4,"%"+keyword+"%");
            ps.setString(5,"%"+keyword+"%");
            ps.setString(6,"%"+keyword+"%");
            ps.setString(7,"%"+keyword+"%");
            ResultSet res = ps.executeQuery();
            while(res.next()){
                Pet pet = new Pet();
                pet.setId(res.getInt("petId"));
                pet.setOwnerId(res.getInt("petOwnerId"));
                pet.setName(res.getString("name"));
                pet.setDob(res.getDate("dob").toLocalDate());
                pet.setGender(res.getString("gender"));
                pet.setKind(res.getString("kind"));
                pet.setRace(res.getString("race"));
                pet.setColor(res.getString("color"));
                pet.setTimestamp(res.getTimestamp("timestamp"));
                petsList.add(pet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return petsList;
    }



    public static ArrayList<PetKind>  getPetKind(){
        ArrayList<PetKind> petkinds = new ArrayList();
        try {
            ResultSet res = DbConnect.getConnection().createStatement().executeQuery("select * from petKind");
            while(res.next()){
                PetKind petKind = new PetKind();
                petKind.setPetKindId(res.getInt("petKindId"));
                petKind.setName(res.getString("name"));
                petkinds.add(petKind);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  petkinds;
    }

    public static double getPetWeight(int petId){
        double petWeight = 0;
        String query = "select petWeight from medicRecord where petId=? order by medicRecordId desc limit 1;";
        try {
            PreparedStatement ps = DbConnect.getConnection().prepareStatement(query);
            ps.setInt(1,petId);
            ResultSet res = ps.executeQuery();
            if(res.next()){
                petWeight = res.getDouble("petWeight");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return petWeight;
    }


}