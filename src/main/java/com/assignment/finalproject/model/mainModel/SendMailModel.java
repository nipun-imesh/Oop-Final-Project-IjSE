package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.sub.GetParentIdDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SendMailModel {

    public ObservableList<GetParentIdDTO> getAllParentID() throws SQLException {

        ResultSet rst = CrudUtil.execute("select parent_id from parent where status = 'Active'");

        ObservableList<GetParentIdDTO> getParentIdDTOS = FXCollections.observableArrayList();
        while (rst.next()){
            GetParentIdDTO getParentIdDTO = new GetParentIdDTO(
                    rst.getString(1)
            );
            getParentIdDTOS.add(getParentIdDTO);
        }
        return getParentIdDTOS;
    }

    public String getParentName(String parentId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT parent_name FROM parent WHERE parent_id = ?", parentId);

        if (resultSet.next()) {
            return resultSet.getString("parent_name");
        }
        return null;
    }

    public String getParentEmail(String parentId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT e_mail FROM parent WHERE parent_id = ?", parentId);

        if(resultSet.next()){
            return resultSet.getString("e_mail");
        }
        return null;
    }
}
