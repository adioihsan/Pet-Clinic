package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.MedicRecord;

import java.sql.*;
import java.util.ArrayList;

public class MedicRecordDao {

    public static int saveMedicRecord(MedicRecord medicRecord){
        int id=0;
        String query = "insert into medicRecord(petId,anamnesis,diagnosis,recordDate,veterinarianId,petWeight,status) values(?,?,?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,medicRecord.getPetId());
            ps.setString(2,medicRecord.getAnamnesis());
            ps.setString(3,medicRecord.getDiagnosis());
            ps.setDate(4, Date.valueOf(medicRecord.getRecordDate()));
            ps.setInt(5,medicRecord.getVeterinarianId());
            ps.setDouble(6,medicRecord.getPetWeight());
            ps.setString(7,"Belum Ada Pembayaran");
            if(ps.executeUpdate()>0){
                ResultSet res = con.createStatement().executeQuery("select LAST_INSERT_ID()");
                res.next();
                id = res.getInt(1);
            };
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public static boolean updateMedicRecord(MedicRecord medicRecord){
        String query = "update medicRecord set anamnesis=?,diagnosis=?,recordDate=?,veterinarianId=?,petWeight=?" +
                " where medicRecordId=?";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,medicRecord.getAnamnesis());
            ps.setString(2,medicRecord.getDiagnosis());
            ps.setDate(3, Date.valueOf(medicRecord.getRecordDate()));
            ps.setInt(4,medicRecord.getVeterinarianId());
            ps.setDouble(5,medicRecord.getPetWeight());
            ps.setInt(6,medicRecord.getMedicRecordId());
            return ps.executeUpdate() > 0 ;
         } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static ArrayList<MedicRecord> getMedicRecord(int petId){
        ArrayList<MedicRecord> medicRecords =  new ArrayList<>();
        String query = "select medicRecordId,recordDate,anamnesis,diagnosis,veterinarianId," +
                "concat(veterinarian.firstname,\" \",veterinarian.lastName) as veterinarianName,petWeight,status " +
                "from medicRecord join veterinarian using(veterinarianId) where petId=?";
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
                medicRecord.setVeterinarianId(res.getInt("veterinarianId"));
                medicRecord.setVeterinarianName(res.getString("veterinarianName"));
                medicRecord.setPetWeight(res.getDouble("petWeight"));
                medicRecord.setStatus(res.getString("status"));
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
