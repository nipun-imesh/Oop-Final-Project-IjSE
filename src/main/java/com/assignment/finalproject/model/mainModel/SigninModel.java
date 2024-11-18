package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.main.SigninDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SigninModel {

    public String getUserId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select User_id from user order by User_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("A%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "A001"; // Return the default customer ID if no data is found
    }

    public boolean save(SigninDTO signinDto) throws SQLException {

            return CrudUtil.execute("insert into user(User_id,User_name,User_password) values(?,?,?)",
                    signinDto.getUserid(),
                    signinDto.getUsername(),
                    signinDto.getPassword());



    }
}
