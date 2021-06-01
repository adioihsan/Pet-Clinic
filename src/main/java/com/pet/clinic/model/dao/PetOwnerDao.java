package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Pet;
import com.pet.clinic.model.PetOwner;
import javafx.util.converter.LocalDateStringConverter;
import org.mariadb.jdbc.internal.com.send.SendSslConnectionRequestPacket;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class PetOwnerDao {

/*    public static int holdId(Timestamp timestamp){
        int id = 0 ;
        String query = "insert into petOwner(firstname) values('hold')";
        String query2 = "select id from petOwner where firstname='hold'";
        Connection con = DbConnect.getConnection();
        try {
            con.createStatement().executeUpdate(query);
            ResultSet res = con.createStatement().executeQuery(query2);
            res.next();
            id = res.getInt("id");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }*/

    public static int savePetOwner(PetOwner petOwner){
        int  id = 0;
        boolean isOk = false;
        String query = "insert into petOwner(firstname,lastName,dob,gender,phoneNumber,address,photo,timestamp) values(?,?,?,?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,petOwner.getFirstName());
            ps.setString(2,petOwner.getLastName());
            ps.setDate(3, Date.valueOf(petOwner.getDob()));
            ps.setString(4,petOwner.getGender());
            ps.setDouble(5,petOwner.getPhoneNumber());
            ps.setString(6,petOwner.getAddress());
            ps.setString(7,petOwner.getPhoto());
            ps.setTimestamp(8,petOwner.getTimestamp());
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

/*
    public static int getPetOwnerId(PetOwner petOwner){
        int id = 0;
        String query = "select id from petOwner where firstname=? and lastname=? and address=? and timestamp=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,petOwner.getFirstName());
            ps.setString(2,petOwner.getLastName());
            ps.setString(3,petOwner.getAddress());
            ps.setTimestamp(4,petOwner.getTimestamp());

            ResultSet res = ps.executeQuery();
            res.next();
            id = res.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
*/

    public static  void main(String[] args) throws ParseException {
        PetOwner petOwner = new PetOwner();
        petOwner.setFirstName("Dinda");
        petOwner.setLastName("tryandahry");
        petOwner.setDob(LocalDate.of(2000,01,01));
        petOwner.setGender("Perempuan");
        petOwner.setPhoneNumber(0833451233d);
        petOwner.setAddress("No Where Some where else");
        petOwner.setPhoto("myphoto.png");
        java.util.Date date = new java.util.Date();
        petOwner.setTimestamp(new Timestamp(date.getTime()));

        int newId = savePetOwner(petOwner);
        System.out.println(newId);

    }
}
