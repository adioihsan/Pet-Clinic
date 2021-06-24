package com.pet.clinic.controller.dashboard;

import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.pet.clinic.model.TwoDataChart;
import com.pet.clinic.model.dao.TwoDataChartDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

public class DashboardMainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart petKindChart;

    @FXML
    private CategoryAxis catAxisMedicine;

    @FXML
    private NumberAxis numbAxisMedicine;

    @FXML
    private BarChart<String, Number> chartMedicine;

    @FXML
    private Label lblVisitorToday;

    @FXML
    private Label lblVisitorYesterday;

    private ObservableList<PieChart.Data> petKindChartData = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // pet kind chart
        loadPetKindData();

        //medicine chart
        loadMedicineData();
        //set visitor today;
        loadVisitorThisDay();
        //set visitor yesterday;
        loadVisitorYesterday();
        //set Clock

    }
    private void loadPetKindData(){
        String query = "select distinct petKind.name,(select count(petId) from pet where pet.kind = petKind.name) " +
                "as amount from pet join petKind on pet.kind = petKind.name";
        ArrayList<TwoDataChart> listFromDb = TwoDataChartDao.getChartData(query);
        Iterator<TwoDataChart> itr = listFromDb.iterator();
        while(itr.hasNext()){
            TwoDataChart twoDataChart = itr.next();
            petKindChartData.add(new PieChart.Data(twoDataChart.getName(),twoDataChart.getAmount()));
        }
        petKindChart.setData(petKindChartData);
    }
    private void loadMedicineData(){
        var data = new XYChart.Series<String, Number>();
        String query = "select name,stock from medicine group by Stock limit 5";
        ArrayList<TwoDataChart> listFromDb = TwoDataChartDao.getChartData(query);
        Iterator<TwoDataChart> itr = listFromDb.iterator();
        while(itr.hasNext()){
            TwoDataChart twoDataChart = itr.next();
            data.getData().add(new XYChart.Data<>(twoDataChart.getName(), twoDataChart.getAmount()));
        }
        chartMedicine.getData().add(data);
        chartMedicine.setLegendVisible(false);
    }
    private void loadVisitorThisDay(){
        String query = "select curdate() as today,count(petId) from guestBook where cast(visitTime as Date) = curdate()";
        ArrayList<TwoDataChart> listFromDb = TwoDataChartDao.getChartData(query);
        Iterator<TwoDataChart> itr = listFromDb.iterator();
        while(itr.hasNext()){
            TwoDataChart twoDataChart = itr.next();
           lblVisitorToday.setText(String.valueOf(twoDataChart.getAmount()));
        }
    }
    private void loadVisitorYesterday(){
        String query = "select date_sub(curdate(),interval 1 day) as yesterday,count(petId) from guestBook" +
                " where cast(visitTime as Date) = date_sub(curdate(), interval 1 day)";
        ArrayList<TwoDataChart> listFromDb = TwoDataChartDao.getChartData(query);
        Iterator<TwoDataChart> itr = listFromDb.iterator();
        while(itr.hasNext()){
            TwoDataChart twoDataChart = itr.next();
            lblVisitorYesterday.setText(String.valueOf(twoDataChart.getAmount()));
        }
    }

}