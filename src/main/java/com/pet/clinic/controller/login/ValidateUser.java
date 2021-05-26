package com.pet.clinic.controller.login;
import com.pet.clinic.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ValidateUser {

    private User userLogin;
    private User userDb;
    private String username;
    private String password;
    private String salt;

    public ValidateUser(String username,String password){
        //user from login input
        userLogin = new User();
        userLogin.setUsername(username);
        userLogin.setSalt(getSalt());
        userLogin.setPassword(generatePassword(password,userLogin.getSalt()));

        //user from db

    }

    public static boolean isValid(){

      return false;
    };

    protected static byte[] getSalt() {
        byte[] salt = null;
        //user secure random generator
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            //create array for salt
            salt = new byte[32];
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

}
