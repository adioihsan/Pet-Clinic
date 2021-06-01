package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Pet;

import java.sql.*;
import java.time.LocalDate;

public class PetDao {

    public static int savePet(Pet pet){
        boolean isOk = false;
        int id = 0;
        String query = "insert into pet(ownerId,name,dob,gender,kind,race,color,photo,timestamp) values(?,?,?,?,?,?,?,?,?)";
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
            ps.setString(8,pet.getPhoto());
            ps.setTimestamp(9,pet.getTimestamp());
            isOk = ps.executeUpdate() > 0;
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

    public static void main(String[] args){
        Pet pet = new Pet();
        pet.setOwnerId(2003);
        pet.setName("Keni");
        pet.setDob(LocalDate.of(2020,01,03));
        pet.setGender("Jantan");
        pet.setKind("Kucing");
        pet.setRace("Lokal");
        pet.setColor("Putih");
        pet.setPhoto("keni.png");
        java.util.Date date = new java.util.Date();
        pet.setTimestamp(new Timestamp(date.getTime()));
        System.out.println(savePet(pet));

    }

}