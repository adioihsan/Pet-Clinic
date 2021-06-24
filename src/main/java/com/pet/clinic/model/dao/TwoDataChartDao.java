package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.TwoDataChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TwoDataChartDao {

    public static ArrayList<TwoDataChart> getChartData(String query){
        ArrayList<TwoDataChart> listData = new ArrayList<>();
        try {
            ResultSet res = DbConnect.getConnection().createStatement().executeQuery(query);
            while (res.next()){
                listData.add(new TwoDataChart(res.getString(1),res.getInt(2)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listData;
    }
}
