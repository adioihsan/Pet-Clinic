package com.pet.clinic.model.dao;
import com.pet.clinic.model.User;
import com.pet.clinic.database.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private User user;

    public User getUser(int id){
        String query = "SELECT * from users where id=?";
        return user;
    }
    public User getUser(String username) throws SQLException, ClassNotFoundException {
        user = null;
        String query = "SELECT * FROM users where username =?";
        Connection connection = DbConnect.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,username);
        ResultSet res = ps.executeQuery();
        if(res.first()) {
            user = new User();
            while (res.next()) {
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                user.setSalt(res.getBytes("salt"));
            }
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
        ResultSet res = test("adsan");
        while(res.next()){
            System.out.println(res.getBytes("salt"));
        }
    }


}
