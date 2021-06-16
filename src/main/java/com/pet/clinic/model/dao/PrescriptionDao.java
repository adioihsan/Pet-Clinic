package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Prescription;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionDao {

    public static boolean insertPrescription(Prescription prescription){
        boolean status = false;
        String query = "insert into prescription(medicRecordId,medicineId,description,amount) value(?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,prescription.getMedicRecordId());
            ps.setInt(2,prescription.getMedicineId());
            ps.setString(3,prescription.getDescription());
            ps.setInt(4,prescription.getAmount());
            status = ps.executeUpdate() > 0 ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return status;
    }

    public static boolean updatePrescription(Prescription prescription){
        boolean status = false;
        String query = "insert into prescription(medicRecordId,medicineId,description,amount) value(?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,prescription.getMedicRecordId());
            ps.setInt(2,prescription.getMedicineId());
            ps.setString(3,prescription.getDescription());
            ps.setInt(4,prescription.getAmount());
            status = ps.executeUpdate() > 0 ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static boolean deletePrescription(int medicRecordId){
        String query = "delete from prescription where medicRecordId="+medicRecordId;
        try {
            return DbConnect.getConnection().createStatement().executeUpdate(query) > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Prescription> getPrescription(int medicRecordId){
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        String query ="select medicRecordId,veterinarianId,medicineId,unit ,medicine.name as 'medicineName',description," +
                "amount,sellPrice from prescription join medicine using(medicineId) join medicRecord using(medicRecordId)" +
                " where medicRecordId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,medicRecordId);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                Prescription prescription = new Prescription();
                prescription.setMedicRecordId(res.getInt("medicRecordId"));
                prescription.setMedicineId(res.getInt("medicineId"));
                prescription.setMedicineName(res.getString("medicineName"));
                prescription.setDescription(res.getString("description"));
                prescription.setAmount(res.getInt("amount"));
                prescription.setUnit(res.getString("unit"));
                prescription.setPrice(res.getDouble("sellPrice"));
                prescriptions.add(prescription);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return prescriptions;
    }

}
