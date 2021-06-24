package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Guest;

import java.sql.*;
import java.util.ArrayList;

public class GuestDao {
    public static ArrayList<Guest> getAllGuest(){
        ArrayList<Guest> guestList = new ArrayList<>();
        String query = "select pet.petId,pet.name,pet.petOwnerId,concat(firstName,\" \",lastName) " +
                "as ownerName,visitTime from guestBook join pet using(petId) join petOwner using(petOwnerId)";
        try {
            ResultSet res = DbConnect.getConnection().createStatement().executeQuery(query);
            while (res.next()){
                Guest guest = new Guest();
                guest.setPetId(res.getInt("petId"));
                guest.setPetOwnerId(res.getInt("petOwnerId"));
                guest.setPetName(res.getString("name"));
                guest.setPetOwnerName(res.getString("ownerName"));
                guest.setVisitTime(res.getTimestamp("visitTime").toLocalDateTime());
                guestList.add(guest);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  guestList;
    }
    public static boolean insertGuest(int petId, Timestamp timestamp){
        String query = "insert into guestBook(petId,visitTime) values(?,?)";
        try {
            PreparedStatement ps = DbConnect.getConnection().prepareStatement(query);
            ps.setInt(1,petId);
            ps.setTimestamp(2,timestamp);
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public static boolean deleteGuest(int petId, Timestamp timestamp){
        String query = "delete from guestBook where petId=? and visitTime=?";
        try {
            PreparedStatement ps = DbConnect.getConnection().prepareStatement(query);
            ps.setInt(1,petId);
            ps.setTimestamp(2,timestamp);
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }



}
