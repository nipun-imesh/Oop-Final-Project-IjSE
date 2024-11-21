package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.tm.GetResaltTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentResaliViewModel {

    public ArrayList<GetResaltTM> searchResalt(String classId, String examName, String grade, String studentId) throws SQLException {
        System.out.println("Query Parameters:");
        System.out.println("classId: " + classId);
        System.out.println("examName: " + examName);
        System.out.println("grade: " + grade);
        System.out.println("studentId: " + studentId);

        ResultSet sql = CrudUtil.execute(
                " SELECT s_name ,mark FROM student s INNER JOIN marks m ON s.stu_id = m.stu_id INNER JOIN subject subj ON m.sub_id = subj.sub_id INNER JOIN exam e ON m.ex_id = e.ex_id WHERE  s.class\n" +
                        "= ? AND  e.e_name = ? AND s.s_grade = ? AND s.stu_id ?",
                classId,
                examName,
                grade,
                studentId
        );

        ArrayList<GetResaltTM> getResaltTMS = new ArrayList<>();
        while (sql.next()) {
            GetResaltTM getResaltTM = new GetResaltTM(
                    sql.getString("s_name"),
                    sql.getDouble("mark")
            );
            getResaltTMS.add(getResaltTM);
        }
        return getResaltTMS;
    }
}
