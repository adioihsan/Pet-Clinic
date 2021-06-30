module com.pet.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires tornadofx.controls;
    requires java.sql;
    requires org.mariadb.jdbc;
    requires commons.lang;
    requires jasperreports;
    requires  sqlite.jdbc;
    requires org.mybatis;

    exports com.pet.clinic;
    opens com.pet.clinic to javafx.fxml;
//    opens com.pet.clinic.controller to javafx.fxml;
//    exports com.pet.clinic.controller;
    exports com.pet.clinic.controller.patient;
    opens com.pet.clinic.controller.patient to javafx.fxml;
    exports com.pet.clinic.controller.dashboard;
    opens com.pet.clinic.controller.dashboard to javafx.fxml;
    exports com.pet.clinic.controller.navigation;
    opens com.pet.clinic.controller.navigation to javafx.fxml;
    exports com.pet.clinic.controller.login;
    opens com.pet.clinic.controller.login to javafx.fxml;
    exports  com.pet.clinic.controller.medicRecord;
    opens  com.pet.clinic.controller.medicRecord to javafx.fxml;
    exports com.pet.clinic.controller.veterinarian;
    opens com.pet.clinic.controller.veterinarian to javafx.fxml;
    exports com.pet.clinic.controller.medicine;
    opens com.pet.clinic.controller.medicine to javafx.fxml;
    exports com.pet.clinic.controller.guestBook;
    opens com.pet.clinic.controller.guestBook to javafx.fxml;
    exports com.pet.clinic.model;
    opens com.pet.clinic.model to javafx.fxml;
    exports com.pet.clinic.helper;
    opens com.pet.clinic.helper to javafx.fxml;
    exports com.pet.clinic.controller.payment;
    opens com.pet.clinic.controller.payment to javafx.fxml;
    exports com.pet.clinic.controller.report;
    opens com.pet.clinic.controller.report to javafx.fxml;
    exports com.pet.clinic.controller.admin;
    opens com.pet.clinic.controller.admin to javafx.fxml;
    exports com.pet.clinic.database;
    opens com.pet.clinic.database to javafx.fxml;

}