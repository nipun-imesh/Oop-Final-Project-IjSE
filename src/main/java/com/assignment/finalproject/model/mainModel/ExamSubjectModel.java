package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.dto.sub.ExamSubjectIdDTO;

import java.sql.SQLException;

public class ExamSubjectModel {

    public boolean saveExamSubjectDetails(ExamSubjectIdDTO subjectIdDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO sub_exam_detail (exa_id, sub_id) VALUES (?, ?)",
                subjectIdDTO.getExamId(),
                subjectIdDTO.getSubjectId()
        );
    }

    public boolean updateExamSubjectId(String subjectID, String examID) throws SQLException {
        return CrudUtil.execute(
                "UPDATE sub_exam_detail SET sub_id = ? WHERE exa_id = ?",
                subjectID, examID
        );
    }

    public boolean deleteSubject(String examID) throws SQLException {
        return CrudUtil.execute("DELETE FROM sub_exam_detail WHERE exa_id = ?", examID);
    }
}



