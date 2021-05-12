module com.pet.clinic {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.pet.clinic to javafx.fxml;
    exports com.pet.clinic;
    exports com.pet.clinic.controller;
   // exports com.pet.clinic.model;
}