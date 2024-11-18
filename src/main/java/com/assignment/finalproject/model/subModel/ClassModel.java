package com.assignment.finalproject.model.subModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.sub.ClassDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassModel {

    public ObservableList<ClassDTO> getAllClass() throws SQLException {
        ResultSet rst = CrudUtil.execute("select class_id from class");

        ObservableList<ClassDTO> classDTOS = FXCollections.observableArrayList();
        while (rst.next()) {
            ClassDTO classDTO = new ClassDTO(
                    rst.getString(1)  // Customer ID
            );
            classDTOS.add(classDTO);
        }
        return classDTOS;
    }


    public ClassDTO findByclass(String selectedClassId) {
        return null;
    }
}
