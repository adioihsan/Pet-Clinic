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
        String query = "SELECT * from users where id=?";
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
            user.setId(res.getInt("id"));
            user.setType(res.getString("type"));
            user.setFirstName(res.getString("firstname"));
            user.setLastName(res.getString("lastname"));
            user.setUsername(res.getString("username"));
            user.setHash(res.getString("hash"));
            user.setSalt(res.getString("salt"));
        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public static ResultSet test(String username) throws SQLException, ClassNotFoundException {
      String query = "select * from users where username=?";
      Connection connection = DbConnect.getConnection();
      PreparedStatement ps = connection.prepareStatement(query);
      ps.setString(1,username);
      ResultSet res = ps.executeQuery();
      return res;

    };

    public  static void main(String[] args) throws SQLException, ClassNotFoundException {
        User myuser = getUser("adsan");
        System.out.println(myuser.getUsername());
    }


}
