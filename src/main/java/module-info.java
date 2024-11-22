module com.assignment.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires java.sql.rowset;
    requires java.mail;
    requires net.sf.jasperreports.core;

    opens com.assignment.finalproject.dto.tm to javafx.base;
    opens com.assignment.finalproject.controller to javafx.fxml;
    exports com.assignment.finalproject;
    opens com.assignment.finalproject.controller.login to javafx.fxml;
    exports com.assignment.finalproject.dto.main;
    exports com.assignment.finalproject.dto.tm;

}