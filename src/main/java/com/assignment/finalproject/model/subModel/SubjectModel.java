package com.assignment.finalproject.model.subModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.sub.SabjectDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectModel {

    public ObservableList<SabjectDTO> get6TO9Subject() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from subject ORDER BY sub_id asc limit 8");

        ObservableList<SabjectDTO> sabjectDTOS = FXCollections.observableArrayList();
        while (rst.next()) {
            SabjectDTO sabjectDTO = new SabjectDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
            sabjectDTOS.add(sabjectDTO);
        }
        return sabjectDTOS;
    }

    public ObservableList<SabjectDTO> get10TO11Subject() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from subject ORDER BY sub_id desc limit 9");

        ObservableList<SabjectDTO> sabjectDTOS = FXCollections.observableArrayList();
        while (rst.next()) {
            SabjectDTO sabjectDTO = new SabjectDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
            sabjectDTOS.add(sabjectDTO);
        }
        return sabjectDTOS;
    }

    public String getSubjectName(String subjectID) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select s_name from subject where sub_id = ?",subjectID);
        System.out.println(subjectID);

        if (resultSet.next()) { // Ensure there is a result
            return resultSet.getString("s_name"); // Fetch and return the s_name as a String
        } else {
            return null; // Return null if no result is found (optional, handle as needed)
        }
    }
}
