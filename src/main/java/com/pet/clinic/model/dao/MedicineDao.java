package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MedicineDao {

    public static int insertMedicine(Medicine medicine){
        int id =0;
        String query = "insert into medicine(name,fill,stock,unit,buyPrice,sellPrice,expired) values(?,?,?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,medicine.getName());
            ps.setString(2,medicine.getFill());
            ps.setInt(3,medicine.getStock());
            ps.setString(4,medicine.getUnit());
            ps.setDouble(5,medicine.getBuyPrice());
            ps.setDouble(6,medicine.getSellPrice());
            ps.setDate(7, Date.valueOf(medicine.getExpired()));
            if(ps.executeUpdate()>0){
                ResultSet res = con.createStatement().executeQuery("select LAST_INSERT_ID()");
                res.next();
                id = res.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public static boolean updateMedicine(Medicine medicine){
        String query = "update medicine set name=?,fill=?,stock=?,unit=?,buyPrice=?,sellPrice=?,expired=?,inStock=? where " +
                "medicineId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,medicine.getName());
            ps.setString(2,medicine.getFill());
            ps.setInt(3,medicine.getStock());
            ps.setString(4,medicine.getUnit());
            ps.setDouble(5,medicine.getBuyPrice());
            ps.setDouble(6,medicine.getSellPrice());
            ps.setDate(7, Date.valueOf(medicine.getExpired()));
            ps.setInt(8,medicine.getIn());
            ps.setInt(9,medicine.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    
    public static Medicine getMedicine(int medicineId){
        Medicine medicine = new Medicine();
        String query ="select * from medicine where id="+medicineId;
        try {
            ResultSet res = DbConnect.getConnection().createStatement().executeQuery(query);
            if(res.next()){
                medicine.setId(res.getInt("medicineId"));
                medicine.setName(res.getString("name"));
                medicine.setStock(res.getInt("stock"));
                medicine.setUnit(res.getString("unit"));
                medicine.setIn(res.getInt("inStock"));
                medicine.setOut(res.getInt("outStock"));
                medicine.setBuyPrice(res.getInt("buyPrice"));
                medicine.setSellPrice(res.getInt("sellPricce"));
                medicine.setFill(res.getString("fill"));
                medicine.setExpired(res.getDate("expired").toLocalDate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return medicine;
    }

    public static ArrayList<Medicine> getAllMedicine(int limit){
        ArrayList<Medicine> medicineList = new ArrayList();
        String query = "select * from medicine limit "+limit;
        Connection con = DbConnect.getConnection();
        try {
            ResultSet res = con.createStatement().executeQuery(query);
            while(res.next()){
                Medicine medicine = new Medicine();
                medicine.setId(res.getInt("medicineId"));
                medicine.setName(res.getString("name"));
                medicine.setFill(res.getString("fill"));
                medicine.setUnit(res.getString("unit"));
                medicine.setStock(res.getInt("stock"));
                medicine.setIn(res.getInt("inStock"));
                medicine.setOut(res.getInt("outStock"));
                medicine.setExpired(res.getDate("expired").toLocalDate());
                medicine.setBuyPrice(res.getDouble("buyPrice"));
                medicine.setSellPrice(res.getDouble("sellPrice"));
                medicineList.add(medicine);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return medicineList;
    }

    public static ArrayList<Medicine> findMedicine(String keyword, int limit){
        ArrayList<Medicine> medicineList = new ArrayList();
        String query = "select distinct * from medicine where medicineId=? or name like(?)  or fill like(?) or unit like(?) or stock=?" +
                "or expired=? or buyPrice=? or sellPrice=? limit "+limit;
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,keyword);
            ps.setString(2,"%"+keyword+"%");
            ps.setString(3,"%"+keyword+"%");
            ps.setString(4,"%"+keyword+"%");
            ps.setString(5,keyword);
            ps.setString(6,keyword);
            ps.setString(7,keyword);
            ps.setString(8,keyword);

            ResultSet res = ps.executeQuery();
            while(res.next()){
                Medicine medicine = new Medicine();
                medicine.setId(res.getInt("medicineId"));
                medicine.setName(res.getString("name"));
                medicine.setFill(res.getString("fill"));
                medicine.setUnit(res.getString("unit"));
                medicine.setStock(res.getInt("stock"));
                medicine.setIn(res.getInt("inStock"));
                medicine.setOut(res.getInt("outStock"));
                medicine.setExpired(res.getDate("expired").toLocalDate());
                medicine.setBuyPrice(res.getDouble("buyPrice"));
                medicine.setSellPrice(res.getDouble("sellPrice"));
                medicineList.add(medicine);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return medicineList;
    }
/*    public static void main(String[] args){
        ArrayList<Medicine> medicines = getAllMedicine(1);
        Iterator<Medicine> iter = medicines.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next().getName());
        }
    }*/
}
