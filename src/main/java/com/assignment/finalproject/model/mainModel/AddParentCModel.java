package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.main.AddParentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class AddParentCModel {

    public static boolean saveParent(AddParentDTO addParentDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into parent values (?,?,?,?)",
                addParentDTO.getParentId(),
                addParentDTO.getParentName(),
                addParentDTO.getParentEmail(),
                addParentDTO.getStatus()
        );

    }

    public static ArrayList<AddParentDTO> getAllParent() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from parent where status = 'Active'");

        ArrayList<AddParentDTO> addParentDTOS = new ArrayList<>();

        while (rst.next()) {
            AddParentDTO addParentDTO = new AddParentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)

            );
            addParentDTOS.add(addParentDTO);
        }
        return addParentDTOS;
    }
    public static ArrayList<AddParentDTO> searchParent()  {
//
        return null;
    }

    public String getParentID() throws SQLException {
      //  ResultSet rst = CrudUtil.execute("select parent_id from parent order by parent_id desc limit 1");

        String uniqueID = UUID.randomUUID().toString().substring(0, 7);
        return "P" + uniqueID;
    }

    public static boolean upDateParent(AddParentDTO addParentDTO) throws SQLException {

        return CrudUtil.execute(
                "update parent set parent_name=?,e_mail=?,status=? where parent_id=?",
                addParentDTO.getParentName(),
                addParentDTO.getParentEmail(),
                addParentDTO.getStatus(),
                addParentDTO.getParentId()
        );
    }

    public static boolean deleteParent(String parentId) throws SQLException {


        return CrudUtil.execute( "update parent set status = 'Deactive' where parent_id = ?",parentId);
    }
}
