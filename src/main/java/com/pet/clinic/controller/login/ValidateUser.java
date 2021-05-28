package com.pet.clinic.controller.login;
import com.pet.clinic.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import com.pet.clinic.database.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pet.clinic.model.dao.UserDao;

public class ValidateUser {
    private static User userDb;

    public static boolean isValid(String username,String password){
        userDb = UserDao.getUser(username);
        if (userDb != null) {
            String  hashPassword =  generatePassword(password,userDb.getSalt().getBytes());
            if(hashPassword.equals(userDb.getHash()))
                return true;
        }
        return  false;

    };

    public static User getLoggeduser(){
        return userDb;
    }

    protected static byte[] getSalt() {
        byte[] salt = null;
        //user secure random generator
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            //create array for salt
            salt = new byte[16];
            //get random salt
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        };
        return salt;
    }
    protected static String generatePassword(String password,byte[] salt){
        String generatedPassword = null;
        try {
            //create MessageDigest instance for md5
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            //get the hash bytes
            byte[] bytes = md.digest(password.getBytes());
            // convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i < bytes.length;i++){
                sb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
            }
            //get Complete hased password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return  generatedPassword;
    }
/*    public  static  void main(String[] args){
        String password = "123456";
        String salt = "[B@5bcab519";
        String generatedPass = generatePassword(password,salt.getBytes());
        System.out.println(salt);
        System.out.println(generatedPass);
    }*/


}
