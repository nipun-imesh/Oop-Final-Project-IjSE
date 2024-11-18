package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.main.StudentManageDTO;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class StudentManageModel {

    public static boolean saveStudent(StudentManageDTO studto) {
        System.out.println(studto.getStudentId());
        try {
            return CrudUtil.execute(
                    "insert into student values (?,?,?,?,?,?,?,?)",
                    studto.getStudentId(),
                    studto.getStudentName(),
                    studto.getAge(),
                    studto.getDateofBarth(),
                    studto.getGrade(),
                    studto.getS_class(),
                    studto.getParentId(),
                    studto.getStatus()
            );
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Duplicate ID").show();
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<StudentManageDTO> getAllstuden() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM student WHERE status = 'Active'");

        ArrayList<StudentManageDTO> studentManageDTOS = new ArrayList<>();

        while (rst.next()) {
            StudentManageDTO studentManageDTO = new StudentManageDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );
            studentManageDTOS.add(studentManageDTO);
        }
        return studentManageDTOS;
    }

    public String getStudentID() throws SQLException {

        String uniqueID = UUID.randomUUID().toString().substring(0, 8);
        return "S0" +"" + uniqueID;
    }

    public boolean deleteStudent(String id) throws SQLException {
        return CrudUtil.execute("update student set status = 'Deactive' where stu_id = ?",id);
    }

    public boolean updateStudent(StudentManageDTO studentManageDTO) {
        try {
            return CrudUtil.execute(
                    "update student set stu_name = ?,age = ?,DOB = ?,s_grade = ?,class = ?,parent_id = ? where stu_id = ?",
                    studentManageDTO.getStudentName(),
                    studentManageDTO.getAge(),
                    studentManageDTO.getDateofBarth(),
                    studentManageDTO.getGrade(),
                    studentManageDTO.getS_class(),
                    studentManageDTO.getParentId(),
                    studentManageDTO.getStudentId()
            );
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Duplicate ID").show();
            throw new RuntimeException(e);
        }
    }


}
