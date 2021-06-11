package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.MedicRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class MedicRecordDao {

    public static ArrayList<MedicRecord> getMedicRecord(int petId){
        ArrayList<MedicRecord> medicRecords =  new ArrayList<>();
        String query = "select medicRecordId,recordDate,anamnesis,diagnosis,concat(veterinarian.firstName,\" \",veterinarian." +
                "lastName,\"(\",veterinarianId,\")\") as veterinarian " +
                "from medicRecord join veterinarian using(veterinarianId) where petId =?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,petId);
            ResultSet res = ps.executeQuery();
            while (res.next()){
                MedicRecord medicRecord = new MedicRecord();
                medicRecord.setMedicRecordId(res.getInt("medicRecordId"));
                medicRecord.setRecordDate(res.getDate("recordDate").toLocalDate());
                medicRecord.setAnamnesis(res.getString("anamnesis"));
                medicRecord.setDiagnosis(res.getString("diagnosis"));
                medicRecord.setVeterinarian(res.getString("veterinarian"));
                // get actions
                medicRecords.add(medicRecord);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return medicRecords;
    }

    public static ArrayList<String> getActionsName(int medicRecordId){
        ArrayList<String> actionsName = new ArrayList<>();
        String query = "select action.name as \"actionsName\" from action join actionsData using(actionId) " +
                "join medicRecord using(medicRecordId) where medicRecordId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,medicRecordId);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                actionsName.add(res.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return actionsName;
    }


/*    public static void main(String[] args){
        ArrayList<String> names = getActionsName(10);
        Iterator<String> name = names.iterator();
        while(name.hasNext()){
            System.out.println(name.next());
        }
    }*/
}
