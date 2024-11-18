package com.assignment.finalproject.model.subModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.sub.HallDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HallModel {
    public ObservableList<HallDTO> getAllHall() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from hall");

        ObservableList<HallDTO> hallDTOS = FXCollections.observableArrayList();
        while (rst.next()) {
            HallDTO hallDTO = new HallDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
            hallDTOS.add(hallDTO);
        }
        return hallDTOS;
    }
}
