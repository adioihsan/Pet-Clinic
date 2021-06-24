package com.pet.clinic.helper;

import com.pet.clinic.App;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Map;

public abstract class Report {
    private static JasperReport jreport;
    private static JasperViewer jviewer;
    private static JasperPrint jprint;

    public static void createReport(Connection connect, Map<String,Object> map, URL url){
        try {
            jreport = (JasperReport) JRLoader.loadObject(url);
            jprint = JasperFillManager.fillReport(jreport,map,connect);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void showReport(){
        jviewer = new JasperViewer(jprint , false);
        jviewer.setVisible(true);
    }


}
