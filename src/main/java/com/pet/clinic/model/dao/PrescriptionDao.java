package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Prescription;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionDao {

    public static ArrayList<Prescription> getPrescription(int medicRecordId){
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        String query ="select medicRecordId,veterinarianId,medicineId,medicine.name as 'medicineName',description," +
                "amount from prescription join medicine using(medicineId) join medicRecord using(medicRecordId)" +
                " where medicRecordId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,medicRecordId);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                Prescription prescription = new Prescription();
                prescription.setMedicRecordId(res.getInt("medicRecordId"));
                prescription.setVeterinarianId(res.getInt("veterinarianId"));
                prescription.setMedicineId(res.getInt("medicineId"));
                prescription.setMedicineName(res.getString("medicineName"));
                prescription.setDescription(res.getString("description"));
                prescription.setAmount(res.getInt("amount"));
                prescriptions.add(prescription);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return prescriptions;
    }
}
