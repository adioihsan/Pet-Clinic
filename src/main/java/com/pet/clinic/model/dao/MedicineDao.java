package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MedicineDao {

    public static int saveMedicine(Medicine medicine){
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
            id = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
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
                medicine.setIn(res.getInt("in"));
                medicine.setOut(res.getInt("out"));
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
                medicine.setIn(res.getInt("in"));
                medicine.setOut(res.getInt("out"));
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
