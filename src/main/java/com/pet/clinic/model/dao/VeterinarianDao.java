package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetOwner;
import com.pet.clinic.model.Veterinarian;

import java.sql.*;
import java.util.ArrayList;

public class VeterinarianDao {

    public static int insertVeterinarian(Veterinarian veterinarian){
        int  id = 0;
        boolean isOk = false;
        String query = "insert into veterinarian(firstname,lastName,title,specialist,dob,gender,phoneNumber,address) values(?,?,?,?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,veterinarian.getFirstName());
            ps.setString(2,veterinarian.getLastName());
            ps.setString(3, veterinarian.getTitle());
            ps.setString(4, veterinarian.getSpecialist());
            ps.setDate(5, Date.valueOf(veterinarian.getDob()));
            ps.setString(6,veterinarian.getGender());
            ps.setDouble(7,veterinarian.getPhoneNumber());
            ps.setString(8,veterinarian.getAddress());
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

    public static boolean updateVeterinarian(Veterinarian vet){
        boolean status = false;
        String query = "update veterinarian set firstName=? , lastName=? , title=? , specialist=? , dob=? , gender=?," +
                "phoneNumber=?,address=? where id=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,vet.getFirstName());
            ps.setString(2,vet.getLastName());
            ps.setString(3,vet.getTitle());
            ps.setString(4,vet.getSpecialist());
            ps.setDate(5,Date.valueOf(vet.getDob()));
            ps.setString(6,vet.getGender());
            ps.setDouble(7,vet.getPhoneNumber());
            ps.setString(8, vet.getAddress());
            ps.setInt(9,vet.getId());
            status = ps.executeUpdate() > 0 ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        };
        return status;
    }

    public static boolean deleteVeterinarian(int id){
        boolean status = false;
        String query = "delete from veterinarian where id=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            status = ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }



    public static void insertPhoto(int id, String photoName){
        String query = "update veterinarian set photo=? where id=?";
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

    public static Veterinarian getVeterinarian(int id){
        Veterinarian vet = null;
        String query = "select * from veterinarian where id=? ";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet res = ps.executeQuery();
            if(res.next()){
                vet = new Veterinarian();
                vet.setId(res.getInt("id"));
                vet.setFirstName(res.getString("firstName"));
                vet.setLastName(res.getString("lastName"));
                vet.setTitle(res.getString("title"));
                vet.setSpecialist(res.getString("specialist"));
                vet.setDob(res.getDate("dob").toLocalDate());
                vet.setGender(res.getString("gender"));
                vet.setPhoneNumber(res.getDouble("phoneNumber"));
                vet.setAddress(res.getString("address"));
                vet.setPhoto(res.getString("photo"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vet;
    }

    public static ArrayList<Veterinarian> getAllPet(){
        String query = "select * from veterinarian";
        ArrayList<Veterinarian> vetsList = new ArrayList();
        Connection con = DbConnect.getConnection();
        try {
            ResultSet res = con.createStatement().executeQuery(query);
            while(res.next()){
                System.out.println("Result Not Empty");
                Veterinarian veterinarian = new Veterinarian();
                veterinarian.setId(res.getInt("id"));
                veterinarian.setFirstName(res.getString("firstName"));
                veterinarian.setLastName(res.getString("lastName"));
                veterinarian.setTitle(res.getString("title"));
                veterinarian.setSpecialist(res.getString("specialist"));
                veterinarian.setDob(res.getDate("dob").toLocalDate());
                veterinarian.setGender(res.getString("gender"));
                veterinarian.setPhoneNumber(Double.valueOf(res.getString("phoneNumber")));
                veterinarian.setAddress(res.getString("address"));
                vetsList.add(veterinarian);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vetsList;
    }
}