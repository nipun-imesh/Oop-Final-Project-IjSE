module com.assignment.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;

    opens com.assignment.finalproject.dto.tm to javafx.base;
    opens com.assignment.finalproject.controller to javafx.fxml;
    exports com.assignment.finalproject;
    opens com.assignment.finalproject.controller.login to javafx.fxml;
    exports com.assignment.finalproject.dto.main;
    exports com.assignment.finalproject.dto.tm;

}