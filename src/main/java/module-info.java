module com.pet.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires org.mariadb.jdbc;

    opens com.pet.clinic to javafx.fxml;
//    opens com.pet.clinic.controller to javafx.fxml;
    exports com.pet.clinic;
//    exports com.pet.clinic.controller;
    exports com.pet.clinic.controller.patient;
    opens com.pet.clinic.controller.patient to javafx.fxml;
    exports com.pet.clinic.controller.dashboard;
    opens com.pet.clinic.controller.dashboard to javafx.fxml;
    exports com.pet.clinic.controller.navigation;
    opens com.pet.clinic.controller.navigation to javafx.fxml;
    exports com.pet.clinic.controller.login;
    opens com.pet.clinic.controller.login to javafx.fxml;
    // exports com.pet.clinic.model;
}