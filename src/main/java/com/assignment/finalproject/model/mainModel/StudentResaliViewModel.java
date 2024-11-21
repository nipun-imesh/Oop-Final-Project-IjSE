package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.tm.GetResaltTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentResaliViewModel {

    public ArrayList<GetResaltTM>searchResalt(String studentId, String classId, String grade, String examName) throws SQLException {

        ResultSet rst = CrudUtil.execute(
                "SELECT subject_name,mark FROM mark WHERE student_id = ? AND class = ? AND s_grade = ? AND e_name = ?",
                studentId,
                classId,
                grade,
                examName
        );
        ArrayList<GetResaltTM> getResaltTMS = new ArrayList<>();

        while (rst.next()) {
            GetResaltTM getResaltTM = new GetResaltTM(
                    rst.getString(1),
                    rst.getDouble(2)
            );
            getResaltTMS.add(getResaltTM);
        }
        return null;
    }
}
