package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.db.DBConnection;
import com.assignment.finalproject.dto.main.LoginDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    public int chekid(LoginDTO loginDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select * from user where User_name = ? and User_password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, loginDto.getUsername());
        preparedStatement.setString(2, loginDto.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            if(resultSet != null){
                return 1;
            }
        }
        return 0;
    }
}
