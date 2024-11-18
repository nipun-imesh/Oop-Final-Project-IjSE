package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.tm.ManageExamMarkTM;
import com.assignment.finalproject.dto.tm.GetStudentNameIdTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageMarkModel {
    public ArrayList<GetStudentNameIdTM> getStudentDetail(String classId, String grade) throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "SELECT * FROM student WHERE   class = ? AND s_grade = ? AND status = 'Active'",
                grade,
                classId
        );
        ArrayList<GetStudentNameIdTM> getStudentNameIdTMS = new ArrayList<>();

        while (rst.next()) {
            GetStudentNameIdTM getStudentNameIdTM = new GetStudentNameIdTM(
                    rst.getString(1),
                    rst.getString(2)
            );
            getStudentNameIdTMS.add(getStudentNameIdTM);
        }
        return getStudentNameIdTMS;
    }

    public ArrayList<ManageExamMarkTM> getStudentMarkDetail(String studentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT \n" +
                "    e.ex_id AS examID, \n" +
                "    m.sub_id AS subjectID, \n" +
                "    e.e_name AS examName, \n" +
                "    m.mark, \n" +
                "    es.date, \n" +
                "    m.stu_id AS studentID\n" +
                "FROM \n" +
                "    exam e\n" +
                "JOIN \n" +
                "    marks m ON e.ex_id = m.ex_id\n" +
                "JOIN \n" +
                "    exam_schedule es ON e.ex_id = es.exam_id\n" +
                "WHERE \n" +
                "    m.stu_id = ?", studentId);

        ArrayList<ManageExamMarkTM> manageExamMarkTMS = new ArrayList<>();

        while (rst.next()) {
            ManageExamMarkTM manageExamMarkTM = new ManageExamMarkTM(
                    rst.getString("examID"),
                    rst.getString("subjectID"),
                    rst.getString("studentID"),  // Now this will correctly retrieve the studentID
                    rst.getString("mark"),
                    rst.getString("date")
            );
            manageExamMarkTMS.add(manageExamMarkTM);
        }
        return manageExamMarkTMS;
    }

}

