package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.db.DBConnection;
import com.assignment.finalproject.dto.sub.ExamDTO;
import com.assignment.finalproject.dto.sub.ExamScheduleDTO;
import com.assignment.finalproject.dto.sub.ExamSubjectIdDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class AddExamListModel {
    ExamShedulModel examShedulModel = new ExamShedulModel();
    ExamSubjectModel examSubjectModel = new ExamSubjectModel();

    public String getExamShedulID() {
        String uniqueID = UUID.randomUUID().toString().substring(0, 5);
        return "SH0" + "" + uniqueID;
    }

    public String getExamID() {
        String uniqueID = UUID.randomUUID().toString().substring(0, 5);
        return "EX0" + "" + uniqueID;
    }

    public boolean saveExamAndSchedule(
            ArrayList<ExamDTO> examDTOList,
            ArrayList<ExamScheduleDTO> examScheduleDTOList,
            ArrayList<ExamSubjectIdDTO> examSubjectIdDTOList) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Begin transaction

        try {

            for (ExamDTO examDTO : examDTOList) {
                boolean isExamSaved =  CrudUtil.execute(
                        "INSERT INTO exam (ex_id, e_name, grade) VALUES (?, ?, ?)",
                        examDTO.getExamId(),
                        examDTO.getExamName(),
                        examDTO.getGrade()
                );
                if (!isExamSaved) {
                    connection.rollback();
                    return false;
                }
            }

            for (ExamScheduleDTO scheduleDTO : examScheduleDTOList) {
                boolean isScheduleSaved = examShedulModel.saveExamSchedule(scheduleDTO);
                if (!isScheduleSaved) {
                    connection.rollback();
                    return false;
                }
            }

            for (ExamSubjectIdDTO subjectIdDTO : examSubjectIdDTOList) {
                boolean isSubjectSaved = examSubjectModel.saveExamSubjectDetails(subjectIdDTO);
                if (!isSubjectSaved) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;

        } finally {
            connection.setAutoCommit(true);
        }
    }
}

