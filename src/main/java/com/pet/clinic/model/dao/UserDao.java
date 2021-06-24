package com.pet.clinic.model.dao;
import com.pet.clinic.model.User;
import com.pet.clinic.database.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private static User user;

    public User getUser(int id){
        String query = "SELECT * from users where userId=?";
        return user;
    }
    public static User getUser(String username){
        user = null;
        try{
        String query = "SELECT * FROM users where username =?";
        Connection connection = DbConnect.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,username);
        ResultSet res = ps.executeQuery();
        if(res.next()) {
            user = new User();
            user.setId(res.getInt("userId"));
            user.setType(res.getString("type"));
            user.setFirstName(res.getString("firstname"));
            user.setLastName(res.getString("lastname"));
            user.setUsername(res.getString("username"));
            user.setHash(res.getString("hash"));
            user.setSalt(res.getString("salt"));
            user.setPhoneNumber(res.getDouble("phoneNumber"));
            user.setPhoto(res.getString("photo"));
            user.setPrivilege(res.getString("privilege"));
            user.setType(res.getString("type"));
        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean checkIsRegistered(String username){
        try {
            ResultSet res =  DbConnect.getConnection().createStatement().executeQuery("select * from " +
                    "users where username="+"'"+username+"'");
            return res.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static int insertUser(User user){
        String query = "insert into users(firstname,lastname,username,hash,salt,privilege,phoneNumber,type) values(?,?,?,?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,user.getFirstName());
            ps.setString(2,user.getLastName());
            ps.setString(3,user.getUsername());
            ps.setString(4,user.getHash());
            ps.setString(5,user.getSalt());
            ps.setString(6,user.getPrivilege());
            ps.setDouble(7,user.getPhoneNumber());
            ps.setString(8,user.getType());
            if(ps.executeUpdate() > 0){
                ResultSet res = con.createStatement().executeQuery("select last_insert_id()");
                if(res.next()) return res.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public static boolean updateUserPhoto(int userId ,String fileName){
       String query = "update users set photo=? where userId=?";
       Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,fileName);
            ps.setInt(2,userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
/*    public  static void main(String[] args) throws SQLException, ClassNotFoundException {
        User myuser = getUser("adsan");
        System.out.println(myuser.getUsername());
    }*/


}
